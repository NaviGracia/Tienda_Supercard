/**
* Interfaz de modificaciones en los precios
* @author Ivan García
* @version 1.0, 2024/04/22
*/
public interface ModificacionesPrecio{
    public double aplicarDescuento(double precio, double descuento);
    public double aumentarPrecio(double precio, double inflacion);
}