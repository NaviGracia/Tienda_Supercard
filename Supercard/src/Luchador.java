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

    public int getFuerza() {
        return fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    public int getResistencia() {
        return resistencia;
    }

    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getCarisma() {
        return carisma;
    }

    public void setCarisma(int carisma) {
        this.carisma = carisma;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString() + "\t" + fuerza + "\t" + resistencia + "\t" + velocidad + "\t" + carisma + "\t" + super.getPrecio() + "€ \t" + super.getStock();
    }

    

    
}