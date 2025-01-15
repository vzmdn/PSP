
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class GestorClient implements Runnable {

    private Socket clientSocket;

    public GestorClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override

    public void run() {

        try {

            BufferedReader entrada = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter eixida = new PrintWriter(clientSocket.getOutputStream(), true);

            eixida.println("Introduiu la contrasenya:");

            String codi = entrada.readLine();

            if (!"EndavantLoNostreReiJaume1".equals(codi)) {
                //si la contraseña es incorrecta

                //imprimir la contraseña recibida
                System.out.println("intent de connexio amb codi incorrecte: " + codi);

                //enviar mensaje de error al cliente
                eixida.println("Codi incorrecte. Connexio rebutjada.");

                //cerrar la conexión
                clientSocket.close();
                return;
            }

            System.out.println(clientSocket.getInetAddress() + " autenticat correctament.");
            eixida.println("Autenticacio correcta.");

            String missatge = entrada.readLine();

            System.out.println("Missatge rebut: " + missatge);
            Log.registrar("Missatge rebut: " + missatge + "\n");

            entrada.close();
            eixida.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}