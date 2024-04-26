import java.util.Scanner;

/**
 * Entrada_Salida
 */
public class Entrada_Salida {
    private int num;
    private String cadena;
    private double dinero;
    static Scanner sc = new Scanner(System.in);
    
    public static int devolverNum(){
        return sc.nextInt();
    }

    public static String devolverCadena(){
        return sc.nextLine();
    }
    
    public static double devolverDinero(){
        return sc.nextDouble();
    }
}