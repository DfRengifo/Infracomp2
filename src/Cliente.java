import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Random;

import javax.crypto.*;
import javax.security.auth.x500.X500Principal;

import org.bouncycastle.x509.X509V1CertificateGenerator;

public class Cliente extends Thread
{
	private String mensaje;
	private String llave;
	private Key llaveEncrip;

	public Cliente() {

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
	
	//Creación del Certificado Digital
	public X509Certificate crearCD(String llave)
	{
		Random r = new Random();
		BigInteger serialNumber = new BigInteger(llave.length(), r);
		X509V1CertificateGenerator certGen = new X509V1CertificateGenerator();
		X500Principal dnName = new X500Principal("CN=Test CA Certificate");
		
		KeyPair kp = new KeyPair(llavePrivada, llavePublica);
		
		String algoritmo = "MD5"
		certGen.setSerialNumber(serialNumber);
		certGen.setIssuerDN(dnName);
		certGen.setSubjectDN(dnName);
		certGen.setPublicKey(llavePublica);
		certGen.setSignatureAlgorithm(algoritmo);
		
	}
	
	
	//Cifrado Simétrico
	
	/*
	 * Metodo para encriptar dado un algoritmo, metodo de encriptacion, tamaño en bits y un texto
	 * Ej: AES, ECB, 128, bananana
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
	 * Ej: AES, ECB, 128, bananana
	 */

	public String desencriptarConPKSC5(String algoritmo, String metodo, String tamanio, String texto) throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException
	{
		byte[] enByt = texto.getBytes("UTF8");
		
		KeyGenerator keyGen = KeyGenerator.getInstance(algoritmo);		
		keyGen.init(Integer.parseInt(tamanio));
		Cipher cipher = Cipher.getInstance(algoritmo + "/" + metodo + "/PKSC5Padding" );
		cipher.init(Cipher.DECRYPT_MODE, llaveEncrip);
        // Decrypt the ciphertext using the same key
        byte[] newPlainText = cipher.doFinal(texto.getBytes("UTF8"));
        return new String(cipher.doFinal(enByt), "UTF8");
	}
}
