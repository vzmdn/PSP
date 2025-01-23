package Practica_3_1_TCP;

import java.io.*;
import java.net.*;

public class Servidor {
    static final int PUERTO = 5000;
    static int nCli = 3;

    public Servidor(){
        try {
            ServerSocket skServidor = new ServerSocket(PUERTO);
            System.out.println("Escucho el puerto " + PUERTO);
            for (int i = 1; i <= nCli; i++) {
                Socket skCliente = skServidor.accept();
                System.out.println("Sirvo al cliente " + i);
                OutputStream aux = skCliente.getOutputStream();
                DataOutputStream flujo = new DataOutputStream(aux);
                flujo.writeUTF("Hola cliente " + i);
                skCliente.close();
            }
            System.out.println("Demasiados clientes por hoy");
            skServidor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Servidor();
    }
}
