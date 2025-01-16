
import java.net.*;
import java.io.*;

public class Cliente {

	public static void main(String args[]) throws IOException {
		try (
				Socket socket = new Socket("localhost", 60000)) {

			// CREE FLUXOS D'ENTRADA I EIXIDA
			PrintWriter feixida = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader fentrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// FLUX PER A L'ENTRADA PER TECLAT
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			String cadena, cadenaRebuda;

			System.out.println("Introdueix cadenes i rep en majuscula. Introdueix * per a finalitzar");
			do {
				System.out.println("Introdueix cadena: ");
				cadena = in.readLine();
				feixida.println(cadena);
				cadenaRebuda = fentrada.readLine();
				System.out.println("Rep: " + cadenaRebuda);

			} while (!(cadena.contentEquals("*")));

			System.out.println("Fi comunicacio");

			// TANQUE FLUXOS I SOCKET
			feixida.close();
			fentrada.close();
			in.close();
			socket.close();
		}
	}
}
