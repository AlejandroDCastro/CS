package seguridad;

/**
 * 
 * @author Alex
 * Implementacion del Algoritmo RSA
 *
 */

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;
import java.util.Base64;

public class RSA {
	
	// VARIABLES DE INSTANCIA
	
	private PublicKey publica;
	private PrivateKey privada;
	private Cipher cipher;
	
	
	// METODO CONSTRUCTOR
	
	public RSA() throws Exception {
		// Instanciamos un generador de claves de tipo RSA
		KeyPairGenerator kgen = KeyPairGenerator.getInstance("RSA");
		// Inicializamos especificando su tamanyo de clave
		kgen.initialize(1024);
		// Obtenemos un repositorio para obtener las claves publica y privada
		KeyPair par = kgen.genKeyPair();
		// Obtenemos las claves
		publica = par.getPublic();
		privada = par.getPrivate();
		
		// Generamos un objeto de cifrado de tipo RSA
		cipher = Cipher.getInstance("RSA");
	}
	
	
	// METODO CONSTRUCTOR AL QUE SE LE PASA UNA CLAVE SECRETA
	
	public RSA(String priv) throws Exception
	{
		KeyFactory factory = KeyFactory.getInstance("RSA");
		//Estandar de claves privadas
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(priv));
		privada = factory.generatePrivate(keySpec);
		
		// Generamos un objeto de cifrado de tipo RSA
		cipher = Cipher.getInstance("RSA");
	}

	

	/**
	 * Encriptamos la clave AES pasada por parametro
	 * 
	 * @param archivo
	 * @throws Exception
	 * @return criptosistema en array bytes
	 */
	
	public String encriptarClaveAES(String s) throws Exception {
		
		// modificamos el sistema para que encripte con la clave publica
		cipher.init(Cipher.ENCRYPT_MODE, publica);
		
		// ciframos la clave AES
		byte[] cifrado = cipher.doFinal(s.getBytes());
		
		// devolvemos la clave AES encriptada como un String
		return new String(Base64.getEncoder().encode(cifrado));
	}
	
	
	
	/**
	 * Decriptamos la clave AES pasada por parametro
	 * 
	 * @param archivo
	 * @throws Exception
	 * @return mensaje en claro en String
	 */
	
	public String decriptarClaveAES(String s) throws Exception {
		
		// modificamos el sistema para que desencripte con la clave privada
		cipher.init(Cipher.DECRYPT_MODE, privada);
		
		// desciframos la clave AES
		byte[] decriptado = cipher.doFinal(Base64.getDecoder().decode(s));
		
		return new String(decriptado);
	}
	
	
	
	/**
	 * Getter de la clave publica
	 * 
	 * @return PublicKey
	 */
	
	public String getPublicKey() {
		byte[] llave = publica.getEncoded();
		return Base64.getEncoder().encodeToString(llave);
	}

	
	
	/**
	 * Getter de la clave privada
	 * 
	 * @return PrivateKey
	 */
	
	public String getPrivateKey() {
		byte[] llave = privada.getEncoded();
		return Base64.getEncoder().encodeToString(llave);
	}
}