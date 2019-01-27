package pruebas;

/**
 * mainUpload
 * Se pide al usuario introducir una password. Se crea una clave AES con dicha clave
 * y se imprime por pantalla. Se obtiene el archivo pasado por parametro y
 * se encripta con la clave, y se imprime por pantalla. Se crea un fichero en la nube
 * con la cabecera y el archivo encriptado.
 * 
 * mainDownload
 * Se hace el mismo proceso pero en orden inverso, desencriptando archivos
 * y claves
 * 
 * @author Alex
 */

import java.io.*;
import java.util.Scanner;
import seguridad.*;

public class PruebaAES {
	
	
	// metodo que crea un archivo de texto y escribe en el, especificando su nombre o su ruta
	public static File crearCopiaSeguridadRemota(String r, String c) throws Exception {
		
		// especificamos su ruta
		File f = new File(r);
		
		// si no existia creamos el archivo
		if(!f.exists()) {
			f.createNewFile();
		}
		
		// copiamos la cabecera cifrada AES junto con el archivo cifrado
		FileWriter fw = new FileWriter(f);
		BufferedWriter br = new BufferedWriter(fw);
		br.write(c);
		br.close();
		fw.close();
		
		return f;
	}

	public static void mainUpload(String[] args) throws Exception {
		
		if(args!=null && args.length==1) {
			String ruta = "C:\\Users\\Alex\\Desktop\\Papa\\";
			String clave = "";
			SHA3 rellena = new SHA3();
			
			System.out.print("Introduce una password: ");
			Scanner esc = new Scanner(System.in);
			clave = esc.nextLine();
			esc.close();
			
			String sha = rellena.getSHA512(clave);
			AES aes1 = new AES(sha);
			System.out.println("Clave AES introducida: " + aes1.getClavePrivada());
			
			File fichero = new File(args[0]);
			String[] encriptado = aes1.encriptarArchivo(fichero);
			System.out.println("El archivo " + encriptado[1] + " encriptado: " + encriptado[0]);
			
			String texto = encriptado[1] + ".txt";
			String contenido = encriptado[1] + "####" + encriptado[0];
			PruebaAES.crearCopiaSeguridadRemota(ruta + texto, contenido);
			
			System.out.println("Clave AES hasheada: " + sha);
		}
	}
	
	
	public static void mainDownload(String[] args) {
		if(args.length == 1) {
			FileReader fichero = null;
			BufferedReader br = null;
			try {
				
				
				String nombre = args[0];
				String ruta = "C:\\Users\\Alex\\Desktop\\Papa\\" + nombre;
				String clave = "";
				SHA3 rellena = new SHA3();
				
				File texto = new File(ruta);
				fichero = new FileReader(texto);
				br = new BufferedReader(fichero);
				String contenido = br.readLine();
				System.out.println("Contenido: " + contenido);
				String[] cifrado = contenido.split("####");
				
				System.out.print("Introduce una password: ");
				Scanner esc = new Scanner(System.in);
				clave = esc.nextLine();
				esc.close();
				
				String sha = rellena.getSHA512(clave);
				AES aes1 = new AES(sha);
				System.out.println("Clave AES hasheada: " + sha);
				aes1.desencriptarArchivo(cifrado[1], cifrado[0]);
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
					fichero.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		AES aes1, aes2;
		
		aes1 = new AES();
		System.out.println("--------- Clave AES1 generada ---------");
		System.out.println(aes1.getClavePrivada());
		aes2 = new AES();
		System.out.println("--------- Clave AES2 generada ---------");
		System.out.println(aes2.getClavePrivada());
		aes2.setClavePrivada(aes1.getClavePrivada());
		System.out.println("--------- Clave AES2 setteada ---------");
		System.out.println(aes2.getClavePrivada());
	}
	
}
