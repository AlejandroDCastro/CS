package seguridad;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.Base64;
import java.io.*;
import com.google.common.io.Files;

public class AES {

	// VARIABLES DE INSTANCIA
	
	private SecretKeySpec sKeySpec;
	private Cipher cipher;
	private IvParameterSpec iv;
	public static final String sIv = "AES/CBC/PKCS5Padding";
	
	// METODO CONSTRUCTOR
	
	public AES() throws Exception {
		
		// Instanciamos un Generador de llaves en tipo AES
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		// Inicializamos el generador especificandole un tamanyo de 128 bytes
		kgen.init(128);
		// Generamos un objeto de cifrado de tipo AES
		cipher = Cipher.getInstance(sIv);
		// Instanciamos una llave secreta
		SecretKey llave = kgen.generateKey();
		// Codificamos dicha llave en bytes
		byte[] crudo = llave.getEncoded();
		// Construimos una clave secreta especificando que es de tipo AES
		sKeySpec = new SecretKeySpec(crudo, "AES");
		// Para cifrar en modo CBC necesitamos este parametro
		byte[] ivBytes = {54, 1, 0, 0, 38, 124, 42, 48, 0, 23, 88, 34, 11, 11, 98, 32};
		iv = new IvParameterSpec(ivBytes);
	}
	
	
	// METODO CONSTRUCTOR AL QUE LE PASAS UNA CLAVE SECRETA
	
	public AES(String cl) throws Exception{
		// Generamos un objeto de cifrado de tipo AES
		cipher = Cipher.getInstance(sIv);
		// Codificamos el password en bytes
		byte[] crudo = cl.getBytes();
		// Construimos una clave secreta especificando que es de tipo AES
		sKeySpec = new SecretKeySpec(crudo, "AES");
		// Para cifrar en modo CBC necesitamos este parametro
		byte[] ivBytes = {54, 1, 0, 0, 38, 124, 42, 48, 0, 23, 88, 34, 11, 11, 98, 32};
		iv = new IvParameterSpec(ivBytes);
	}
	
	
	/**
	 * Encriptamos el archivo pasado por parametro
	 * 
	 * @param archivo
	 * @throws Exception
	 * @return mensaje encriptado en String y el nombre del archivo
	 */
	
	public String[] encriptarArchivo (File f) throws Exception {
		
		String[] devolver = new String[2];
			
		devolver[1] = f.getName();
		
		// Inicializamos el sistema de ahora en modo de ENCRIPTACION con la clave del constructor
		cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv);
		
		// Encriptamos el mensaje
		byte[] bytesArray = new byte[(int) f.length()]; 

		FileInputStream fis = new FileInputStream(f);
		fis.read(bytesArray); //read file into bytes[]
		fis.close();
		byte[] encriptado = cipher.doFinal(bytesArray);
		
		devolver[0] = new String(Base64.getEncoder().encode(encriptado));
		
		return devolver;
	}
	
	
	/**
	 * Desencriptamos el archivo pasado por parametro
	 * 
	 * @param archivo
	 * @throws Exception
	 * @return mensaje en claro en String
	 */
	
	public void desencriptarArchivo(String s, String nombre) throws Exception {
		
		// Inicializamos el sistema de ahora en modo de DESENCRIPTACION con la clave del constructor
		cipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv);
		
		// Obtenemos el array de bytes de lo decriptado
		byte[] desencriptado = cipher.doFinal(Base64.getDecoder().decode(s));
		
		//Pasamos de bytes a File con ayuda de la libreria Google Guava
		Files.write(desencriptado, new File(nombre));
	}
	

	
	/**
	 * Getter de la clave privada en base 64
	 * 
	 * @throws Exception
	 * @return skeySpec
	 */
	
	public String getClavePrivada() {
		byte[] llave = sKeySpec.getEncoded();
		return Base64.getEncoder().encodeToString(llave);
	}
	
	
	
	/**
	 * Setter de la clave privada
	 * 
	 * @throws Exception
	 * @params String
	 * @return void
	 */
	
	public void setClavePrivada(String s) {
		byte[] b = Base64.getDecoder().decode(s);
		sKeySpec = new SecretKeySpec(b, "AES");
	}

}