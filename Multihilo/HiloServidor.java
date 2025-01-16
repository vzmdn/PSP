
import java.io.*;
import java.net.*;

public class HiloServidor extends Thread {
	BufferedReader bufferedReader;
	PrintWriter printWriter;
	Socket socket = null;

	// CONSTRUCTOR HiloServidor
	public HiloServidor(Socket s) throws IOException {
		System.out.println("Cree fil servidor");
		socket = s;
		// CREE FLUXOS D'ENTRADA I EIXIDA
		printWriter = new PrintWriter(socket.getOutputStream(), true);
		bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	// LOGICA DEL SERVIDOR. ATIENDO AL CLIENTE
	public void run() {
		try {
			String cadena = "";
			boolean parar = false;

			while (!cadena.trim().equals("*") && !parar) {
				System.out.println("En fil: comunique amb: " + socket.toString());
				try {
					cadena = bufferedReader.readLine();
					System.out.println("En fil: llig cadena " + cadena);
					printWriter.println(cadena.trim().toUpperCase());// ENVIE CADENA AMB MAJ�SCULES
				} catch (IOException e) {
					System.out.println("ERROR: client desconnectat");
					parar = true;
				}

			}

			System.out.println("FI AMB " + socket.toString());

			// TANQUE FLUXES I SOCKET
			printWriter.close();
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("ERROR");
		}
	}
}