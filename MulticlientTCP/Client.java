import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private static final String SERVER_ADDRESS = "localhost"; // Direcció del servidor
    private static final int SERVER_PORT = 5000;  // Porta del servidor

    public static void main(String[] args) {
        try {
            // Creem el socket per a la connexió amb el servidor
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connexio establerta amb el servidor.");

            // Crear objectes d'entrada i sortida
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter eixida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader teclat = new BufferedReader(new InputStreamReader(System.in));

            // Llegir el missatge que sol·licita la contrasenya
            System.out.println(entrada.readLine());  // El servidor sol·licita la contrasenya

            // Enviar la contrasenya al servidor
            String contrasenya = teclat.readLine();  // Llegeix la contrasenya de l'usuari
            eixida.println(contrasenya);

            // Llegir la resposta del servidor
            String resposta = entrada.readLine();
            System.out.println(resposta); // Mostra la resposta del servidor

            // Si la contrasenya és correcta, enviar un missatge al servidor
            if (resposta.equals("Autenticacio correcta.")) {
                System.out.println("Introduiu un missatge per al servidor:");
                String missatge = teclat.readLine();  // Llegeix el missatge de l'usuari
                eixida.println(missatge); // Envia el missatge al servidor
            }

            // Tancar la connexió
            entrada.close();
            eixida.close();
            socket.close();
            System.out.println("Connexio tancada.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
