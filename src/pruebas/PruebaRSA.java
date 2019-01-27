package pruebas;


/**
 * main1
 * Funcionamiento estandar del cifrado RSA
 * 
 * main2
 * Funcionamiento estandar del cifrado RSA pero modificando su clave privada
 * con un metodo setter
 * 
 * @author Alex
 */


import seguridad.AES;
import seguridad.RSA;

public class PruebaRSA {
	public static void main1(String []args) throws Exception
	{
		AES instanciaA = new AES();
		String claveA = instanciaA.getClavePrivada();
		System.out.println("------------Clave privada de AES a encriptar-------------");
		System.out.println(claveA);
		RSA instanciaR = new RSA();
		String encr = instanciaR.encriptarClaveAES(claveA);
		System.out.println("------------Clave privada de AES encriptada-------------");
		System.out.println(encr);
		System.out.println("------------Clave privada de AES desencriptada-------------");
		System.out.println(instanciaR.decriptarClaveAES(encr));
	}
	
	public static void main2(String[] args) throws Exception {
		RSA rsa1, rsa2;
		AES aes1;
		
		aes1 = new AES();
		System.out.println("----------- Clave AES a encriptar -----------");
		System.out.println(aes1.getClavePrivada());
		rsa1 = new RSA();
		System.out.println("----------- Claves RSA publica/privada para encriptar -----------");
		System.out.println(rsa1.getPublicKey());
		System.out.println(rsa1.getPrivateKey());
		String encriptado = rsa1.encriptarClaveAES(aes1.getClavePrivada());
		System.out.println("----------- Claves AES encriptada -----------");
		System.out.println(encriptado);
		rsa2 = new RSA(rsa1.getPrivateKey());
		System.out.println("----------- Nueva clave RSA privada -----------");
		System.out.println(rsa2.getPrivateKey());
		String decriptado = rsa2.decriptarClaveAES(encriptado);
		System.out.println("----------- Clave AES desencriptada -----------");
		System.out.println(decriptado);
		if(decriptado.equals(aes1.getClavePrivada())) {
			System.out.println("Los string de la clave AES coinciden!!!");
		} else {
			System.out.println("ERROR: No funciona este metodo...");
		}
	}
}
