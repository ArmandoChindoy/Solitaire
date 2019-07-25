package Solitaire;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Tablero extends JFrame implements  MouseListener,MouseMotionListener {

	JPanel areaDeJuego;
		JPanel columns;
		JPanel topColumns;
		JPanel marcador;
		JPanel tablaJugadores;
		JLayeredPane lp;
		Motor game;
		int puntaje;
		JLabel puntajeActual;
		JTextArea tabla;
		ArrayList<Jugador> jugadores = new ArrayList<>();
		Jugador jugador = new Jugador();


		Pila tempPila;
		Point mouseOffset;
		
		/**
		 * Constructor de la clase Tablero
		 */
		public Tablero(Motor game) {
			this.game = game;
			


			// Window settings
			setTitle("El Joker");
			//Maximo tamaño de ventana
			setExtendedState(MAXIMIZED_BOTH);
			//Tamaño cuando se minimiza
			setSize(900,800);
			//Trae la imagen del tablero en este caso el joker con cuatro cartas quemandose
			try {
				setContentPane((new JPanelWithBackground("../images/background.jpg")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			setLayout(new BorderLayout());
			
			areaDeJuego = new JPanel();
			areaDeJuego.setOpaque(false);
			areaDeJuego.setLayout(new BoxLayout(areaDeJuego, BoxLayout.PAGE_AXIS));
			
			// Centra la aplicaion
			setLocationRelativeTo(null);
			
			// Cerrado de la aplicacion
		    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			
			// Muestra multiples columnas en la misma fila
			FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
			flow.setAlignOnBaseline(true);
			
			// Add the columns panel
			columns = new JPanel();
			columns.setOpaque(false);
			columns.setLayout(flow);
			columns.setMinimumSize(new Dimension(200, 900));
			
			// Añade las columnas al tope
			FlowLayout topFlow = new FlowLayout(FlowLayout.LEFT);
			topFlow.setAlignOnBaseline(true);
			
			topColumns = new JPanel();
			topColumns.setOpaque(false);
			topColumns.setLayout(topFlow);

			//Añade el marcador
			marcador = new JPanel();
			marcador.setOpaque(false);
			marcador.setLayout(flow);
			marcador.setMinimumSize(new Dimension(100,100));
			//Añade un label con el marcador Actual al Jpanel marcador
			puntajeActual = new JLabel("PUNTAJE ACTUAL: "+ puntaje);
			puntajeActual.setForeground(Color.YELLOW);
			puntajeActual.setBackground(new Color(0,0,0,0));
			Font auxFont=puntajeActual.getFont();
			puntajeActual.setFont(new Font(auxFont.getFontName(), auxFont.getStyle(), 30));
			this.marcador.add(puntajeActual);
			//Añade una tabla de los mejores puntajes
			tablaJugadores = new JPanel();
			tablaJugadores.setOpaque(false);
			tablaJugadores.setLayout(flow);
			tablaJugadores.setMinimumSize(new Dimension(200,100));
			//Añade un TextArea con el marcador Actual al Jpanel TablaJugadores
			tabla = new JTextArea();
			tabla.setForeground(Color.YELLOW);
			tabla.setBackground(new Color(0,0,0,0));
			Font auxFont2=tabla.getFont();
			tabla.setFont(new Font(auxFont2.getFontName(), auxFont2.getStyle(), 30));
			tabla.setEditable(false);
			setTabla();
			this.tablaJugadores.add(tabla);


			areaDeJuego.add(topColumns);
			areaDeJuego.add(columns);
			areaDeJuego.add(this.marcador);
			areaDeJuego.add(tablaJugadores);
			
			//añade el area de juego
			add(areaDeJuego);
			
			// Display the window
			lp = getLayeredPane();
			setVisible(true);
			
			// Elemento auxiliar
			mouseOffset = new Point(0, 0);
			
			initialize();
		}
		
		/**
		 * Añade cartas del juego al tablero.
		 */
		private void initialize() {

			topColumns.removeAll();
			columns.removeAll();

			// Los escuchadores de las cartas
			for(Carta c: game.mazo.cartas) {
				c.addMouseListener(this);
				c.addMouseMotionListener(this);		
			}
			
			game.montajeJuego();
			for(Pila p : game.pilas) {
				columns.add(p);
			}
			
			topColumns.add(game.DibujarPila);
			topColumns.add(game.getPila);
			
			for(Pila p : game.finalPilas) {
				topColumns.add(p);
			}
			
			validate();


			recuperarJugadores();
			setTabla();
			jugador.setNombre(JOptionPane.showInputDialog("Ingresa tu Nombre"));
			jugadores.add(jugador);
			grabarjugadores();
		}

		/**
		 * Reinicia el juego
		 */
		public void reset() {
			game.reiniciarElJuego();
			initialize();
			repaint();
		}

		/**
		 *Llama al metodo de la clase Jugador llamado guardar
		 * */
		public void grabarjugadores(){
			Jugador jugador = new Jugador();
			jugador.guardar(jugadores);
		}
		/**
	 	*Llama al metodo de la clase Jugador llamado recuperar
	 	* */
		public void recuperarJugadores(){
		Jugador jugador = new Jugador();
		jugadores =jugador.recuperar();
		}
		/**
		 * Genera la tabla de los primeros 5 mejores puntajes
		 * */
		public void setTabla(){
			tabla.setText("");
			recuperarJugadores();
			jugadores = ordenar(jugadores);
			for (int i = 0; i <jugadores.size(); i++) {
				if(i==5){break;}
				tabla.setText(tabla.getText()+"\n"+(i+1)+"-"+ jugadores.get(i).nombre+"    "+jugadores.get(i).puntaje);
			}
		}
	/**
	 * Organiza el ArrayList de tipo jugador de mayor a menor segun su puntaje
	 * */
	public static ArrayList<Jugador> ordenar(ArrayList<Jugador> jugadores){
		ArrayList<Jugador> j = jugadores;
		for (int i = 1; i < j.size(); i++) {
			Jugador temp;
			if (j.get(i - 1).puntaje < j.get(i).puntaje) {

				temp = j.get(i - 1);
				j.set(i - 1, j.get(i));
				j.set(i, temp);
				i = 0;
			}
		}
		return j;
	}
	/**
     * Auxiliary class which stores information about a single menu option
     * @member {String} name The name of the
     * @member {Integer} shortcut The mnemonic for this button
     */

		
		/**
		 * Function to handle most of the events performed on the Tablero
		 */



		@Override
		public void mouseDragged(MouseEvent e) {
			if(tempPila != null) {
				
				Point pos = getLocationOnScreen();
				pos.x = e.getLocationOnScreen().x - pos.x - mouseOffset.x;
				pos.y = e.getLocationOnScreen().y - pos.y - mouseOffset.y;
				
				tempPila.setLocation(pos);
			}
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {

		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getComponent() instanceof Carta) {
				Carta c = (Carta)e.getComponent();
				Pila p = (Pila)c.getParent();
				
				switch(p.tipo) {
					case elegir:
						game.drawCard();
					break;
					case Normal:
						game.clickPile(p);
					break;
					case Get:
						game.turnGetPile();
					break;
				}	
				repaint();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(e.getComponent() instanceof Carta) {
				Carta c = (Carta)e.getComponent();

				if(c.estaVolteada)
					return;
				
				Pila p  = (Pila)c.getParent();
				
				if(p.cartas.isEmpty() || p.tipo == Pila.TipoPila.Final) return;
				
				tempPila = p.dividir(c);


				lp.add(tempPila, JLayeredPane.DRAG_LAYER);

				Point pos = getLocationOnScreen();
				mouseOffset = e.getPoint();
				pos.x = e.getLocationOnScreen().x - pos.x - mouseOffset.x;
				pos.y = e.getLocationOnScreen().y - pos.y - mouseOffset.y;
				
				tempPila.setLocation(pos);
				
				repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(tempPila != null) {
				
				Point mousePos = e.getLocationOnScreen();
				boolean match = false;

				ArrayList<Pila> droppable = new ArrayList<Pila>(game.pilas);
				droppable.addAll(game.finalPilas);
				
				for(Pila p: droppable) {
					Point pilePos = p.getLocationOnScreen();
					Rectangle r = p.getBounds();
					r.x = pilePos.x;
					r.y = pilePos.y;
					
					if(r.contains(mousePos) && p.aceptaPila(tempPila)) {
						p.unir(tempPila);
						puntaje+=10;
						puntajeActual.setText("PUNTAJE ACTUAL: "+ puntaje);
						jugador.setPuntaje(puntaje);
						for (int i = 0; i <jugadores.size() ; i++) {
							if(jugador.getNombre().equalsIgnoreCase(jugadores.get(i).getNombre())){
								jugadores.get(i).setPuntaje(jugador.puntaje);
								grabarjugadores();
								setTabla();
							}
						}
						match = true;
						break;
					}
				}

				if(!match) tempPila.padre.unir(tempPila);
					
				lp.remove(tempPila);
				tempPila = null;

				repaint();
				
				if(game.checkWin()) {
					JOptionPane.showMessageDialog(this, "Ganaste! Felicitaciones!");
					reset();
				}
			}
		}
		
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		
		public class JPanelWithBackground extends JPanel {
			  private Image backgroundImage;

			  // Metodo para cargar la imagen del joker
			  public JPanelWithBackground(String fileName) throws IOException {
				URL urlToImage = this.getClass().getResource(fileName);
			  	backgroundImage = ImageIO.read(urlToImage);
			  }
			  //Metodo sobre escrito
			  public void paintComponent(Graphics g) {
			    super.paintComponent(g);

			    // elegir la imagen de fondo
			    g.drawImage(backgroundImage, 0, 0, this);
			  }
		}
}
