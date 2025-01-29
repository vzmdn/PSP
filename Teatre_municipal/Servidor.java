import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Servidor {
    static final int PUERTO = 4500;
    static int nCli = 3;
    private Map<String, Integer> butacasDisponibles;

    public Servidor() {
        butacasDisponibles = new HashMap<>();
        butacasDisponibles.put("GAL", 8);
        butacasDisponibles.put("CEN", 54);
        butacasDisponibles.put("LAT1", 4);
        butacasDisponibles.put("LAT2", 4);
        butacasDisponibles.put("VIP1", 3);
        butacasDisponibles.put("VIP2", 3);

        try {
            ServerSocket skServidor = new ServerSocket(PUERTO);
            System.out.println("Escucho el puerto " + PUERTO);

            while (true) {
                Socket skCliente = skServidor.accept();
                

                InputStream auxIn = skCliente.getInputStream();
                DataInputStream flujoIn = new DataInputStream(auxIn);
                String mensaje = flujoIn.readUTF();

                OutputStream auxOut = skCliente.getOutputStream();
                DataOutputStream flujoOut = new DataOutputStream(auxOut);

                System.out.println("Cliente: " + mensaje);

                if (mensaje.toLowerCase().equals("fin")) {
                    flujoOut.writeUTF("Fin");
                    skCliente.close();
                    continue;
                }

                if (mensaje.toLowerCase().equals("ver butacas")) {
                    StringBuilder respuesta = new StringBuilder();
                    for (Map.Entry<String, Integer> entry : butacasDisponibles.entrySet()) {
                        respuesta.append(entry.getKey()).append(": ").append(entry.getValue()).append(" disponibles\n");
                    }
                    flujoOut.writeUTF(respuesta.toString());
                } else if (butacasDisponibles.containsKey(mensaje)) {
                    int disponibles = butacasDisponibles.get(mensaje);
                    if (disponibles > 0) {
                        butacasDisponibles.put(mensaje, disponibles - 1);
                    } else {
                        flujoOut.writeUTF("Butacas agotadas");
                    }
                }
                else if(mensaje.toLowerCase().equals("hola")){
                    flujoOut.writeUTF("Hola, soy el servidor");

                } else {
                    flujoOut.writeUTF("Error");
                }

                skCliente.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Servidor();
    }
}