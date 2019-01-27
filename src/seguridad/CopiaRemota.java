package seguridad;

import java.io.*;
import java.util.*;

/**
 * Clase que permite subir subir y descargar archivos
 * de forma segura
 * 
 * @params [Archivo] + [E/D](Encriptar/Decriptar)
 * @author Alex
 *
 */

public class CopiaRemota {
	
	
	// metodo que crea un archivo de texto y escribe en el, especificando su nombre o su ruta
	public static File crearCopiaSeguridadRemota(String r, String c) {
		
		FileWriter fw = null;
		BufferedWriter br = null;
		File f = null;
		
		try {
			
			// especificamos su ruta
			f = new File(r);
			
			// si no existia creamos el archivo
			if(!f.exists()) {
				f.createNewFile();
			}
			
			// copiamos la cabecera cifrada AES junto con el archivo cifrado
			fw = new FileWriter(f);
			br = new BufferedWriter(fw);
			br.write(c);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fw.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return f;
	}
	
	
	
	public static String leerCopiaSeguridadRemota(File f) {
		
		FileReader fw = null;
		BufferedReader br = null;
		String devuelve = null;
		
		try {
			
			fw = new FileReader(f);
			br = new BufferedReader(fw);
			devuelve = br.readLine();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fw.close();
				
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				
			}
		}
		return devuelve;
	}
	

	

	public static void main(String[] args) {
		AES aes1, aes2;
		RSA rsa1;
		String password;
		File archivo = null, archivoClaveRSAprivada = null;
		SHA3 hash = new SHA3();
		String rutaDrive = "C:\\Users\\Usuario\\Google Drive\\iSecurityCS\\";
		String rutaUploads = "C:\\Users\\Usuario\\Desktop\\iSecurity\\Uploads\\";
		String rutaDownloads = "C:\\Users\\Usuario\\Desktop\\iSecurity\\Downloads\\";
		
		
		if(args.length != 3) {
			System.out.println("ERROR: Introduce todos los parametros...");
			System.exit(-1);
		}
		
		
		// si todos los parametros son correctos
		try {
			
			
			
			// comprobamos la operacion a realizar: subir o descargar de la nube
			if(args[1].equals("E")) {
				
				
				// obtenemos la password introducida por el usuario
				password = args[2];
				
				// inicializamos las variables AES y RSA principales
				aes1 = new AES();
				rsa1 = new RSA();
				
				// obtenemos el archivo a cifrar
				archivo = new File(rutaUploads + args[0]);
				
				// lo ciframos con AES obteniendo su nombre y el string del cifrado
				String[] archivoCifrado = aes1.encriptarArchivo(archivo);
				
				// ciframos la clave AES usada con el metodo RSA
				String claveAEScifrada = rsa1.encriptarClaveAES(aes1.getClavePrivada());
				
				// creamos el .txt remoto que conformara la copia de seguridad con el nombre correspondiente
				String cabecera = claveAEScifrada + "####" + archivoCifrado[0];
				CopiaRemota.crearCopiaSeguridadRemota(rutaDrive + archivoCifrado[1] + ".txt", cabecera);
				
				// creamos otro .txt local que contendra la clave privada rsa
				archivoClaveRSAprivada = CopiaRemota.crearCopiaSeguridadRemota("key" + archivoCifrado[1] + ".txt", rsa1.getPrivateKey());
				
				// ciframos con AES, esta password tendra que ser recordada por el usuario
				aes2 = new AES(hash.getSHA512(password));
				String[] archivoCifrado2 = aes2.encriptarArchivo(archivoClaveRSAprivada);
				
				// lo guardamos por ultimo en la nube con la password para posterior comprobacion
				CopiaRemota.crearCopiaSeguridadRemota(rutaDrive + archivoCifrado2[1], archivoCifrado2[0]);
				archivoClaveRSAprivada.delete();
				
				System.out.println("Copia de seguridad remota generada correctamente!!!");
				
				
				
			} else if(args[1].equals("D")) {
				
				File archivo1 = null, archivo2 = null;
				String nombre = args[0];
				
				
				// obtenemos la password introducida por el usuario
				password = args[2];
				
				// obtenemos los archivos que estan en la nube
				archivo1 = new File(rutaDrive + nombre);
				archivo2 = new File(rutaDrive + "key" + nombre);
				
				// obtenemos sus contenidos
				String contenido1 = CopiaRemota.leerCopiaSeguridadRemota(archivo1);
				String contenido3 = CopiaRemota.leerCopiaSeguridadRemota(archivo2);
				
				// usamos la password introducida para obtener todas las claves y criptosistemas
				aes2 = new AES(hash.getSHA512(password));
				
				// obtenemos el archivo que contiene la clave RSA privada
				aes2.desencriptarArchivo(contenido3, rutaDownloads + "key" + nombre);
				archivoClaveRSAprivada = new File(rutaDownloads + "key" + nombre);
				
				// obtenemos la clave rsa privada
				String contenido2 = CopiaRemota.leerCopiaSeguridadRemota(archivoClaveRSAprivada);
				rsa1 = new RSA(contenido2);
				
				// obtenemos la clave AES cifrada junto a la copia de seguridad del archivo
				String[] encriptado = contenido1.split("####");
				
				// desciframos la clave AES con RSA
				String claveAES = rsa1.decriptarClaveAES(encriptado[0]);
				
				// instanciamos dicha clave AES
				aes1 = new AES();
				aes1.setClavePrivada(claveAES);
				
				// desencriptamos el archivo en la carpeta de downloads
				String[] formato = nombre.split("\\.");
				aes1.desencriptarArchivo(encriptado[1], rutaDownloads + formato[0] + "." + formato[1]);
				
				// borramos por ultimo los .txt innecesarios
				archivoClaveRSAprivada.delete();
				
				System.out.println("Se ha descargado el archivo correctamente!!!");
				
			}
			
		} catch(Exception e) {
			System.out.println("Error: No se ha podido ejecutar la operacion...");
		}

	}

}
