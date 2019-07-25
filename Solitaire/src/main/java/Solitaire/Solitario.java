package Solitaire;
/**
 * Clase main
 * */
public class Solitario {

    Motor juego;
    Tablero tablero;

    /**
     * Constructor de la Clase
     * */

    public Solitario() {
        juego = new Motor();
        tablero = new Tablero(juego);
    }

    public static void main(String[] args) {
        new Solitario();
    }
}
