package es.upsa.app.tictactoe;

/**
 * Created by mayteEcheverry on 14/5/16.
 */

//Me sirve como solucion a la hora de pasar objetos
class Comunicador{
    private static Comunicador comunicador;
    private Object objeto = null;

    private Comunicador(){}

    public static Comunicador obtenerComunicador(){
        if(comunicador == null){
            comunicador = new Comunicador();
        }
        return comunicador;
    }

    public void setObjeto(Object newObjeto) {
        objeto = newObjeto;
    }

    public Object getObjeto() {
        return objeto;
    }

}
