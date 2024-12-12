// Fil que representa el procés de carregar el revòlver

public class Cargar extends Thread {

    private Pistola arma;

    private int cartucho;



    public Cargar(Pistola arma, int cartucho) {

        this.arma = arma;

        this.cartucho = cartucho; // Número de cartutxos que s'estan carregant

    }



    public void run() {

        for (int i = 0; i < cartucho; i++) {

            arma.apuntar(); // Es posa en posició

            System.out.println("Carregant el cartutx #" + (i + 1));

        }

    }

}