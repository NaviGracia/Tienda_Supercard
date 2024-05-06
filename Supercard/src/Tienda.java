/**
* Codigo del main de la tienda
* Main de la tienda Supercard
* @author Ivan García
* @version 1.0, 2024/04/22
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Tienda extends Entrada_Salida{
    //Color de letra a salida por pantalla
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    
    public static Connection conexion = null;
    public static Statement st = null;

    static HashMap<Integer, Luchador> luchadores = new HashMap<Integer, Luchador>();

    //Conversión de BD a objetos
    public static String conversionCartas(){
        try {
            ResultSet rs = st.executeQuery("SELECT ca.n_carta, ca.nombre, ca.categoria, ca.precio, ca.stock, l.fuerza, l.resistencia, l.velocidad, l.carisma FROM carta ca LEFT JOIN luchador l ON ca.n_carta = l.n_carta");
            while (rs.next()) {
                luchadores.put(rs.getInt(1), new Luchador(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
            }
            return "Cartas cargadas correctamente";
        } catch (Exception e) {
            // TODO: handle exception
            String error = "Error en la conversion de cartas: " + e;
            return error;
        }
    }

    //Introduccion de datos específicos
    public static int recibirNumCarta(){
        System.out.println("Inserte el nº de la carta:");
        return devolverInt();
    }

    public static String recibirNombreCarta(){
        System.out.println("Inserte el nombre:");
        return devolverString();
    }

    public static String recibirCategoria(){
        System.out.println("Inserte la categoría:");
        mostrarCategorias();
        return devolverString();
    }
    
    public static double recibirPrecio(){
        System.out.println("Inserte el precio");
        return devolverDouble();
    }

    public static int recibirFuerzaCarta(){
        System.out.println("Inserte la fuerza de la carta:");
        return devolverInt();
    }

    public static int recibirResistenciaCarta(){
        System.out.println("Inserte la resistencia de la carta:");
        return devolverInt();
    }

    public static int recibirVelocidadCarta(){
        System.out.println("Inserte la velocidad de la carta:");
        return devolverInt();
    }

    public static int recibirCarismaCarta(){
        System.out.println("Inserte el carisma de la carta:");
        return devolverInt();
    }

    public static void toStringCarta(ResultSet rs) throws Exception{
        System.out.println(ANSI_CYAN + rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getDouble(4) + "€ " + rs.getInt(5) + ANSI_RESET);
    }

    //Controlador de Resultados
    public static String controladorContinuar(){
        System.out.println("Presione Enter para continuar.");
        return devolverString();
    }

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

    //Menu Único de la tabla de BD Categoria
    public static void mostrarCategorias(){
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
    //Insertar Datos a la BD
    public static void insertarCartaBD(int nCarta) throws Exception{
        String sql = "INSERT INTO carta VALUES(?, ?, ?, ?, 100)";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, nCarta);
        sentencia.setString(2, recibirNombreCarta());
        sentencia.setString(3, recibirCategoria());
        sentencia.setDouble(4, recibirPrecio());
        sentencia.executeUpdate();
    }

    public static void insertarLuchadorBD(int nCarta) throws Exception{
        String sql = "INSERT INTO luchador VALUES(?, ?, ?, ?, ?)";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, nCarta);
        sentencia.setDouble(2, recibirFuerzaCarta());
        sentencia.setDouble(3, recibirResistenciaCarta());
        sentencia.setDouble(4, recibirVelocidadCarta());
        sentencia.setDouble(5, recibirCarismaCarta());
        sentencia.executeUpdate();
    }

    public static void registrarNuevaCartaArrayList(int nCarta){
        try {
            ResultSet rs = st.executeQuery("SELECT ca.n_carta, ca.nombre, ca.categoria, ca.precio, ca.stock, l.fuerza, l.resistencia, l.velocidad, l.carisma FROM carta ca LEFT JOIN luchador l ON ca.n_carta = l.n_carta WHERE ca.n_carta = " + nCarta);
            while (rs.next()) {
                luchadores.put(rs.getInt(1), new Luchador(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error en la inserción de la nueva carta en el arraylist: "+ e);
        }
    }

    public static void registrarNuevaCarta(){
        try {
            int nCarta = recibirNumCarta();
            insertarCartaBD(nCarta);
            insertarLuchadorBD(nCarta);
            registrarNuevaCartaArrayList(nCarta);
            System.out.println("Carta registrada correctamente");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error en el registro de una nueva carta: " + e);
        }
    }

    //Búsqueda de Productos
    public static void buscarProducto() throws Exception{
        System.out.println("Menú de Búsqueda: \n1. Nº Carta (Mediante HashMap) \n2. Nombre \n3. Categoría");
        switch (devolverInt()) {
            case 1:
                busquedaNCarta();
                break;
            case 2:
                busquedaNombre();
                break;
            case 3:
                busquedaCategoria();
                break;
        }
    }

    public static void busquedaNCarta(){
        int nCartaBusqueda = recibirNumCarta();
        for (Luchador l : luchadores.values()) {
            if (l.getN_carta()==nCartaBusqueda) {
                System.out.println(l.toString());
            }
        }
    }

    public static void busquedaNombre(){
        String nombreBusqueda = recibirNombreCarta();
        for (Luchador l : luchadores.values()) {
            if (l.getNombre()==nombreBusqueda) {
                System.out.println(l.toString());
            }
        }
    }

    public static void busquedaCategoria(){
        String categoriaBusqueda = recibirCategoria();
        for (Luchador l : luchadores.values()) {
            if (l.getCategoria()==categoriaBusqueda) {
                controladorObjetos(l);
            }
        }
    }

    //Eliminación de Cartas 
    public static void eliminarCarta(){
        PreparedStatement sentencia = null;
        String sql = "DELETE FROM carta WHERE n_carta = ?";
        try {
            sentencia = conexion.prepareStatement(sql);
            int n_carta_eliminar = recibirNumCarta();
            luchadores.remove(n_carta_eliminar);
            sentencia.setInt(1, n_carta_eliminar);
            sentencia.executeQuery();
            System.out.println("Carta eliminada correctamente");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Fallo en la eliminación de cartas: " + e);

        }
    }

    public static void mostrarCatalogo() throws Exception{
        ResultSet rs = st.executeQuery("SELECT n_carta, nombre, categoria, precio, stock FROM catalogo_cartas ORDER BY n_carta");
        int controladorCatalogo = 0;
        while (rs.next()) {
            if (controladorCatalogo == 6) {
                toStringCarta(rs);
                controladorContinuar();
                controladorCatalogo = 0;
            } else{
                toStringCarta(rs);
                controladorCatalogo++;
            }
        }
        controladorContinuar();
    }

    public static void mostrarCartas() throws Exception{
        //Arreglar Tabulaciones
        System.out.println(ANSI_RED + "NºCarta\tNombre \tCategoría \tFuerza \tResistencia \tVelocidad \tCarisma \tPrecio \tStock" + ANSI_RESET);
        int controladorCatalogo = 0;
        for(Luchador l : luchadores.values()){
            controladorObjetos(l);
        }
        controladorContinuar();
    }

    




    public static void main(String[] args) throws Exception {
    //Conectar BD
    try {
        Class.forName("org.postgresql.Driver");
    } catch (Exception e) {
        // TODO: handle exception
        System.out.println("Error en registro driver");
    }

    
    conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Supercard", "dam", "dam");
    st = conexion.createStatement();
    System.out.println(conversionCartas());

    int eleccion;
    do {    
        System.out.println(ANSI_RED + "Bienvenido al Sistema Gestor de la Tienda Supercard. \n Que desea realizar: " + ANSI_RESET + ANSI_CYAN +  "\n 1. Registrar Nueva Carta (BD y HashMap)" + "\n 2. Buscar Carta (HashMap)" + "\n 3. Eliminar Carta (BD y HashMap)" + "\n 4. Actualizar Carta (BD y HashMap)" + "\n 5. Mostrar Catalogo (BD)" + "\n 6. Mostrar Cartas (HashMap) (Con sus Características)" +  "\n 7. Salir" + ANSI_RESET);
        eleccion = devolverInt();
        switch (eleccion) {
            case 1:
                registrarNuevaCarta(); //De momento acabado
                break;
            case 2:
                buscarProducto();
                break;
            case 3:
                eliminarCarta();
                break;
            case 4:
                
                break;
            case 5:
                mostrarCatalogo(); //Acabado
                break;
            case 6:
                mostrarCartas();
                break;
            case 7: 
                System.out.println("Saliendo del Programa");
                break;
            default:
                break;
        }
    } while (eleccion!=7);
    System.out.println("Saliendo del Sistema Gestor de la Tienda Supercard.");
    }
}


    
