import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    static final String HOST = "localhost";
    static final int PUERTO = 4500;

    public Cliente() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                Socket skCliente = new Socket(HOST, PUERTO);

                System.out.println("Introduce el codigo de la butaca que quieres reservar: ");
                String mensaje = scanner.nextLine();

                OutputStream auxOut = skCliente.getOutputStream();
                DataOutputStream flujoOut = new DataOutputStream(auxOut);
                flujoOut.writeUTF(mensaje);

                InputStream auxIn = skCliente.getInputStream();
                DataInputStream flujoIn = new DataInputStream(auxIn);
                String respuesta = flujoIn.readUTF();
                System.out.println("Servidor: " + respuesta);

                skCliente.close();

                if (mensaje.equals("Fino")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Cliente();
    }
}