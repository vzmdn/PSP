package Practica_3_2;
import java.io.*;
import java.net.*;
import java.util.Scanner;

class Client {
    // NOM MAQUINA I PORT
    static final String HOST = "localhost";
    static final int PORT = 5000;

    public Client(String host) {
        try {
            // ES CREA EL SOCKET
            Socket sCliente = new Socket(HOST, PORT);
            System.out.println("Conectat al servidor...");

            // CREE ELS FLUXOS D'ENTRADA I EIXIDA PER AL SOCKET
            InputStream isEntrada = sCliente.getInputStream();
            DataInputStream disEntrada = new DataInputStream(isEntrada);
            OutputStream osEixida = sCliente.getOutputStream();
            DataOutputStream dosEixida = new DataOutputStream(osEixida);

            Scanner scanner = new Scanner(System.in);
            String missatge;

            while (true) {
                System.out.print("Escriu un missatge: ");
                missatge = scanner.nextLine();
                dosEixida.writeUTF(missatge);

                if (missatge.equalsIgnoreCase("Adeu")) {
                    System.out.println("Servidor: " + disEntrada.readUTF());
                    break;
                }

                System.out.println("Servidor: " + disEntrada.readUTF());
            }

            // TANQUE EL SOCKET
            System.out.println("Tancant connexió...");
            sCliente.close();
        } catch (Exception e) {
            System.out.println("Error: no s'ha pogut connectar a " + host + ":" + PORT);
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] arg) {
        if (arg.length != 1) {
            System.out.println("Error: deus introduir un paràmetre que és el HOST");
            return;
        }
        new Client(arg[0]);
    }
}