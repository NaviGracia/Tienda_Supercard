/**
 * Luchador
 */
public class Luchador extends Carta{
    private int fuerza;
    private int resistencia;
    private int velocidad;
    private int carisma;
    
    public Luchador(int id, String categoria, String nombre, int fuerza, int resistencia, int velocidad, int carisma) {
        super(id, categoria, nombre);
        this.fuerza = fuerza;
        this.resistencia = resistencia;
        this.velocidad = velocidad;
        this.carisma = carisma;
    }
    
}