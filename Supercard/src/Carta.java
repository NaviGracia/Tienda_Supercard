/**
* Clase del Objeto Carta
* @author Ivan García
* @version 1.0, 2024/04/22
*/
import java.io.Serializable;

abstract class Carta implements ModificacionesPrecio, Serializable{
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";

    private int n_carta;
    private String nombre;
    private String categoria;
    private double precio;
    
    public Carta(int n_carta, String nombre, String categoria, double precio) {
        this.n_carta = n_carta;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }

    public int getN_carta() {
        return n_carta;
    }

    public void setN_carta(int n_carta) {
        this.n_carta = n_carta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return n_carta + "\t" + nombre + "\t" + categoria;
    }

    //Método Abstracto
    abstract int venderCarta();

    //Métodos Interfaz
    public double aplicarDescuento(double precio, double descuento){
        double precioSinDescuento = precio;
        double precioFinal = precioSinDescuento - (precioSinDescuento/descuento);
        return precioFinal;
    }

    public double aumentarPrecio(double precio, double inflacion){
        double precioBase = precio;
        double precioFinal = precioBase + (precioBase/inflacion);
        return precioFinal;
    }
}