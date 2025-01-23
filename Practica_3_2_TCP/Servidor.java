package Practica_3_2_TCP;
import java.io.*;
import java.net.*;

class Servidor {
    // PORT EN EL QUE ESPERA CONNEXIONS
    static final int PORT = 5000;

    public Servidor() {
        try {
            // CREA SOCKET I ESPERA
            ServerSocket ssServidor = new ServerSocket(PORT);
            System.out.println("Escolte al port " + PORT);

            while (true) {
                Socket sCliente = ssServidor.accept();
                System.out.println("Serveisc al client");

                InputStream isEntrada = sCliente.getInputStream();
                DataInputStream disEntrada = new DataInputStream(isEntrada);
                OutputStream osEixida = sCliente.getOutputStream();
                DataOutputStream dosEixida = new DataOutputStream(osEixida);

                String missatge;
                while (true) {
                    missatge = disEntrada.readUTF();
                    System.out.println("Client: " + missatge);

                    if (missatge.equalsIgnoreCase("Hola")) {
                        dosEixida.writeUTF("Hola soc el servidor");
                    } else if (missatge.equalsIgnoreCase("Com estas")) {
                        dosEixida.writeUTF("Molt be");
                    } else if (missatge.equalsIgnoreCase("Adeu")) {
                        dosEixida.writeUTF("Fins ara");
                        break;
                    } else {
                        dosEixida.writeUTF("Missatge no reconegut");
                    }
                }

                System.out.println("Tancant connexió...");
                sCliente.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] arg) {
        new Servidor();
    }
}