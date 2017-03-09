package es.upsa.app.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mayteEcheverry on 10/5/16.
 */

public class MainActivity extends Activity {

    //Logica del juego
    private LogicaTablero tablero;
    //Botones del tablero
    private Button botonesTablero[];

    // Vistas de texto
    private TextView tvInfo;
    private TextView tvJugadorUnoCount;
    private TextView tvJugadorUnoText;
    private TextView tvEmpCount;
    private TextView tvJugadorDosCount;
    private TextView tvJugadorDosText;
    private TextView txtIniJugador1;
    private TextView txtIniJugador2;

    private boolean primeroJugadorUno = true;
    private boolean haySoloUnJugador = false;
    private boolean isTurnoDelUno = true;
    private boolean terminarPartida = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean tipoJuego = getIntent().getExtras().getBoolean("tipo_partida");
        tablero = (LogicaTablero)Comunicador.obtenerComunicador().getObjeto();

        botonesTablero = new Button[tablero.getTamanoTablero()];
        botonesTablero[0] = (Button) findViewById(R.id.celda1);
        botonesTablero[1] = (Button) findViewById(R.id.celda2);
        botonesTablero[2] = (Button) findViewById(R.id.celda3);
        botonesTablero[3] = (Button) findViewById(R.id.celda4);
        botonesTablero[4] = (Button) findViewById(R.id.celda5);
        botonesTablero[5] = (Button) findViewById(R.id.celda6);
        botonesTablero[6] = (Button) findViewById(R.id.celda7);
        botonesTablero[7] = (Button) findViewById(R.id.celda8);
        botonesTablero[8] = (Button) findViewById(R.id.celda9);

        tvInfo = (TextView) findViewById(R.id.info);
        tvJugadorUnoCount = (TextView) findViewById(R.id.humanCount);
        tvJugadorDosCount = (TextView) findViewById(R.id.androidCount);
        tvJugadorUnoText = (TextView) findViewById(R.id.human);
        tvJugadorDosText = (TextView) findViewById(R.id.android);
        tvEmpCount = (TextView) findViewById(R.id.tiesCount);

        txtIniJugador1 = (TextView)findViewById(R.id.nom_judador1);
        txtIniJugador1.setText(tablero.getJugadorUno().getNombre());
        txtIniJugador2 = (TextView)findViewById(R.id.nom_jugador2);
        txtIniJugador2.setText(tablero.getJugadorDos().getNombre());

