/**
* Creación de nueva excepción
* @author Ivan García
* @version 1.0, 2024/04/22
*/
import java.sql.SQLException;

// Definición de tu excepción personalizada
public class MiExcepcionSQL extends Exception {
    public MiExcepcionSQL(String mensaje, SQLException causa) {
        super(mensaje, causa);
    }
}
