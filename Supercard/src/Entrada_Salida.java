import java.util.Scanner;

/**
 * Entrada_Salida
 */
public class Entrada_Salida {
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

    public static String devolverCategoria(){
        System.out.println("Inserte la categoría:");
        return sc.nextLine();
    }
    
    public static double devolverPrecio(){
        System.out.println("Inserte el precio");
        double n = sc.nextDouble();limpiar();
        return n;
    }
}