import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Servidor {
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private ObjectInputStream objectInputStream = null;

    public void comunica() {
        try {
            Scanner tec = new Scanner(System.in);

            // Crear el servidor
            String direccion = InetAddress.getLocalHost().getHostAddress();
            int puerto = 5000;
            System.out.print("introduce puerto (default: 5000): ");

            String input = tec.nextLine();
            if (!input.isEmpty()) {
                try {
                    puerto = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                }
            }

            serverSocket = new ServerSocket(puerto);

            System.out.println("Servidor abierto en " + direccion + ":" + puerto + "\nEsperando conexiones...");
            socket = serverSocket.accept();
            System.out.println("Cliente conectado " + socket.getInetAddress());

            // Crear el flujo de entrada
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            // Recibir objeto
            Vehiculo vehiculo = (Vehiculo) objectInputStream.readObject();
            System.out.println("Recibido del cliente:");
            System.out.println(vehiculo);

            // Cerrar el flujo de entrada
            objectInputStream.close();
            socket.close();

        } catch (SocketException se) {
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.comunica();
    }
}
