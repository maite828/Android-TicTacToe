package es.upsa.app.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mayteEcheverry on 15/5/16.
 */

//Esta es la ultima actividad y me sirve para ver el detalle de cada jugador
public class JugadorActivity extends Activity {

    public static final String EXTRA_JUGADOR_INDEX = "EXTRA_JUGADOR_INDEX";
    private int contactoIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_jugador);

        //obtengo de la actividad acterior el indice del listview con la posición del jugador seleccionado
        Intent intent = getIntent();
        contactoIndex = intent.getIntExtra(EXTRA_JUGADOR_INDEX, -1);
        if ( contactoIndex != -1 ) {

            //obtengo los jugadores
            LogicaTablero tablero = (LogicaTablero)Comunicador.obtenerComunicador().getObjeto();
            Jugador[] jugadores = tablero.getJugadores();
            Jugador jugador = jugadores[contactoIndex];

            TextView tvNombre   = (TextView) findViewById(R.id.tv_nombre);
            TextView tvMarca    = (TextView) findViewById(R.id.tv_ficha);
            TextView tvGanadas = (TextView) findViewById(R.id.tv_ganadas);
            TextView tvPartidas = (TextView) findViewById(R.id.tv_total);
            ImageView image   = (ImageView) findViewById(R.id.image_list_jugador);

            if (jugador.getMarcaJugador() == 'X')
                image.setImageResource(R.drawable.play2_x_grande);
            else
                image.setImageResource(R.drawable.play1_o_grande);

            tvNombre.setText(jugador.getNombre());
            tvMarca.setText(String.valueOf(jugador.getMarcaJugador()));
            tvGanadas.setText(String.valueOf(jugador.getpGanadas()));
            tvPartidas.setText(String.valueOf(tablero.getPartidas()));
        }
    }
}
