package es.upsa.app.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by mayteEcheverry on 10/5/16.
 */

public class MenuActivity  extends Activity implements RadioGroup.OnCheckedChangeListener{

    private LinearLayout padre;
    private LogicaTablero tablero;
    private EditText primeroX;
    private EditText segundoO;
    private EditText unJugX;

    //RadioButton
    private RadioGroup rg;
    private RadioButton rbUno;
    private RadioButton rbDos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);

        padre = (LinearLayout) findViewById(R.id.padre);
        primeroX = (EditText) findViewById(R.id.etJug1);
        segundoO = (EditText) findViewById(R.id.etJug2);
        unJugX = (EditText) findViewById(R.id.et1_jugador);

        rg = (RadioGroup) findViewById(R.id.rg);
        rbUno = (RadioButton) findViewById(R.id.rbSelect1);
        rbDos = (RadioButton) findViewById(R.id.rbSelect2);
        rg.setOnCheckedChangeListener(this);

        Button jugar=(Button)findViewById(R.id.bJugar);
        jugar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent (MenuActivity.this, MainActivity.class);

                if (rbUno.isChecked()){
                    if (!String.valueOf(unJugX.getText()).equals("") && String.valueOf(primeroX.getText()).equals("") && String.valueOf(segundoO.getText()).equals("")){
                        i.putExtra("tipo_partida", true);
                        tablero=new LogicaTablero(String.valueOf(unJugX.getText()),"Android");
                        Comunicador.obtenerComunicador().setObjeto(tablero);
                        unJugX.setText("");
                        startActivityForResult(i, 0);
                    }
                }else {

                    if (rbDos.isChecked()) {
                        if (!String.valueOf(primeroX.getText()).equals("") && !String.valueOf(segundoO.getText()).equals("") && String.valueOf(unJugX.getText()).equals("")) {
                            i.putExtra("tipo_partida", false);
                            tablero = new LogicaTablero(String.valueOf(primeroX.getText()), String.valueOf(segundoO.getText()));
                            Comunicador.obtenerComunicador().setObjeto(tablero);
                            primeroX.setText("");
                            segundoO.setText("");
                            startActivityForResult(i, 0);
                        }
                    }else{
                        Toast toast=Toast.makeText(MenuActivity.this,R.string.error, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER| Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            }
        });

        //Salir
        ((Button) findViewById(R.id.salir)).setOnClickListener(new OnClickListener() {
            public void onClick(View V) {
                MenuActivity.this.finish();
            }
        });
    }

    //Avisador al hacer el check en el radio
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch ( group.getId() ){
            case R.id.rg:
                switch ( checkedId ){
                    case R.id.rbSelect1:
                        if ( rbUno.isChecked() ) {
                            Toast toast=Toast.makeText(MenuActivity.this,R.string.check_uno, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER| Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        break;

                    case R.id.rbSelect2:
                        if ( rbDos.isChecked() ) {
                            Toast toast=Toast.makeText(MenuActivity.this,R.string.check_dos, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER| Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        break;
                }
                break;
        }
    }

}
