import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Cliente {
    private Socket socket = null;
    private ObjectOutputStream objectOutputStream = null;
    private boolean connected = false;

    public Cliente() {
    }

    public void comunica() {
        while (!connected) {
            try {
                Scanner tec = new Scanner(System.in);
                // Crear el socket
                System.out.print("introduce servidor (default: localhost): ");
                String servidor = tec.nextLine();
                if(servidor.isBlank()) servidor = "localhost";

                System.out.print("introduce puerto (default: 5000): ");
                int puerto = 5000;
                String input = tec.nextLine();
                if (!input.isEmpty()) {
                    try {
                        puerto = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                    }
                }

                socket = new Socket(servidor, puerto);
                System.out.println("Conectado al servidor");
                connected = true;

                // Crear el flujo de salida
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                // Enviar objeto
                Vehiculo vehiculo = new Vehiculo("12345678A", "1234ABC", "Seat", "Ibiza", "Gasolina", 2010);
                System.out.println("se va a enviar: " + vehiculo.getMatricula());

                // Escribir en el flujo de salida
                objectOutputStream.writeObject(vehiculo);
                System.out.println("Enviado al servidor: " + vehiculo.getMatricula());

                // Cerrar el flujo de salida
                objectOutputStream.close();
                socket.close();

            } catch (SocketException se) {
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.comunica();
    }

}
