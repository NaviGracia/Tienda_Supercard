/**
* Clase de entrada|salida de valores
* @author Ivan García
* @version 1.0, 2024/04/22
*/
import java.util.Scanner;

/**
 * Entrada_Salida
 */
public class Entrada_Salida {

    static Scanner sc = new Scanner(System.in);
    
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
}