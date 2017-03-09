package es.upsa.app.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by mayteEcheverry on 15/5/16.
 */

//Esta actividad es la encargada de cargar la lista de los jugadores y de pasar la posición seleccionada a la siguiente actividad
public class ListaActivity extends Activity implements AdapterView.OnItemClickListener {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_jugadores);

        lv = (ListView)findViewById(R.id.lv);
        initList();

        lv.setOnItemClickListener( this );
    }

    public void initList() {
        LogicaTablero tablero = (LogicaTablero)Comunicador.obtenerComunicador().getObjeto();
        JugadorAdapter adapter = new JugadorAdapter(this, tablero.getJugadores());
        lv.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, JugadorActivity.class);
        intent.putExtra(JugadorActivity.EXTRA_JUGADOR_INDEX, position);
        startActivity( intent );
    }
}
