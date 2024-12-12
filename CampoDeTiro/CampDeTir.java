public class CampDeTir {

    public static void main(String[] args) { 

        Pistola arma = new Pistola();

        Cargar c = new Cargar(arma, arma.cartucho); // Fil encarregat de carregar la pistola

        Descargar d = new Descargar(arma, arma.cartucho); // Fil encarregat de descarregar-la i disparar

        c.start(); // Inici del fil de càrrega

        d.start(); // Inici del fil de descàrrega

    }

}

