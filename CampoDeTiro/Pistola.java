public class Pistola {

    public int cartucho = 6; // Nombre màxim de cartutxos (revolta completa)

    private boolean enposicion = true; // Estat: si està en posició per apuntar o disparar



    // Mètode sincronitzat per a disparar

    public synchronized void disparar() {

        while (!enposicion || cartucho == 0) { // Si no es pot disparar o no hi ha cartutxos

            try {

                wait(); // Esperar fins que es puga disparar

            } catch (InterruptedException e) {

                System.out.println("Interrupció durant el disparar!");

            }

        }

        enposicion = false; // Estat canviat a no en posició

        cartucho--; // Reduïm el nombre de cartutxos

        System.out.println("Disparat! Cartutxos restants: " + cartucho);

        notifyAll(); // Notifiquem a altres fils que poden operar

    }



    // Mètode sincronitzat per a apuntar

    public synchronized void apuntar() {

        while (enposicion) { // Si està ja apuntant, esperar

            try {

                wait();

            } catch (InterruptedException e) {

                System.out.println("Interrupció durant l'apuntar!");

            }

        }

        enposicion = true; // Estat canviat a en posició

        System.out.println("Arma en posició per apuntar!");

        notifyAll(); // Notifiquem a altres fils

    }

}

