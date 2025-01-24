import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Servidor {
    public static void main(String args[]) {
        String str = "password";
        String password = "";
        try {
            password = encriptar(str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DatagramSocket dSocket = null;

        /*
         * if (args.length < 1) {
         * System.out.print("Introduce <puerto> como argumento");
         * System.exit(1);
         * }
         */

        try {
            // socket_no -> PUERTO
            int socket_no = 5000;
            dSocket = new DatagramSocket(socket_no);
            byte[] mensajeRecibido;

            while (true) {
                mensajeRecibido = new byte[1000];
                DatagramPacket dpRecibido = new DatagramPacket(
                        mensajeRecibido,
                        mensajeRecibido.length);
                dSocket.receive(dpRecibido);
                InetAddress senderAddress = dpRecibido.getAddress();

                byte[] datosRecibidos = dpRecibido.getData();
                ByteBuffer wrapped = ByteBuffer.wrap(datosRecibidos, 0, 4);
                int pwdLength = wrapped.getInt();

                int mensajeLength = datosRecibidos.length - 4 - pwdLength;
                byte[] mensajeBytes = new byte[mensajeLength];
                byte[] passwordBytes = new byte[pwdLength];

                System.arraycopy(datosRecibidos, 4, mensajeBytes, 0, mensajeLength);
                System.arraycopy(datosRecibidos, 4 + mensajeLength, passwordBytes, 0, pwdLength);

                String mensajeOriginal = new String(mensajeBytes).trim();
                String passwordRecibida = new String(passwordBytes).trim();

                if (passwordRecibida.equals(password)) {
                    System.out.println("Recibido del cliente " + senderAddress + ": " + mensajeOriginal);

                    System.out.println(
                            "Recibido del cliente " + senderAddress + ": " + new String(dpRecibido.getData()).trim());

                    DatagramPacket dpRespuesta = new DatagramPacket(
                            dpRecibido.getData(),
                            dpRecibido.getLength(),
                            dpRecibido.getAddress(),
                            dpRecibido.getPort());

                    dSocket.send(dpRespuesta);
                } else {
                    System.out.println("Tornen les tropes a casa, hi ha infiltrats");
                    break;
                }
            }

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
