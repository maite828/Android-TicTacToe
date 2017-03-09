package es.upsa.app.tictactoe;

/**
 * Created by mayteEcheverry on 4/5/16.
 */

//Modelo Jugador
public class Jugador {

    private String nombre;
    private int pGanadas;
    private char marcaJugador;
    private int partidas;

    public Jugador() {
    }

    public Jugador(String nombre, int pGanadas, char mjugador) {
        this.nombre = nombre;
        this.pGanadas = pGanadas;
        this.marcaJugador = mjugador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getpGanadas() {
        return pGanadas;
    }

    public void setpGanadas(int pGanadas) {
        this.pGanadas = pGanadas;
    }

    public char getMarcaJugador() {
        return marcaJugador;
    }

    public void setMarcaJugador(char jugador) {
        this.marcaJugador = jugador;
    }

}
