/**
* Codigo del main de la tienda
* Main de la tienda Supercard
* @author Ivan García
* @version 1.0, 2024/04/22
*/
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;


public class Tienda extends Entrada_Salida{
    //Color de letra a salida por pantalla
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    
    public static Connection conexion = null;
    public static Statement st = null;

    static HashMap<Integer, Luchador> luchadores = new HashMap<Integer, Luchador>();

    //Conversión de BD a objetos
    public static void conversionCartas(){
        try {
            ResultSet rs = st.executeQuery("SELECT ca.n_carta, ca.nombre, ca.categoria, ca.precio, ca.stock, l.fuerza, l.resistencia, l.velocidad, l.carisma FROM carta ca LEFT JOIN luchador l ON ca.n_carta = l.n_carta");
            while (rs.next()) {
                luchadores.put(rs.getInt(1), new Luchador(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
            }
            System.out.println("Cartas cargadas correctamente");
        } catch (SQLException e) {
            // Lanzar tu excepción personalizada
            System.out.println("Ocurrió un error de SQL"+ e);
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

    public static int recibirStock(){
        System.out.println("Inserte el stock de la carta:");
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

    public static void registrarNuevaCartaHashMap(int nCarta){
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
            registrarNuevaCartaHashMap(nCarta);
            System.out.println("Carta registrada correctamente");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error en el registro de una nueva carta: " + e);
        }
    }

    //Búsqueda de Productos
    public static void buscarProducto() throws Exception{
        System.out.println("Menú de Búsqueda: \n1. Nº Carta (Mediante HashMap) \1n2. Nombre \n3. Categoría");
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
            if (l.getNombre().equalsIgnoreCase(nombreBusqueda)) {
                System.out.println(l.toString());
            }
        }
    }

    public static void busquedaCategoria(){
        String categoriaBusqueda = recibirCategoria();
        for (Luchador l : luchadores.values()) {
            if (l.getCategoria().equalsIgnoreCase(categoriaBusqueda)) {
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

    public static void menuActualizarCarta() throws Exception{
        System.out.println("Introduzca el nº de la carta que desea actualizar:");
        int cartaActualizar = devolverInt();
        int eleccion;
        do{
            System.out.println(ANSI_RED + "Que atributo desea modificar?" + ANSI_RESET + ANSI_CYAN +  "\n 1. Nombre \n 2. Categoría \n 3. Precio \n 4. Stock \n 5. Fuerza \n 6. Resistencia \n 7. Velocidad \n 8. Carisma \n 9. Salir"  + ANSI_RESET);
        eleccion = devolverInt();
        switch (eleccion) {
            case 1:
                actualizarCartaBD("nombre", cartaActualizar);
                break;
            case 2:
                actualizarCartaBD("categoria", cartaActualizar);
                break;
            case 3:
                actualizarCartaBD("precio", cartaActualizar);
                break;
            case 4:
                actualizarCartaBD("stock", cartaActualizar);
                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;
            case 8: 
                
                break;
            case 9:
                System.out.println("Volviendo al Menú Principal");
                break;
        }
        }while(eleccion!=9);
    }

    public static void actualizarCartaBD(String atributo, int cartaActualizar) throws Exception{
        String sql = "UPDATE carta SET ? = ? WHERE n_carta = ?";
        PreparedStatement sentenciaSQL = conexion.prepareStatement(sql);
        sentenciaSQL.setString(1, atributo);
        switch(atributo){
            case "nombre":
                sentenciaSQL.setString(2, recibirNombreCarta());
                break;
            case "categoria":
                sentenciaSQL.setString(2, recibirCategoria());
                break;
            case "precio":
                sentenciaSQL.setDouble(2, recibirPrecio());
                break;
            case "stock":
                sentenciaSQL.setInt(2, recibirStock());
                break;
        };
        sentenciaSQL.setInt(3, cartaActualizar);
    }

    public static void actualizarLuchadorBD(String atributo, int cartaActualizar) throws Exception{
        String sql = "UPDATE luchador SET ? = ? WHERE n_carta = ?";
        PreparedStatement sentenciaSQL = conexion.prepareStatement(sql);
        sentenciaSQL.setString(1, atributo);
        sentenciaSQL.setInt(2, devolverInt);
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
        System.out.println(ANSI_RED + "NºCarta \tNombre \tCategoría \tFuerza \tResistencia \tVelocidad \tCarisma \tPrecio \tStock" + ANSI_RESET);
        int controladorSalto = 0;
        for(int x = 1; x > 0; x++){
            if(controladorSalto > 6){
                System.out.println(luchadores.get(x));
                controladorSalto = 0;
                controladorContinuar();
            }else{
                System.out.println(luchadores.get(x));
                controladorSalto++;
            }
        }
        controladorContinuar();
    }

    public static void aplanarLuchadores(){
        ArrayList<Luchador> copiaLuchadores = new ArrayList<>();
        for(Luchador l : luchadores.values()){
            copiaLuchadores.add(l);
        }
        try{
            
        }catch(Exception e){
            System.out.println("Error en el aplanamiento de Luchadores: " + e);
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
    

    
    conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Supercard", "dam", "dam");
    st = conexion.createStatement();
    conversionCartas();
    int eleccion;
    do {    
        System.out.println(ANSI_RED + "Bienvenido al Sistema Gestor de la Tienda Supercard. \n Que desea realizar: " + ANSI_RESET + ANSI_CYAN +  "\n 1. Registrar Nueva Carta (BD y HashMap)" + "\n 2. Buscar Carta (HashMap)" + "\n 3. Eliminar Carta (BD y HashMap)" + "\n 4. Actualizar Carta (BD y HashMap)" + "\n 5. Mostrar Catalogo (BD)" + "\n 6. Mostrar Cartas (Con sus Características)(HashMap)" + "\n 7. Guardar catalogo en fichero (ArrayList + Aplanamiento + Ficheros)" + "\n 8. Salir" + ANSI_RESET);
        eleccion = devolverInt();
        switch (eleccion) {
            case 1:
                registrarNuevaCarta(); //Acabado
                break;
            case 2:
                buscarProducto();//Acabado
                break;
            case 3:
                eliminarCarta();//Acabado
                break;
            case 4:
                
                break;
            case 5:
                mostrarCatalogo(); //Acabado
                break;
            case 6:
                mostrarCartas(); //Acabado
                break;
            case 7:
                aplanarLuchadores();
                break;
            case 8: 
                System.out.println("Saliendo del Programa");
                break;
            default:
                break;
        }
    } while (eleccion!=8);
    System.out.println("Saliendo del Sistema Gestor de la Tienda Supercard.");
    }
}


    
