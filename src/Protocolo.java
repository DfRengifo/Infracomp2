import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Protocolo {


	public static void procesar(BufferedReader pIn,PrintWriter pOut) throws IOException {
		String inputLine, outputLine;
		int estado = 0;
		while (estado < 6 && (inputLine = pIn.readLine()) != null) {
			switch (estado) {
			case 0:
				if (inputLine.equalsIgnoreCase("HOLA")) {
					outputLine = "OK";
					estado++;
				} else {
					outputLine = "ERROR-EsperabaHola";
					estado = 0;
				}
				break;
			case 1:
				try {
					String[] algoritmos = inputLine.split(":");
					
					outputLine = "OK";
					estado++;
				} catch (Exception e) {
					outputLine = "ERROR";
					estado = 0;
				}
				break;
			case 2:
				if (inputLine.equalsIgnoreCase("Certificado Cliente")) {
					System.out.println("OK");
					outputLine = "Certificado Servidor";
					estado++;
				} else {
					outputLine = "ERROR";
					estado = 0;
				}
				break;
			case 3:
				if (inputLine.equalsIgnoreCase("OK")) {
					
					outputLine = "Llave simetrica con Kc+";
					estado++;
				} else {
					outputLine = "ERROR";
					estado = 0;
				}
				break;
			case 4:
				if (inputLine.equalsIgnoreCase("Llave simetrica con Ks+")) {
					
					outputLine = "OK";
					estado++;
				} else {
					outputLine = "ERROR";
					estado = 0;
				}
				break;
			case 5:
				if (inputLine.equalsIgnoreCase("consulta cifrada y el HMAC")) {
					
					outputLine = "OK:DEBE O OK:PAZYASLVO";
					estado++;
				} else {
					outputLine = "ERROR";
					estado = 0;
				}
				break;
			default:
				outputLine = "ERROR";
				estado = 0;
				break;
			}
			pOut.println(outputLine);
		}
	}
}


