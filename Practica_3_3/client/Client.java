import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client {
	// REBEM EN args MISSATGE, HOST DESTINACIO I PORT
	public static void main(String args[]) {

		DatagramSocket dSocket = null;

		// CONTROL ENTRADA ARGUMENTS
		if (args.length < 3) {
			System.out.println("Introduce argumentos: <missatge> <nom del Host> <numero de port>");
			System.exit(1);
		}
		try {
			// ENVIAMENT DEL DATAGRAMA
			dSocket = new DatagramSocket();
			byte[] missatgeEnviat = args[0].getBytes();
			InetAddress aHost = InetAddress.getByName(args[1]); // RECUPERE EL HOST DES DE L'ARGUMENT
			int serverPort = Integer.valueOf(args[2]).intValue(); // RECUPERE EL PORT DES DE L'ARGUMENT
			DatagramPacket dpEnviament = new DatagramPacket(missatgeEnviat, args[0].length(), aHost, serverPort); // DATAGRAMA A ENVIAR
			dSocket.send(dpEnviament); // ENVIE EL DATAGRAMA

			// RECEPCIO DEL DATAGRAMA
			byte[] missatgeRebut = new byte[1000];
			DatagramPacket dpResposta = new DatagramPacket(missatgeRebut, missatgeRebut.length);
			dSocket.receive(dpResposta); // REP EL DATAGRAMA
			System.out.println("Resposta: " + new String(dpResposta.getData()));

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} finally {
			if (dSocket != null) // SI EL SOCKET EXISTEIX
				dSocket.close(); // TANQUE
		}
	}
}