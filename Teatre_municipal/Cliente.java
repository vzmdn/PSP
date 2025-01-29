import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    static final String HOST = "localhost";
    static final int PUERTO = 5000;

    public Cliente() {
        Socket skCliente = null;
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                skCliente = new Socket(HOST, PUERTO);

                System.out.println("Introduce el codigo de la butaca que quieres reservar: ");
                String mensaje = scanner.nextLine();

                OutputStream auxOut = skCliente.getOutputStream();
                DataOutputStream flujoOut = new DataOutputStream(auxOut);
                flujoOut.writeUTF(mensaje);

                InputStream auxIn = skCliente.getInputStream();
                DataInputStream flujoIn = new DataInputStream(auxIn);
                try {
                    String respuesta = flujoIn.readUTF();
                    System.out.println("Servidor: " + respuesta);
                } catch (EOFException eof) {
                    System.out.println("Connection closed by server.");
                }

                if (mensaje.toLowerCase().equals("fin")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (skCliente != null) {
                try {
                    skCliente.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Cliente();
    }
}