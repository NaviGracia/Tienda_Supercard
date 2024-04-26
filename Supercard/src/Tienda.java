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
import java.util.Scanner;


public class Tienda extends Entrada_Salida{
    static Scanner sc = new Scanner(System.in);
    //Color de letra a salida por pantalla
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";

    //Insertar Datos a la BD
    static public void insertarSobreBD(Connection conexion, PreparedStatement sentencia){
        
       try {
            String sentenciaSQL = "INSERT INTO sobre VALUES(?, ?)";
            sentencia = conexion.prepareStatement(sentenciaSQL);

            System.out.println("Inserte el tamaño del sobre:");
            sentencia.setInt(1, devolverNum());
            System.out.println("Inserte el precio del sobre:");
            sentencia.setDouble(2, devolverDinero());
            sentencia.executeUpdate();
            System.out.println("Sobre añadido");
       } catch (Exception e) {
        // TODO: handle exception
        System.out.println("Error al insertar un sobre");
       }
    }

    static public void insertarCartaLuchadorBD(Connection conexion, PreparedStatement sentencia){
        
        try {
             String sentenciaSQL = "INSERT INTO luchador VALUES(?, ?)";
             sentencia = conexion.prepareStatement(sentenciaSQL);
 
             System.out.println("Inserte el tamaño del sobre:");
             sentencia.setInt(1, devolverNum());
             System.out.println("Inserte el precio del sobre:");
             sentencia.setDouble(2, devolverDinero());
             sentencia.executeUpdate();
             System.out.println("Sobre añadido");
        } catch (Exception e) {
         // TODO: handle exception
         System.out.println("Error al insertar un sobre");
        }
     }

    static public void registrarNuevoProducto(Connection conexion){
        System.out.println("Inserta el tipo de producto que se va a añadir:");
        String tabla = sc.nextLine();
        PreparedStatement sentencia = null;

        try{
            if (tabla.equalsIgnoreCase("Sobre")) {
                String seguir;
                do {
                    insertarSobreBD(conexion, sentencia);
                    System.out.println("Desea añadir otro sobre? (Si | No)");
                    seguir = sc.nextLine();
                } while (seguir.equalsIgnoreCase("Si"));
            }
        }catch (Exception e) {
            System.out.println("Error al registrar un nuevo producto");
        } 
    }

    public static void buscarProducto(Statement st, Connection conexion) throws Exception{
        String sql = "SELECT * FROM ?";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        //Se puede mejorar el sistema de búsqueda
        System.out.println("Introduzca el ID del producto que desea buscar");
        sentencia.setString(1, sc.nextLine());
        ResultSet rs = sentencia.executeQuery();

        System.out.println("Mostrando Resultados:");
        while (rs.next()) {
         System.out.println(ANSI_RED + rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getDouble(4) + ANSI_RESET);
     }
     rs.close();
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
        System.out.println("Bienvenido al Sistema Gestor de la Tienda Supercard. \n Que desea realizar: " + "\n 1. Registrar Nuevo Producto" + "\n 2. Buscar Producto" + "\n 3. Eliminar Producto" + "\n 4. Actualizar Producto" + "\n 6. Salir");
        eleccion = sc.nextInt();
        sc.nextLine();
        switch (eleccion) {
            case 1:
                registrarNuevoProducto(conexion);
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


    
    