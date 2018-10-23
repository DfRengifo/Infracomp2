import java.io.UnsupportedEncodingException;
import java.security.*;
import javax.crypto.*;

public class Cliente extends Thread
{
	private String mensaje;
	private static Servidor servidor;
	private String llave;
	private Key llaveEncrip;

	public Cliente( Servidor pServidor, String pMensaje) {

		this.mensaje = pMensaje;
		this.servidor = pServidor;
	}

	@Override
	public void run()
	{
		String m = mensaje;
		almacenar(m);
	}

	public synchronized void  almacenar(String mensaje)
	{
		synchronized(mensaje)
		{
			try {

				mensaje.wait();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Metodo para encriptar dado un algoritmo, metodo de encriptacion, tamaño en bits y un texto
	 * Ej: AES, ECP, 128, bananana
	 */
	
	public String encriptarConPKSC5(String algoritmo, String metodo, String tamanio, String texto) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
	{
		
		byte[] enByt = texto.getBytes("UTF8");
		
		KeyGenerator keyGen = KeyGenerator.getInstance(algoritmo);		
		keyGen.init(Integer.parseInt(tamanio));
		Key key = keyGen.generateKey();
		llaveEncrip = key;
		Cipher cipher = Cipher.getInstance(algoritmo + "/" + metodo + "/PKSC5Padding" );
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return new String(cipher.doFinal(enByt), "UTF8");
	}
	
	/*
	 * Metodo para desencriptar dado un algoritmo, un metodo de encriptacion, tamaño en bits y una llave
	 * Ej: AES, ECP, 128, bananana
	 */

	public String desencriptarConPKSC5(String algoritmo, String metodo, String tamanio, String texto) throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException
	{
		byte[] enByt = texto.getBytes("UTF8");
		
		KeyGenerator keyGen = KeyGenerator.getInstance(algoritmo);		
		keyGen.init(Integer.parseInt(tamanio));
		Key key = keyGen.generateKey();
		llaveEncrip = key;
		Cipher cipher = Cipher.getInstance(algoritmo + "/" + metodo + "/PKSC5Padding" );
		cipher.init(Cipher.DECRYPT_MODE, llaveEncrip);
        // Decrypt the ciphertext using the same key
        byte[] newPlainText = cipher.doFinal(texto.getBytes("UTF8"));
        return new String(cipher.doFinal(enByt), "UTF8");
	}
}
