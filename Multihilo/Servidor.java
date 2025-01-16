
import java.io.*;
import java.net.*;

public class Servidor {

	public static void main(String args[]) throws IOException {

		ServerSocket servidor; // CREE serverSocket
		servidor = new ServerSocket(60000);
		System.out.println("Inici Servidor en 60000");

		while (true) {
			Socket socketClient = new Socket();
			socketClient = servidor.accept(); // ESPERE AL CLIENT
			HiloServidor fil = new HiloServidor(socketClient); // FIL AT�N AL CLIENT
			fil.start(); // INICIE EL FIL
			System.out.println("Inici FilServidor");
		}

	}
}
