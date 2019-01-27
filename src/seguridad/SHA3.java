package seguridad;

import java.math.BigInteger;//Para usar enteros grandes
import java.security.MessageDigest;//Necesario para usar SHA-512
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;


public class SHA3 {

	public String getSHA512(String input){

		String toReturn = null;
		try {
			
			//Se guarda en md un MessageDigest de tipo SHA-512
		    MessageDigest md = MessageDigest.getInstance("SHA-512");
		    
		    //Resetea el mensaje
		    md.reset();
		    
		    //Especifica el array de bytes que se usara, input (pasado a bytes UTF-8) en este caso
		    md.update(input.getBytes(StandardCharsets.UTF_8));
		    
		    //digest() resume la palabra, que resulta en un gran entero (hexadecimal), que a su vez se pasa a String
		    toReturn = String.format("%040x", new BigInteger(1, md.digest()));
		    
		    //cogemos las primeras 16 palabras que equivaldrian a los 128 bits
		    toReturn = toReturn.substring(0,16);
		    
		}
		catch (NoSuchAlgorithmException e) {
		    e.printStackTrace();
		}
		return toReturn;
	}

}