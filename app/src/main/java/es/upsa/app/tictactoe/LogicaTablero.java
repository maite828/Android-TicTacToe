package es.upsa.app.tictactoe;

import java.util.Random;

/**
 * Created by mayteEcheverry on 10/5/16.
 */

//Toda la chicha del asunto
public class LogicaTablero {

    private char tablero[];
    private final static int tamanoTablero = 9;
    private Jugador jugador1;
    private Jugador jugador2;
    public final char espVacio = ' ';
    private Random aleatorio;
    private int empCounter;
    private Jugador[] jugadores;
    public static final int GANAEMPATE = 1;
    public static final int GANAJUGADOR1 = 2;
    public static final int SINOHAYGANADOR = 0;
    public int partidas;

    public LogicaTablero(){}

    //Le paso al constructor los nombres de los jugadores
    public LogicaTablero(String j1, String j2){
        jugador1 = new Jugador(j1,0,'X');
        jugador2 = new Jugador(j2,0,'O');

        jugadores = new Jugador[2];

        tablero = new char[tamanoTablero];
        for (int i = 0; i < tamanoTablero; i++)
            tablero[i] = espVacio;
        aleatorio = new Random();
        this.empCounter = 0;
        this.partidas = 0;
    }

    public Jugador[] getJugadores() {
        return jugadores;
    }

    public void setJugadores(Jugador uno, Jugador dos){
        jugadores[0] = uno;
        jugadores[1] = dos;
    }

    public int getEmpCounter() {
        return empCounter;
    }

    public void setEmpCounter(int empCounter) {
        this.empCounter = empCounter;
    }

    //Devuelve el tamaño del tablero
    public int getTamanoTablero() {
        return tamanoTablero;
    }

    public Jugador getJugadorUno() {
        return jugador1;
    }

    public Jugador getJugadorDos() {
        return jugador2;
    }

    public int getPartidas() {
        return partidas;
    }

    public void setPartidas(int partidas) {
        this.partidas = partidas;
    }

    //Limpiar el tablero de todas las X y O
    public void limpiarTablero() {
        for (int i = 0; i < tamanoTablero; i++) {
            tablero[i] = espVacio;
        }
    }

    //configurar el punto determinado en el tablero, la ubicación debe estar disponible, o no será cambiado en el tablero.
    public void setMover(char JUGADOR, int location) {
        tablero[location] = JUGADOR;
    }

    /*Me ayuda aconseguir el mejor movimiento para android, llamo a setMove()
    para realizar movimiento en este punto del tablero.*/
    public int getMovimientoMaquina() {
        int mover;
        //ver si hay un movimiento para O, para ganar
        for (int i = 0; i < getTamanoTablero(); i++) {
            if (tablero[i] != jugador1.getMarcaJugador() && tablero[i] != jugador2.getMarcaJugador()) {
                char marca = tablero[i];
                tablero[i] = jugador2.getMarcaJugador();
                if (controlGanador() == 3) {
                    setMover(jugador2.getMarcaJugador(), i);
                    return i;
                } else
                    tablero[i] = marca;
            }
        }

        //Ver si hay un movimiento para O para bloquear a X o ganar
        for (int i = 0; i < getTamanoTablero(); i++) {
            if (tablero[i] != jugador1.getMarcaJugador() && tablero[i] != jugador2.getMarcaJugador()) {
                char marca = tablero[i];
                tablero[i] = jugador1.getMarcaJugador();
                if (controlGanador() == 2) {
                    setMover(jugador2.getMarcaJugador(), i);
                    return i;
                } else
                    tablero[i] = marca;
            }
        }

        //Generar movimiento aleatorio
        do {
            mover = aleatorio.nextInt(getTamanoTablero());
        } while (tablero[mover] == jugador1.getMarcaJugador() || tablero[mover] == jugador2.getMarcaJugador());

        setMover(jugador2.getMarcaJugador(), mover);
        return mover;
    }

    /*Compruebe si hay un ganador y devolver un valor de estado que indica que ha ganado
      Devuelvo 0 si no hay ganador o empate, 1 si es un empate, 2 si X gana, 3 si O gana */
    public int controlGanador() {
        //compruebo filas
        for (int i = 0; i <= 6; i += 3) {
            if (    tablero[i] == jugador1.getMarcaJugador() &&
                    tablero[i+1] == jugador1.getMarcaJugador() &&
                    tablero[i+2] == jugador1.getMarcaJugador())
                return 2;
            if (    tablero[i] == jugador2.getMarcaJugador() &&
                    tablero[i+1] == jugador2.getMarcaJugador() &&
                    tablero[i+2] == jugador2.getMarcaJugador())
                return 3;
        }
        //compruebo columnas
        for (int i = 0; i <= 2; i++) {
            if (    tablero[i] == jugador1.getMarcaJugador() &&
                    tablero[i+3] == jugador1.getMarcaJugador() &&
                    tablero[i+6] == jugador1.getMarcaJugador())
                return 2;
            if (    tablero[i] == jugador2.getMarcaJugador() &&
                    tablero[i+3] == jugador2.getMarcaJugador() &&
                    tablero[i+6] == jugador2.getMarcaJugador())
                return 3;
        }
        //compruebo diagonales
        if (   (tablero[0] == jugador1.getMarcaJugador() &&
                tablero[4] == jugador1.getMarcaJugador() &&
                tablero[8] == jugador1.getMarcaJugador()) ||
                tablero[2] == jugador1.getMarcaJugador() &&
                tablero[4] == jugador1.getMarcaJugador() &&
                tablero[6] == jugador1.getMarcaJugador())
            return 2;
        if (   (tablero[0] == jugador2.getMarcaJugador() &&
                tablero[4] == jugador2.getMarcaJugador() &&
                tablero[8] == jugador2.getMarcaJugador()) ||
                tablero[2] == jugador2.getMarcaJugador() &&
                tablero[4] == jugador2.getMarcaJugador() &&
                tablero[6] == jugador2.getMarcaJugador())
            return 3;

        // Compruebe si hay un empate
        for (int i = 0; i < getTamanoTablero(); i++) {
            //Si nos encontramos con un número, entonces nadie ha ganado aún
            if (tablero[i] != jugador1.getMarcaJugador() && tablero[i] != jugador2.getMarcaJugador())
                return 0;
        }
        //a través del bucle anterior, vemos todos los lugares, y en este caso será empate
        return 1;
    }
}
