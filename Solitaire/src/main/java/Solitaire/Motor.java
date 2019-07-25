package Solitaire;

import Solitaire.Carta.Palos;
import Solitaire.Pila.TipoPila;

import java.util.ArrayList;

/**
 * Clase motor del juego donde se encuntra toda la logica
 * Contiene todas las intancias del juego
 */
public class Motor {

	ArrayList<Pila> pilas;
	ArrayList<Pila> finalPilas;
	Pila DibujarPila, getPila;
	ArrayList<Pila> todasLasPilas;
	public final int numeroPila = 7;
	public mazo mazo;

	/**
	 * Class constructor
	 */
	public Motor() {
		reiniciarElJuego();
	}

	/**
	 * Reinicia el juego
	 */
	public void reiniciarElJuego() {
		mazo = new mazo();
		mazo.mezclar();

		DibujarPila = new Pila(120);
		DibujarPila.setCompensar(0);

		getPila = new Pila(180);
		getPila.setCompensar(0);

		finalPilas = new ArrayList<>();
		pilas = new ArrayList<>();

		todasLasPilas = new ArrayList<>();
		todasLasPilas.add(DibujarPila);
		todasLasPilas.add(getPila);
	}

	/**
	 * Setup the initial juego state
	 */
	public void montajeJuego() {
		// Generate pilas
		DibujarPila.tipo = TipoPila.elegir;
		getPila.tipo = TipoPila.Get;

		for (int i = 1; i <= numeroPila; ++i) {
			Pila p = new Pila(120);

			// Añade una carta a la pila actual.
			for (int j = 1; j <= i; ++j) {
				Carta carta = mazo.sacarCarta();
				p.añadirCarta(carta);

				if (j != i)
					carta.hide();
				else
					carta.show();
			}

			pilas.add(p);
			todasLasPilas.add(p);
		}

		for (Carta.Palos palos : Palos.values()) {
			Pila p = new Pila(100);
			p.setCompensar(0);
			p.tipo = Pila.TipoPila.Final;
			finalPilas.add(p);
			todasLasPilas.add(p);
		}

		while (mazo.size() > 0) {
			Carta carta = mazo.sacarCarta();
			carta.hide();
			DibujarPila.añadirCarta(carta);
		}
	}

	/**
	 * Elije una carta de la pila dibujada para ponerla en la nueva pila
	 */
	public void drawCard() {
		if (!DibujarPila.cartas.isEmpty()) {
			Carta drew = DibujarPila.elegirCarta();
			drew.estaVolteada = false;
			getPila.añadirCarta(drew);
		}
	}

	/**
	 * Si la pila de las carta volteadas es clickeada la carta tope se dara vuelta
	 */
	public void clickPile(Pila p) {
		if (!p.cartas.isEmpty()) {
			Carta c = p.cartas.get(p.cartas.size() - 1);
			if (c.estaVolteada) {
				c.estaVolteada = false;
			}
		}
	}

	/**
	 * Voltea la pila y la pone denuevo para elegir
	 */
	public void turnGetPile() {
		if (!DibujarPila.cartas.isEmpty()) return;

		while (!getPila.cartas.isEmpty()) {
			Carta c = getPila.elegirCarta();
			c.estaVolteada = true;

			DibujarPila.añadirCarta(c);
		}
	}

	/**
	 * Revisa si todas las cartas esta en la pila corecta
	 *
	 * @return {Boolean}
	 */
	public boolean checkWin() {
		for (Pila p : finalPilas) {
			if (p.cartas.size() != 13)
				return false;
		}
		return true;
	}
}