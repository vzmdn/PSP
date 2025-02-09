import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Cliente {
    static Scanner tec = new Scanner(System.in);

    public static void main(String args[]) throws Exception {

        DatagramSocket dSocket = null;

        /*
         * if (args.length < 3) {
         * System.out.println("Introduce argumentos: <missatge> <servidor> <puerto>");
         * System.exit(1);
         * }
         */
        try {
            dSocket = new DatagramSocket();
            System.out.print("Introduce el mensaje a enviar: ");
            String mensaje = tec.nextLine();
            System.out.print("Introduce la contraseña: ");
            String pwdEncriptada = encriptar(tec.nextLine());

            byte[] mensajeEnviado = mensaje.getBytes();
            byte[] pwdEncriptadaBytes = pwdEncriptada.getBytes();
            byte[] pwdLengthBytes = ByteBuffer.allocate(4).putInt(pwdEncriptadaBytes.length).array();
            byte[] datosCombinados = new byte[4 + mensajeEnviado.length + pwdEncriptadaBytes.length];

            System.arraycopy(pwdLengthBytes, 0, datosCombinados, 0, 4);
            System.arraycopy(mensajeEnviado, 0, datosCombinados, 4, mensajeEnviado.length);
            System.arraycopy(pwdEncriptadaBytes, 0, datosCombinados, 4 + mensajeEnviado.length,
                    pwdEncriptadaBytes.length);

            InetAddress aHost = InetAddress.getByName("localhost");
            int serverPort = 5000;

            DatagramPacket dpEnviado = new DatagramPacket(datosCombinados, datosCombinados.length, aHost, serverPort);
            dSocket.send(dpEnviado);

            // RECEPCIÓN
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
        // Fixed secret key (for example, derived from a password)
        byte[] keyBytes = "1234567890123456".getBytes(); // 16 bytes = 128 bits
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        // Fixed IV (must be 16 bytes for AES)
        byte[] ivBytes = "abcdefghijklmnop".getBytes(); // 16 bytes = 128 bits
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        // Encriptar la contraseña
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        String pwdEncriptada = Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes()));

        return pwdEncriptada;
    }
}