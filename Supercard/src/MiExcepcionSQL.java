import java.sql.SQLException;

// Definición de tu excepción personalizada
public class MiExcepcionSQL extends Exception {
    public MiExcepcionSQL(String mensaje, SQLException causa) {
        super(mensaje, causa);
    }
}
