package Solitaire;

import Solitaire.Carta.Palos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * La clase maso guarda las 52 cartas
 */
public class mazo {
	
	ArrayList<Carta> cartas;
	
	/**
	 * Constructor de la clase
	 */
	public mazo() {
		
		// Crea las 52 carta a partir de la clase carta
		cartas = new ArrayList<>();
		
		for(Palos palo : Palos.values()) {
			for(int valor = 1; valor <= 14; ++valor) {
				if(valor != 11)
					cartas.add(new Carta(valor, palo));
			}
		}
	}

	/**
	 * Mezcla las cartas
	 */
	public void mezclar() {
		Random randIndex = new Random();
		int size = cartas.size();
		
		for(int shuffles = 1; shuffles <= 20; ++shuffles)
			for (int i = 0; i < size; i++) 
				Collections.swap(cartas, i, randIndex.nextInt(size));
		
	}
	
	/**
	 * Retorna el tamaño del mazo
	 * @return {Integer} numero de cartas del mazo
	 */
	public int size() {
		return cartas.size();
	}
	
	/**
	 * saca cartas del mazo, si no esta vacio
	 * @return la primera carta del mazo
	 */
	public Carta sacarCarta() {
		Carta c = cartas.get(0);
		cartas.remove(0);

		return c;
	}
	
}
