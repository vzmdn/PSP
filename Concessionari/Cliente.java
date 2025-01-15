import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class Cliente {
    private Socket socket = null;
    private ObjectOutputStream objectOutputStream = null;
    private boolean connected = false;

    public Cliente() {
    }

    public void communicate() {
        while (!connected) {
            try {
                // Crear el socket
                socket = new Socket("localhost", 5000);
                System.out.println("Conectado al servidor");
                connected = true;

                // Crear el flujo de salida
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                // Enviar objeto
                Vehicle vehicle = new Vehicle("12345678A", "1234ABC", "Seat", "Ibiza", "Gasolina", 2010);
                System.out.println("se va a enviar: " + vehicle.getMatricula());

                // Escribir en el flujo de salida
                objectOutputStream.writeObject(vehicle);
                System.out.println("Enviado al servidor: " + vehicle.getMatricula());

            

                // Cerrar el flujo de salida
                objectOutputStream.close();
                socket.close();

            } catch (SocketException se) {
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.communicate();
    }

}
