import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Cliente {
    static Scanner tec = new Scanner(System.in);

    public static void main(String args[]) {

        DatagramSocket dSocket = null;

        /*
         * if (args.length < 3) {
         * System.out.println("Introduce argumentos: <missatge> <servidor> <puerto>");
         * System.exit(1);
         * }
         */
        try {
            // ENVIO
            dSocket = new DatagramSocket();
            System.out.print("Introduce un mensaje: ");
            String mensaje = tec.nextLine();
            System.out.print("Introduce la contraseña: ");
            String password = tec.nextLine();
            String pwdEncriptada = "";
            try {
                pwdEncriptada = encriptar(password);
            } catch (Exception e) {
                System.out.println("error al encriptar: " + e.getMessage());
                System.exit(1);
            }
            byte[] mensajeEnviado = (mensaje).getBytes();
            byte[] pwdEncriptadaBytes = pwdEncriptada.getBytes();
            byte[] pwdLengthBytes = ByteBuffer.allocate(4).putInt(pwdEncriptadaBytes.length).array();
            byte[] datosCombinados = new byte[4 + mensajeEnviado.length + pwdEncriptadaBytes.length];
            System.arraycopy(
                    pwdLengthBytes,
                    0,
                    datosCombinados,
                    0,
                    4);
            System.arraycopy(
                    mensajeEnviado,
                    0,
                    datosCombinados,
                    0,
                    mensajeEnviado.length);
            System.arraycopy(
                    pwdEncriptadaBytes,
                    0,
                    datosCombinados,
                    mensajeEnviado.length,
                    pwdEncriptadaBytes.length);
            // aHost -> HOST
            InetAddress aHost = InetAddress.getByName("localhost");
            // serverPort -> PUERTO
            int serverPort = 5000;

            // Creación del datagrama
            DatagramPacket dpEnviado = new DatagramPacket(
                    datosCombinados,
                    datosCombinados.length,
                    aHost, serverPort);
            dSocket.send(dpEnviado);

            // RECPCIÓN
            byte[] mensajeRecibido = new byte[1000];
            DatagramPacket dpRecibido = new DatagramPacket(mensajeRecibido, mensajeRecibido.length);
            dSocket.receive(dpRecibido);
            System.out.println("Respuesta del servidor: " + new String(dpRecibido.getData()));

        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (dSocket != null)
                dSocket.close();
        }
    }

    public static String encriptar(String password) throws Exception {
        // Generar clave AES
        SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();

        // Encriptar la contraseña
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        String pwdEncriptada = Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes()));

        return pwdEncriptada;
    }
}