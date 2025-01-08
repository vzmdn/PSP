import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Servidor {
	public static void main(String args[]) {

		DatagramSocket dSocket = null;

		if (args.length < 1) { // REBEM EN args EL PORT
			System.out.println("Introduce <numero de port>");
			System.exit(1);
		}

		try {
			int socket_no = Integer.valueOf(args[0]).intValue(); // GUARDE EL N�MERO DE PORT
			dSocket = new DatagramSocket(socket_no);
			byte[] missatgeRebut;

			while (true) {
				missatgeRebut = new byte[1000];
				DatagramPacket dpRebut = new DatagramPacket(missatgeRebut, missatgeRebut.length);
				dSocket.receive(dpRebut);
				InetAddress senderAddress = dpRebut.getAddress();
				System.out.println("Rep del client" + senderAddress + ": " + new String(dpRebut.getData()).trim());

				// ENVIE EL DATAGRAMA a dpRebut.getAddress() I AL PORT dpRebut.getPort() (DEL QUE HE REBUT)
				// ENVIE EL MATEIX MISSATGE QUE REP: dpRebut.getData()
				DatagramPacket dpResposta = new DatagramPacket(dpRebut.getData(), dpRebut.getLength(),
						dpRebut.getAddress(), dpRebut.getPort());
						
				dSocket.send(dpResposta);
			}
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