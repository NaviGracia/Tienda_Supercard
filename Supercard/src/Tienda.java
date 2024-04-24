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


public class Tienda {
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
            sentencia.setInt(1, sc.nextInt());
            System.out.println("Inserte el precio del sobre:");
            sentencia.setDouble(2, sc.nextDouble());sc.nextLine();
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
             sentencia.setInt(1, sc.nextInt());
             System.out.println("Inserte el precio del sobre:");
             sentencia.setDouble(2, sc.nextDouble());sc.nextLine();
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

        public static int generarNº(){
            return (int) (Math.random()*68+1);   
        }

        public static void generarJoker(Connection conexion, Statement st, int i, ArrayList<Integer> al){
            try {
                al.add(0);
                for (int ind = 1; ind <= i; ind++) {
                    int generate = generarNº();
                    for (Integer num : al) {
                        if (generate==num) {
                            i-=1;
                        }else{
                            al.add(generate);
                            String sql = "SELECT name, effect, rarity, cost FROM jokers WHERE id = ?";
                            PreparedStatement ps = conexion.prepareStatement(sql);
                            ps.setInt(1, generate);
                            ResultSet rs = ps.executeQuery();

                            while (rs.next()) {
                                System.out.println("Carta " + ind + ": " + rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4) + "$");
                            }
                        }
                    }
                }


            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("Error en la generación de Jokers");
            }
        }

        public static void generar_joker_sobre(Connection conexion, Statement st){
            System.out.println("Elige el nº del tipo de sobre a abrir: \n 1- Standard (2) \n 2- Jumbo (4) \n 3- Mega (5)");
            int eleccion = sc.nextInt();
            ArrayList<Integer> al = new ArrayList<>();
            switch (eleccion) {
                case 1:
                    System.out.println("Abriendo Sobre Standard:");
                    generarJoker(conexion, st, 2, al);                       
                    break;
                case 2:

                    break;
                case 3:

                    break;
            }
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


    
    