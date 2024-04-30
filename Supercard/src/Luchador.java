/**
 * Luchador
 */
public class Luchador extends Carta{
    private int fuerza;
    private int resistencia;
    private int velocidad;
    private int carisma;
    
    public Luchador(int n_carta, String nombre, String categoria, double precio, int stock, int fuerza, int resistencia,
            int velocidad, int carisma) {
        super(n_carta, nombre, categoria, precio, stock);
        this.fuerza = fuerza;
        this.resistencia = resistencia;
        this.velocidad = velocidad;
        this.carisma = carisma;
    }




    

    
}