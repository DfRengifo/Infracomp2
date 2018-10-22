
public class Cliente extends Thread
{
	private String mensaje;
	private static Servidor servidor;
	private String llave


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

}
