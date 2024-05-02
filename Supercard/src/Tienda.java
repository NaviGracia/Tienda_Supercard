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


public class Tienda extends Entrada_Salida{
    //Color de letra a salida por pantalla
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";

    static ArrayList<Luchador> luchadores = new ArrayList<>();

    //Conversión de BD a objetos
    public static String conversionCartas(Statement st){
        try {
            ResultSet rs = st.executeQuery("SELECT ca.n_carta, ca.nombre, ca.categoria, ca.precio, ca.stock, l.fuerza, l.resistencia, l.velocidad, l.carisma FROM carta ca LEFT JOIN luchador l ON ca.n_carta = l.n_carta");
            while (rs.next()) {
                luchadores.add(new Luchador(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
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

    public static String recibirCategoria(Statement st, Connection conexion){
        System.out.println("Inserte la categoría:");
        mostrarCategorias(st, conexion);
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
    public static String controladorResultados(){
        System.out.println("Presione Enter para continuar.");
        return devolverString();
    }

    //Menu Único de la tabla de BD Categoria
    public static void mostrarCategorias(Statement st, Connection conexion){//Para las busquedas
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
    public static void insertarCartaBD(int nCarta, Statement st, Connection conexion) throws Exception{
        String sql = "INSERT INTO carta VALUES(?, ?, ?, ?, 100)";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, nCarta);
        sentencia.setString(2, recibirNombreCarta());
        sentencia.setString(3, recibirCategoria(st, conexion));
        sentencia.setDouble(4, recibirPrecio());
        sentencia.executeUpdate();
    }

    public static void insertarLuchadorBD(int nCarta, Statement st, Connection conexion) throws Exception{
        String sql = "INSERT INTO luchador VALUES(?, ?, ?, ?, ?)";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, nCarta);
        sentencia.setDouble(2, recibirFuerzaCarta());
        sentencia.setDouble(3, recibirResistenciaCarta());
        sentencia.setDouble(4, recibirVelocidadCarta());
        sentencia.setDouble(5, recibirCarismaCarta());
        sentencia.executeUpdate();
    }

    public static void registrarNuevaCartaArrayList(int nCarta, Statement st){
        try {
            ResultSet rs = st.executeQuery("SELECT ca.n_carta, ca.nombre, ca.categoria, ca.precio, ca.stock, l.fuerza, l.resistencia, l.velocidad, l.carisma FROM carta ca LEFT JOIN luchador l ON ca.n_carta = l.n_carta WHERE ca.n_carta = " + nCarta);
            while (rs.next()) {
                luchadores.add(new Luchador(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error en la inserción de la nueva carta en el arraylist: "+ e);
        }
    }

    public static void registrarNuevaCarta(Connection conexion, Statement st){
        try {
            int nCarta = recibirNumCarta();
            insertarCartaBD(nCarta, st, conexion);
            insertarLuchadorBD(nCarta, st, conexion);
            System.out.println("Carta registrada correctamente");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error en el registro de una nueva carta: " + e);
        }
    }

    //Búsqueda de Productos
    public static void buscarProducto(Statement st, Connection conexion) throws Exception{
        System.out.println("Menú de Búsqueda: \n1. Nº Carta \n2. Nombre \n3. Categoría");
        //Hacer la consulta
    }
    //Eliminación de Cartas (*Aquí estoy*) (ArrayList {Como iterar})
    public static String eliminarCarta(Connection conexion){

        return "a";
    }

    public static void mostrarCatalogo(Statement st) throws Exception{
        ResultSet rs = st.executeQuery("SELECT n_carta, nombre, categoria, precio, stock FROM catalogo_cartas ORDER BY n_carta");
        int controladorCatalogo = 0;
        while (rs.next()) {
            if (controladorCatalogo == 4) {
                toStringCarta(rs);
                controladorResultados();
                controladorCatalogo = 0;
            } else{
                toStringCarta(rs);
                controladorCatalogo++;
            }
        }
        controladorResultados();
    }

    public static void mostrarCartas() throws Exception{
        //Me falta ordenar arraylist
        //Arreglar Tabulaciones
        System.out.println(ANSI_RED + "NºCarta\tNombre \tCategoría \tFuerza \tResistencia \tVelocidad \tCarisma \tPrecio \tStock" + ANSI_RESET);
        for (Luchador l : luchadores) {
            System.out.println(ANSI_CYAN + l.toString() + ANSI_RESET);
        }
    }

    




    public static void main(String[] args) throws Exception {
    //Conectar BD
    try {
        Class.forName("org.postgresql.Driver");
    } catch (Exception e) {
        // TODO: handle exception
        System.out.println("Error en registro driver");
    }

    Connection conexion = null;
    conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Supercard", "dam", "dam");
    Statement st = conexion.createStatement();
    System.out.println(conversionCartas(st));

    int eleccion;
    do {    
        System.out.println(ANSI_RED + "Bienvenido al Sistema Gestor de la Tienda Supercard. \n Que desea realizar: " + ANSI_RESET + ANSI_CYAN +  "\n 1. Registrar Nueva Carta" + "\n 2. Buscar Carta" + "\n 3. Eliminar Carta" + "\n 4. Actualizar Carta" + "\n 5. Mostrar Catalogo" + "\n 6. Mostrar Cartas (Solo Luchadores) (Con sus Características)" +  "\n 7. Salir" + ANSI_RESET);
        eleccion = devolverInt();
        switch (eleccion) {
            case 1:
                registrarNuevaCarta(conexion, st); //De momento acabado
                break;
            case 2:
                
                break;
            case 3:
            
                break;
            case 4:
                
                break;
            case 5:
                mostrarCatalogo(st); //Acabado
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


    
    