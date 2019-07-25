package Solitaire;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * La clase carta es para almacenar la informacion de una sola carta
 */
public class Carta extends JPanel{
		// Members
		public int valor;
		public Palos palo;
		private BufferedImage imagen;
		private BufferedImage backImage;
		boolean estaVolteada;
		Point posicionDeDesplazamiento;
		
		/**
		 * Enumeracion de los valores de los palos
		 */
		public enum Palos {
			Picas(1, false),
			Corazones(2, true),
			Diamantes(3, true),
			Treboles(4, false);
			
			public int value;
			public boolean esRojo;
			
			 Palos(int valor, boolean esRojo) {
				this.value = valor;
				this.esRojo = esRojo;
			}
		}
		
		/**
		 * Convierte le valor de la carta a String
		 * @param {Integer} Valor de la carta
		 */
		public static String valueString(int valor) {
								
			if(valor == 12) return "J";
			if(valor == 13) return "Q";
			if(valor == 14) return "K";
			if(valor == 1) return "A";
			
			// valor entre 2 y 10
			return Integer.toString(valor);
		}
		/**
		 * @return {String} Descripcion de la carta
		 */
		public String toString() {
			return valueString(valor) + " de " + palo.name();
		}
		
		/**
		 * Devuelve un string que se usa para re-inicializar una carta
		 * @return {String} propiedades de la clse por separado
		 */
		public String guardarComoString() {
			return valueString(valor) + " de " + palo.name() + " de " + estaVolteada;
		}
		
		/**
		 * Constructor de la clase
		 * @param {Integer}  valor de la carta
		 */
		public Carta(int valor, Palos palos) {
			this.valor = valor;
			this.palo = palos;
			estaVolteada = false;

			try {
				// Trae la imagen de la carta
				URL url = getClass().getResource("../images/cartas/" + this.toString() + ".png");
				imagen = ImageIO.read(url);
				
				// Trae la imagen trasera de la carta (Joker)
				url = getClass().getResource("../images/cartas/joker.jpg");
				backImage = ImageIO.read(url);
				
				setBounds(0, 0, imagen.getWidth(), imagen.getHeight());
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			posicionDeDesplazamiento = new Point(0,0);
			setSize(new Dimension(100, 145));
			setOpaque(false);
		}
		
		/**
		 * Pone la carta boca abajo
		 */
		public void hide() {
			estaVolteada = true;
		}
		
		/**
		 * Pone la carta boca arriba
		 */
		public void show() {
			estaVolteada = false;
		}
		
		@Override
		protected void paintComponent(Graphics graficos) {
			super.paintComponent(graficos);
			
			BufferedImage img = imagen;
			if(estaVolteada) img = backImage;

			graficos.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	
}
