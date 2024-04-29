/**
* Codigo del main de la tienda
* Main de la tienda Supercard
* @author Ivan García
* @version 1.0, 2024/04/22
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class Tienda extends Entrada_Salida{
    //Color de letra a salida por pantalla
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";

    //Insertar Datos a la BD
    public static void registrarNuevaCarta(Connection conexion, Statement st){
        String sql = "INSERT INTO carta VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, devolverNumCarta());
            sentencia.setString(2, devolverNombre());
            sentencia.setString(3, devolverCategoria(st, conexion));
            sentencia.setDouble(4, devolverPrecio());
            sentencia.executeUpdate();
            System.out.println("Carta registrada correctamente");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error en el registro de una nueva carta: " + e);
        }
        

    }

    public static void buscarProducto(Statement st, Connection conexion) throws Exception{
//Haciendo la consulta
    }


    public static void main(String[] args) throws Exception {
    //Conectar BD
    try {
        Class.forName("org.postgresql.Driver");
    } catch (Exception e) {
        // TODO: handle exception
        System.out.println("Error en registro driver");
    }

    Connection conexion = null;
    conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Supercard", "dam", "dam");
    Statement st = conexion.createStatement();
    conexion.setAutoCommit(false);

    int eleccion;
    do {    
        System.out.println(ANSI_RED + "Bienvenido al Sistema Gestor de la Tienda Supercard. \n Que desea realizar: " + "\n 1. Registrar Nuevo Producto" + "\n 2. Buscar Producto" + "\n 3. Eliminar Producto" + "\n 4. Actualizar Producto" + "\n 6. Salir" + ANSI_RESET);
        eleccion = devolverOpcion();
        switch (eleccion) {
            case 1:
                registrarNuevaCarta(conexion, st); //De momento acaabado
                break;
            case 2:
                
                break;
            case 3:
                
                break;
            case 4:
                
                break;
            case 5:
            
                break;
            case 6: 
                System.out.println("Saliendo del Programa");
                break;
            default:
                break;
        }
    } while (eleccion!=5);
    System.out.println("Saliendo del Sistema Gestor de la Tienda Supercard.");
    conexion.commit();
    
    }
}


    
    