        Button result = (Button)findViewById(R.id.result);
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent (MainActivity.this, ListaActivity.class);
                startActivityForResult(i,0);
            }
        });

        //añado los jugadores al array
        tablero.setJugadores(tablero.getJugadorUno(),tablero.getJugadorDos());

        Button reset = (Button)findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empezarPartida(haySoloUnJugador);
            }
        });

        tvEmpCount.setText(Integer.toString(tablero.getEmpCounter()));
        tvJugadorUnoCount.setText(Integer.toString(tablero.getJugadorUno().getpGanadas()));
        tvJugadorDosCount.setText(Integer.toString(tablero.getJugadorUno().getpGanadas()));

        empezarPartida(tipoJuego);
    }

    //Opciones de menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.juego_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nuevaPartida:
                empezarPartida(haySoloUnJugador);
                break;
            case R.id.salir:
                MainActivity.this.finish();
                break;
        }
        return true;
    }

    // Iniciar partida
    // Borra el tablero y restablece todos los botones / texto
    private void empezarPartida(boolean soloUno) {
        this.haySoloUnJugador = soloUno;

        tablero.limpiarTablero();

        for (int i = 0; i < botonesTablero.length; i++) {
            botonesTablero[i].setText("");
            botonesTablero[i].setEnabled(true);
            botonesTablero[i].setOnClickListener(new ButtonClickListener(i));
            botonesTablero[i].setBackgroundResource(R.mipmap.ic_tablero);
        }

        if (haySoloUnJugador) {
            tvJugadorUnoText.setText(tablero.getJugadorUno().getNombre()+" :");
            tvJugadorDosText.setText(R.string.android);

            if (primeroJugadorUno) {
                tvInfo.setText(tablero.getJugadorUno().getNombre());
                primeroJugadorUno = false;
            } else {
                tvInfo.setText(tablero.getJugadorDos().getNombre());
                int mover = tablero.getMovimientoMaquina();
                setMoverT(tablero.getJugadorDos().getMarcaJugador(), mover);
                primeroJugadorUno = true;
            }
        } else {
            tvJugadorUnoText.setText(tablero.getJugadorUno().getNombre()+" :");
            tvJugadorDosText.setText(tablero.getJugadorDos().getNombre()+" :");

            if (primeroJugadorUno) {
                tvInfo.setText(tablero.getJugadorUno().getNombre());
                primeroJugadorUno = false;
            } else {
                tvInfo.setText(tablero.getJugadorDos().getNombre());
                primeroJugadorUno = true;
            }
        }
        terminarPartida = false;
    }

    private class ButtonClickListener implements View.OnClickListener {
        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        public void onClick(View view) {
            if (!terminarPartida) {
                if (botonesTablero[location].isEnabled()) {
                    if (haySoloUnJugador) {
                        setMoverT(tablero.getJugadorUno().getMarcaJugador(), location);

                        int winner = tablero.controlGanador();

                        if (winner == LogicaTablero.SINOHAYGANADOR) {
                            tvInfo.setText(tablero.getJugadorDos().getNombre());
                            int move = tablero.getMovimientoMaquina();
                            setMoverT(tablero.getJugadorDos().getMarcaJugador(), move);
                            winner = tablero.controlGanador();
                        }

                        if (winner == LogicaTablero.SINOHAYGANADOR)
                            tvInfo.setText(tablero.getJugadorUno().getNombre());
                        else if (winner == LogicaTablero.GANAEMPATE) {
                            tvInfo.setText(R.string.result_tie);
                            tablero.setEmpCounter(tablero.getEmpCounter()+1);
                            tvEmpCount.setText(Integer.toString(tablero.getEmpCounter()));

                            Toast toast=Toast.makeText(MainActivity.this,R.string.tie, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER| Gravity.CENTER, 0, 0);
                            toast.show();

                            tablero.setPartidas(tablero.getPartidas()+1);
                            terminarPartida = true;
                        } else if (winner == LogicaTablero.GANAJUGADOR1) {
                            tvInfo.setText(tablero.getJugadorUno().getNombre());
                            tablero.getJugadorUno().setpGanadas(tablero.getJugadorUno().getpGanadas()+1);
                            tvJugadorUnoCount.setText(Integer.toString(tablero.getJugadorUno().getpGanadas()));

                            Toast toast=Toast.makeText(MainActivity.this,"GANA :"+tablero.getJugadorUno().getNombre(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER| Gravity.CENTER, 0, 0);
                            toast.show();

                            tablero.setPartidas(tablero.getPartidas()+1);
                            terminarPartida = true;
                        } else {
                            tvInfo.setText(tablero.getJugadorDos().getNombre());
                            tablero.getJugadorDos().setpGanadas(tablero.getJugadorDos().getpGanadas()+1);
                            tvJugadorDosCount.setText(Integer.toString(tablero.getJugadorDos().getpGanadas()));

                            Toast toast=Toast.makeText(MainActivity.this,"GANA :"+tablero.getJugadorDos().getNombre(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER| Gravity.CENTER, 0, 0);
                            toast.show();

                            tablero.setPartidas(tablero.getPartidas()+1);
                            terminarPartida = true;
                        }
                    } else {
                        if (isTurnoDelUno)
                            setMoverT(tablero.getJugadorUno().getMarcaJugador(), location);
                        else
                            setMoverT(tablero.getJugadorDos().getMarcaJugador(), location);

                        int winner = tablero.controlGanador();

                        if (winner == LogicaTablero.SINOHAYGANADOR) {
                            if (isTurnoDelUno) {
                                tvInfo.setText(tablero.getJugadorDos().getNombre());
                                isTurnoDelUno = false;
                            } else {
                                tvInfo.setText(tablero.getJugadorUno().getNombre());
                                isTurnoDelUno = true;
                            }
                        } else if (winner == LogicaTablero.GANAEMPATE) { //SABEMOS EMPATE
                            tvInfo.setText(R.string.result_tie);
                            tvInfo.setText(tvInfo.getText().toString().toUpperCase());
                            tablero.setEmpCounter(tablero.getEmpCounter()+1);
                            tvEmpCount.setText(Integer.toString(tablero.getEmpCounter()));

                            Toast toast=Toast.makeText(MainActivity.this,R.string.tie, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER| Gravity.CENTER, 0, 0);
                            toast.show();

                            tablero.setPartidas(tablero.getPartidas()+1);
                            terminarPartida = true;
                        } else if (winner == LogicaTablero.GANAJUGADOR1) {//SABEMOS GANADOR 1
                            tvInfo.setText(tablero.getJugadorUno().getNombre().toUpperCase());
                            tablero.getJugadorUno().setpGanadas(tablero.getJugadorUno().getpGanadas()+1);
                            tvJugadorUnoCount.setText(Integer.toString(tablero.getJugadorUno().getpGanadas()));

                            Toast toast=Toast.makeText(MainActivity.this,"GANA :"+tablero.getJugadorUno().getNombre(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER| Gravity.CENTER, 0, 0);
                            toast.show();

                            tablero.setPartidas(tablero.getPartidas()+1);
                            terminarPartida = true;
                            isTurnoDelUno = false;
                        } else {//SABEMOS GANADOR 2
                            tvInfo.setText(tablero.getJugadorDos().getNombre().toUpperCase());
                            tablero.getJugadorDos().setpGanadas(tablero.getJugadorDos().getpGanadas()+1);
                            tvJugadorDosCount.setText(Integer.toString(tablero.getJugadorDos().getpGanadas()));

                            Toast toast=Toast.makeText(MainActivity.this,"GANA :"+tablero.getJugadorDos().getNombre(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER| Gravity.CENTER, 0, 0);
                            toast.show();

                            tablero.setPartidas(tablero.getPartidas()+1);
                            terminarPartida = true;
                            isTurnoDelUno = true;
                        }
                    }
                }
            }
        }
    }

    //Realiza el movimiento para el jugador actual
    private void setMoverT(char jugador, int location) {
        tablero.setMover(jugador, location);
        botonesTablero[location].setEnabled(false);
        if (jugador == tablero.getJugadorUno().getMarcaJugador())
            botonesTablero[location].setBackgroundResource(R.mipmap.x);
        else {
            botonesTablero[location].setBackgroundResource(R.mipmap.o);
        }
    }

}
