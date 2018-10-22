import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor 
{
	public int id;
	public static void main (String args[]) throws IOException 
	{ 		
		ServerSocket ss = new ServerSocket();
		boolean continuar = true;
		while (continuar) 
		{
			new ThreadServidor(ss.accept(), this.id).start();
		}
		ss.close();
	}

	//thread

	public class ThreadServidor extends Thread{
		
		private Socket sktCliente;
		private int id;

		public ThreadServidor(Socket pSocket,int pId) {
			this.id = pId;
			this.sktCliente = pSocket;
		}

		public void run() {
			System.out.println("Inicio de nuevo thread." + id);
			try {
				PrintWriter escritor = new
						PrintWriter(sktCliente.getOutputStream(), true);
				BufferedReader lector = new BufferedReader(new
						InputStreamReader(sktCliente.getInputStream()));
				procesar(lector,escritor);escritor.close();
				lector.close();
				sktCliente.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
