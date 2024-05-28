/**
* Clase de entrada|salida de valores
* @author Ivan García
* @version 1.0, 2024/04/22
*/
import java.util.Scanner;

/*Importaciones Únicas del Proyecto */
import java.sql.Statement;
import java.sql.ResultSet;


public class Entrada_Salida {
    static Scanner sc = new Scanner(System.in);
    
    /*Colores de Terminal */
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    
    /*Estándar*/
    public static int devolverInt(){
        int n = Integer.parseInt(devolverString());
        return n;
    }

    public static String devolverString(){
        return sc.nextLine();
    }
    
    public static double devolverDouble(){
        double n = Double.parseDouble(devolverString());
        return n;
    }

    public static boolean devolverBoolean(){
        boolean bool = sc.nextBoolean();
        return bool;
    }


    //Controlador para que no salten automaticamente los menus después de completar la acción
    public static String controladorContinuar(){
        System.out.println("Presione Enter para continuar.");
        return devolverString();
    }

    //Controlador de Objetos, para que no aparezcan 100 objetos de golpe, reutilizable cambiando los parametros
    public static void controladorObjetos(Luchador l){
        int controladorCatalogo = 0;
        if (controladorCatalogo == 6) {
            System.out.println(l.toString());
            controladorContinuar();
            controladorCatalogo = 0;
        } else{
            System.out.println(l.toString());
            controladorCatalogo++;
        }
    }

    /*Únicos del Proyecto (Separados para que al pasar esta clase a otros proyectos sea sencillo limpiar la clase)*/
    public static int recibirNumCarta(){
        System.out.println("Inserte el nº de la carta:");
        return devolverInt();
    }

    public static String recibirNombreCarta(){
        System.out.println("Inserte el nombre:");
        return devolverString();
    }

    public static String recibirCategoria(Statement st){
        System.out.println("Inserte la categoría:");
        mostrarCategorias(st);
        return devolverString();
    }
    
    public static double recibirPrecio(){
        System.out.println("Inserte el precio");
        return devolverDouble();
    }

    public static void mostrarCategorias(Statement st){
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

}