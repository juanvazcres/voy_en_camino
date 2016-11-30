package mx.com.neus.juanitovision.vo;

/**
 * Created by juan on 12/16/15.
 */
public class Punto {

    private int id;
    private double latitud;
    private double longitud;
    private String nombre;
    private String numero;

    @Override
    public String toString() {
        String punto = this.nombre;
        return punto;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNumero(String numero){
        this.numero=numero;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumero(){
        return numero;
    }

    public String getKey() {
        return id+"";
    }
}
