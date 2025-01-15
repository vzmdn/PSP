

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Servidor {
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private ObjectInputStream objectInputStream = null;

    public void comunica() {
        try {
            // Crear el servidor
            serverSocket = new ServerSocket(5000);
            System.out.println("Servidor abierto. Esperando conexiones...");
            socket = serverSocket.accept();
            System.out.println("Cliente conectado " + socket.getInetAddress());

            // Crear el flujo de entrada
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            System.out.println("Recibido del cliente: " + objectInputStream.readObject());

            // Recibir objeto
            Vehicle vehicle = (Vehicle) objectInputStream.readObject();
            System.out.println("Recibido del cliente: " + vehicle.getDni() + " " + vehicle.getMatricula() + " "
                    + vehicle.getMarca() + " " + vehicle.getModel() + " " + vehicle.getCombustible() + " "
                    + vehicle.getAny());

            // Cerrar el flujo de entrada
            objectInputStream.close();
            socket.close();

        } catch (SocketException se){
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.comunica();
    }
}
