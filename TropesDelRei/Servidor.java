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
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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
            int socket_no = 5000;
            dSocket = new DatagramSocket(socket_no);
            byte[] mensajeRecibido;

            System.out.println("Servidor iniciado en el puerto " + socket_no + ", esperando mensaje");

            while (true) {
                mensajeRecibido = new byte[1000];
                DatagramPacket dpRecibido = new DatagramPacket(mensajeRecibido, mensajeRecibido.length);
                dSocket.receive(dpRecibido);
                InetAddress senderAddress = dpRecibido.getAddress();

                byte[] datosRecibidos = dpRecibido.getData();
                ByteBuffer wrapped = ByteBuffer.wrap(datosRecibidos, 0, 4);
                int pwdLength = wrapped.getInt();

                if (pwdLength < 0 || pwdLength > datosRecibidos.length - 4) {
                    System.out.println("Invalid password length received: " + pwdLength);
                    continue;
                }

                int mensajeLength = dpRecibido.getLength() - 4 - pwdLength;
                byte[] mensajeBytes = new byte[mensajeLength];
                byte[] passwordBytes = new byte[pwdLength];

                System.arraycopy(datosRecibidos, 4, mensajeBytes, 0, mensajeLength);
                System.arraycopy(datosRecibidos, 4 + mensajeLength, passwordBytes, 0, pwdLength);

                String mensajeOriginal = new String(mensajeBytes).trim();
                String passwordRecibida = new String(passwordBytes).trim();

                if (passwordRecibida.equals(password)) { // Replace with actual password check
                    System.out.println("Recibido del cliente " + senderAddress + ": " + mensajeOriginal);

                    DatagramPacket dpRespuesta = new DatagramPacket(dpRecibido.getData(), dpRecibido.getLength(),
                            dpRecibido.getAddress(), dpRecibido.getPort());
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
