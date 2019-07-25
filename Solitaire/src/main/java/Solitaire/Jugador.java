package Solitaire;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Jugador {
    String nombre;
    int puntaje;

    public Jugador(String nombre, int puntaje) {
        this.nombre = nombre;
        this.puntaje = puntaje;
    }
    public Jugador() {
        this.nombre = null;
        this.puntaje = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    @Override
    public String toString() {
        return "nombre='" + nombre +" puntaje=" + puntaje ;
    }
    public void guardar(ArrayList<Jugador> jugador ){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("Jugadores.txt"))){
            for (int i = 0; i <jugador.size() ; i++) {
                bw.write(jugador.get(i).getNombre()+"-");
                bw.write(jugador.get(i).getPuntaje()+"");
                bw.newLine();
            }

        }catch (IOException e){
            JOptionPane.showMessageDialog(null,"No se encuentra el archivo");
        }
    }
    public ArrayList<Jugador> recuperar(){
        ArrayList<Jugador> jugadores = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader("Jugadores.txt"))) {
            String s;
            while ((s = br.readLine()) != null) {
                String[] auxjugador = s.split("-");
                Jugador jugador = new Jugador();
                    jugador.setNombre(auxjugador[0]);
                    jugador.setPuntaje(Integer.parseInt(auxjugador[1]));
                    jugadores.add(jugador);
            }
        }catch (IOException e){
            JOptionPane.showMessageDialog(null,"Jugadores.txt");
        }
        return jugadores;
    }
}
