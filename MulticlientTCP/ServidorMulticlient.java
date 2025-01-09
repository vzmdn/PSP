
import java.net.ServerSocket;

public class ServidorMulticlient {

    private static final int PORT = 5000;

    private static int clientsActuals = 0;

    public static synchronized void desconnectarClient() { clientsActuals--; }

    public static void main(String[] args) {

        try {

            ServerSocket servidor = new ServerSocket(PORT);

            while (true) {

                synchronized (ServidorMulticlient.class) {

                    while (clientsActuals >= 3) { ServidorMulticlient.class.wait(); }

                    clientsActuals++;

                }

                new Thread(new GestorClient(servidor.accept())).start();

            }

        } catch (Exception e) { e.printStackTrace(); }

    }

}