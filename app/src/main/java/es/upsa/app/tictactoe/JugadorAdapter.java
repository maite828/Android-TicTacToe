package es.upsa.app.tictactoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mayteEcheverry on 15/5/16.
 */

//Adaptador que recibirá el contexto de la actividad que le llama y el array de jugadores, para adaptarlos al listvie
public class JugadorAdapter extends BaseAdapter {

    private Context context;
    private Jugador[] jugadores;

    public JugadorAdapter(Context context, Jugador[] jugadores) {
        this.context = context;
        this.jugadores = jugadores;
    }

    @Override
    public int getCount() {
        return jugadores.length;
    }

    @Override
    public Object getItem(int position) {
        return jugadores[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.item_list_jugador, parent, false);
        TextView tvNombre   = (TextView) view.findViewById(R.id.tv_nombre);
        ImageView image   = (ImageView) view.findViewById(R.id.image_list_jugador);

        Jugador jugador = jugadores[position];

        if (jugador.getMarcaJugador() == 'X')
            image.setImageResource(R.drawable.play2_x);
        else
            image.setImageResource(R.drawable.play1_o);

        tvNombre.setText(jugador.getNombre());

        return view;
    }
}
