// Fil que representa el procés de descarregar i disparar el revòlver

public class Descargar extends Thread {

    private Pistola arma;

    private int cartucho;



    public Descargar(Pistola arma, int cartucho) {

        this.arma = arma;

        this.cartucho = cartucho; // Número de cartutxos que es descarreguen

    }



    public void run() {

        for (int i = 0; i < cartucho; i++) {

            arma.disparar(); // Es dispara el cartutx

            System.out.println("Descarregant el cartutx #" + (i + 1));

        }

    }

}