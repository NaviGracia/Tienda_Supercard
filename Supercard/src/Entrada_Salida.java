import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Entrada_Salida
 */
public class Entrada_Salida {
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";

    private int num;
    private String cadena;
    private double dinero;
    static Scanner sc = new Scanner(System.in);
    

    public static void limpiar(){
        sc.nextLine();
    }

    public static int devolverOpcion(){
        System.out.println("Inserte el nº de la opción:");
        int n = sc.nextInt();limpiar();
        return n;
    }

    public static int devolverNumCarta(){
        System.out.println("Inserte el nº de carta:");
        int n = sc.nextInt();limpiar();
        return n;
    }

    public static String devolverNombre(){
        System.out.println("Inserte el nombre de la carta:");
        return sc.nextLine();
    }

    public static void mostrarCategorias(Statement st, Connection conexion){
        try {
            ResultSet rs = st.executeQuery("SELECT categoria FROM categoria");
            while (rs.next()) {
                System.out.println(ANSI_CYAN + rs.getString(1) + ANSI_RESET);
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error al mostrar las categorias registradas en la BD: " + e);
        }
    }

    public static String devolverCategoria(Statement st, Connection conexion){
        System.out.println("Inserte la categoría:");
        mostrarCategorias(st, conexion);
        return sc.nextLine();
    }
    
    public static double devolverPrecio(){
        System.out.println("Inserte el precio");
        double n = sc.nextDouble();limpiar();
        return n;
    }
}