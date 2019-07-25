package Solitaire;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Pila extends JLayeredPane {

	Carta base;
	ArrayList<Carta> cartas;
	int compensar = 15;
	Carta.Palos filtroPalos;
	int ancho;
	Pila padre;
	TipoPila tipo;
	
	enum TipoPila {Normal, elegir, Get, Final};
	
	/**
	 * Constructor de la clase
	 * @param ancho
	 */
	public Pila(int ancho) {
		cartas = new ArrayList<Carta>();
		this.ancho = ancho;
		
		base = new Carta(100, Carta.Palos.Picas);
		add(base, 1, 0);
		
		tipo = TipoPila.Normal;
	}
	
	/**
	 * Añade una carta al tope de la clase.
	 * @param {Carta} c La carta añadida
	 */
	public void añadirCarta(Carta c) {
		c.setLocation(0, compensar * cartas.size());
		cartas.add(c);

		this.add(c, 1, 0);
		actualizarSize();
	}
	
	/**
	 * Remueve la carta de la pila
	 * @param {Carta} c La carta se retira
	 */
	public void removerCarta(Carta c) {
		cartas.remove(c);
		this.remove(c);
		
		actualizarSize();
	}
	
	/**
	 * Retorna la carta tope de la pila, sin removerla
	 * @return {Carta}
	 */
	public Carta getCartaTope() {
		return cartas.get(cartas.size() - 1);
	}
	
	/**
	 * Elije una carta de la pila. No debe estar vacia la pila.
	 * @return La primera carta de la pila
	 */
	public Carta elegirCarta() {
		Carta c = cartas.get(0);
		removerCarta(c);

		return c;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
		actualizarSize();
	}
	
	/**
	 * Actualiza la pila basado en el numero de cartas en ella.
	 */
	public void actualizarSize() {
		int height = base.getSize().height;
		
		if(!cartas.isEmpty()) {
			height += compensar * (cartas.size() - 1);
		}

		this.setPreferredSize(new Dimension(ancho, height));
		this.setSize(ancho, height);
	}
	
	
	/**
	 * Cambia el valor entero de compensar
	 * @param {Integer} compensar
	 */
	public void setCompensar(int compensar) {
		this.compensar = compensar;
		actualizarSize();
	}
	
	/**
	 * Parte la pila en dos pilas
	 * The top half is kept in this pile
	 * @param {Carta} Primera carta de donde empieza la roptura
	 * @return regresa una pila padre
	 */
	public Pila dividir(Carta first) {
		Pila p = new Pila(100);
		
		for(int i = 0; i < cartas.size(); ++i) {
			if(cartas.get(i) == first) {
				for(; i < cartas.size();) {
					p.añadirCarta(cartas.get(i));
					removerCarta(cartas.get(i));
				}
			}
		}
		
		p.padre = this;
		
		return p;
	}
	
	/**
	 * Une la actual pila con otra
	 * La pila dada se coloca en el tope
	 * @param {Pila} p La pila a unir
	 */
	public void unir(Pila p) {
		for(Carta c: p.cartas)
			añadirCarta(c);
		
		actualizarSize();
	}
	
	/**
	 * Busca una carta segun el palo y el valor
	 * @param {int} valor
	 * @param {String} nombrePalo
	 * @return {Carta} La carta Hallada
	 */
	public Carta buscarCarta(int valor, String nombrePalo) {
		
		for(Carta c: cartas) {
			if(c.valor == valor && c.palo.name().equals(nombrePalo))
				return c;
		}
		
		return null;
	}
	
	/**
	 * Miras i la pila esta vacia o no
	 * @return {Boolean} verdadero si la pila esta vacia si no retorna falso
	 */
	public boolean estaVacio() {
		return cartas.size() == 0;
	}
	
	/**
	 * Condicion para movimineto de pila
	 */
	public boolean aceptaPila(Pila p) {
		// Can not add to itself
		if(this == p) return false;
		
		Carta nuevaCarta = p.cartas.get(0);
		Carta topCarta;
		
		switch(tipo) {
			case Normal:
				// If it's empty it can only receive a King
				if(cartas.isEmpty()) {
					if(nuevaCarta.valor == 14) return true;
					return false;
				}
				
				topCarta = cartas.get(cartas.size() - 1);
				if(topCarta.estaVolteada) return false;
				
				// Different color, consecutive values, descending
				if(topCarta.palo.esRojo != nuevaCarta.palo.esRojo)
				   if(topCarta.valor == nuevaCarta.valor + 1 ||
				      topCarta.valor ==  12 && nuevaCarta.valor == 10) {
					   return true;				
				   }
			break;
			
			case Final:
				
				// Merge with a single card
				if(p.cartas.size() > 1) return false;
				
				// Start with an ace
				if(cartas.isEmpty() && nuevaCarta.valor == 1) {
					filtroPalos = nuevaCarta.palo;
					return true;
				}
				
				// Has to be the same color
				if(filtroPalos != nuevaCarta.palo) return false;
				
				// Consecutive values, ascending
				topCarta = cartas.get(cartas.size() - 1);
				if(topCarta.valor == nuevaCarta.valor - 1 ||
				   topCarta.valor ==  10 && nuevaCarta.valor == 12) {
					return true;
				}
			break;
		}
		return false;
	}
	
	public boolean isOptimizedDrawingEnabled() {
        return false;
	}

	@Override
	/**
	 * Retorna un string que contiene todas las cartas de la pila.
	 * @return {String} Cartas separadas por "-" cartas
	 */
	public String toString() {
		String resultado = "";
		
		resultado += base.guardarComoString() + "-";
		
		for(Carta c : cartas) {
			resultado += c.guardarComoString() + "-";
		}
		
		return resultado;
	}
	
	// Change baseline, so pile is aligned to top
	@Override
	public BaselineResizeBehavior getBaselineResizeBehavior() {
	    return BaselineResizeBehavior.CONSTANT_ASCENT;
	}

	@Override
	public int getBaseline(int width, int height) {
	    return 0;
	}
}
