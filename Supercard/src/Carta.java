/**
* Clase del Objeto Carta
* @author Ivan García
* @version 1.0, 2024/04/22
*/
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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

    public void insertarCartaBD(PreparedStatement sentencia) throws Exception{
        String sql = "INSERT INTO carta VALUES(?, ?, ?, ?, 100)";
        sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, nCarta);
        sentencia.setString(2, recibirNombreCarta());
        sentencia.setString(3, recibirCategoria(st));
        sentencia.setDouble(4, recibirPrecio());
        sentencia.executeUpdate();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return ANSI_CYAN + estandarizarEspaciados(n_carta, nombre, categoria) + "\t" + precio + "€\t" + ANSI_RESET;
    }
    
    public static String estandarizarEspaciados(int n_carta, String nombre, String categoria){
        String frase = "";
        try{
            for(int posicion = 0; posicion <=40; posicion++){
                if(posicion == 0){
                    frase+= n_carta;
                    posicion+= Integer.toString(n_carta).length();
                }else if(posicion == 6){
                    frase+= nombre;
                    posicion+= nombre.length()-1;
                }else if(posicion == 26){
                    frase+= categoria;
                    posicion+= categoria.length()-1;
                }else{
                    frase+= " ";
                }
            }
        }catch(Exception e){
            System.out.println("Fallo en la obtención de datos desde la BD: " + e);
        }
        return frase;
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