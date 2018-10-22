import java.security.MessageDigest;

public class Servidor 
{
	private String mensaje;
	private byte[] llaveSimetrica;
	private byte[] llavePublica;
	private String[] algoritmos;

	public Servidor()
	{
		mensaje = null;
	}

	public void run()
	{
		procesarMensaje(mensaje);
	}
	
	private byte[] getDigest(String algorithm, byte[] buffer)
	{
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			digest.update(buffer);
			return digest.digest();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean verificar (String llaveSimetricaP)
	{
		if (algoritmos[0] == null)
		{
			return false;
		}
		else
		{
			getDigest(llaveSimetricaP, algoritmos[]);
			
			// escribir el metodo de verificar esta puta mierda.aaaddufbjdsfuhed
			return true;
		}		
	}

	public void procesarMensaje(String mensaje)
	{
		if (mensaje.equals("HOLA"))
		{
			System.out.println("OK");
		}
		else if (mensaje.startsWith("ALGORITMOS"))
		{
			int i = 0; 				

			if (mensaje.contains("AES"))
			{
				algoritmos[i]= "AES";
				i++;
			}
			if (mensaje.contains("Blowfish"))
			{
				algoritmos[i]= "Blowfish";
				i++;
			}
			if (mensaje.contains("RSA"))
			{
				algoritmos[i]= "RSA";
				i++;
			}
			if (mensaje.contains("HMACMD5"))
			{
				algoritmos[i]= "HMACMD5";
				i++;
			}
			if (mensaje.contains("HMACSHA1"))
			{
				algoritmos[i]= "HMACSHA1";
				i++;
			}
			if (mensaje.contains("HMACSHA256"))
			{
				algoritmos[i]= "HMACSHA256";
				i++;
			}
		}
		else if (mensaje.equals("OK"))
		{
			System.out.println(this.llaveSimetrica);
		}
		else if (verificacion)
		{

		}
		else if (consulta)
		{

		}
		else
		{
			if (verificar(mensaje))
			{
				System.out.println("OK");
			}
			else
			{
				System.out.println("ERRORTY");
			}
		}	
		synchronized(mensaje)
		{
			mensaje.notify();
		}
	}
}
