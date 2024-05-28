/**
* Clase de la cuenta bancaria de la tienda
* @author Ivan García
* @version 1.0, 2024/05/28
*/

import java.sql.ResultSet;
import java.sql.Statement;

public class CuentaTienda{
    private double dinero;

    public CuentaTienda(Statement st){
        this.dinero = cargarCuentaBanco(st);
    }

    public double getDinero(){
        return this.dinero;
    }

    public static double cargarCuentaBanco(Statement st){
        double dinero = 0;
        try {
            ResultSet rs = st.executeQuery("SELECT dinero FROM cuenta_banco");
            dinero = rs.getDouble(1);
        } catch (Exception e) {
            // Lanzar tu excepción personalizada
            System.out.println("Ocurrió un error de SQL"+ e);
        }
        return dinero;
    }

    public String venta(double venta){
        this.dinero += venta;
        return "Venta Ingresada";
    }
}
