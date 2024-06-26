/**
* Codigo main de la tienda
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
    public static PreparedStatement sentencia = null;
    static CuentaTienda ct = new CuentaTienda(st);

    static ArrayList<Luchador> copiaLuchadores = new ArrayList<>();
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

    public static void registrarNuevaCarta(){
        try {
            Luchador l = new Luchador(recibirNumCarta(), recibirNombreCarta(), recibirCategoria(st), recibirPrecio(), recibirFuerza(), recibirResistencia(), recibirVelocidad(), recibirCarisma(), 100);
            l.insertarCartaBD(sentencia);
            insertarLuchadorBD(nCarta);
            registrarNuevaCartaHashMap(nCarta);
            System.out.println("Carta registrada correctamente");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error en el registro de una nueva carta: " + e);
        }
    }

    //Insertar Datos a la BD
    public static void insertarCartaBD(int nCarta) throws Exception{
        String sql = "INSERT INTO carta VALUES(?, ?, ?, ?, 100)";
        sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, nCarta);
        sentencia.setString(2, recibirNombreCarta());
        sentencia.setString(3, recibirCategoria(st));
        sentencia.setDouble(4, recibirPrecio());
        sentencia.executeUpdate();
    }

    public static void insertarLuchadorBD(int nCarta) throws Exception{
        String sql = "INSERT INTO luchador VALUES(?, ?, ?, ?, ?)";
        sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, nCarta);
        sentencia.setDouble(2, devolverInt());
        System.out.println("Inserte la resistencia de la carta:");
        sentencia.setDouble(3, devolverInt());
        System.out.println("Inserte la velocidad de la carta:");
        sentencia.setDouble(4, devolverInt());
        System.out.println("Inserte el carisma de la carta:");
        sentencia.setDouble(5, devolverInt());
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

    //Búsqueda de Productos
    public static void buscarProducto() throws Exception{
        System.out.println("Menú de Búsqueda: \n1. Nº Carta (Mediante HashMap) \n2. Nombre \n3. Categoría");
        int eleccion = devolverInt();
        if(eleccion < 1 || eleccion > 3){
            throw new ExcepcionFalloSubmenu("Nº Incorrecto");
        }else{
            switch (eleccion) {
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
        String categoriaBusqueda = recibirCategoria(st);
        for (Luchador l : luchadores.values()) {
            if (l.getCategoria().equalsIgnoreCase(categoriaBusqueda)) {
                controladorObjetos(l);
            }
        }
    }

    //Eliminación de Cartas 
    public static void eliminarCarta(){
        sentencia = null;
        String sql = "DELETE FROM carta WHERE n_carta = ?";
        try {
            sentencia = conexion.prepareStatement(sql);
            int n_carta_eliminar = recibirNumCarta();
            luchadores.remove(n_carta_eliminar);
            sentencia.setInt(1, n_carta_eliminar);
            sentencia.executeQuery();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Carta eliminada correctamente");;
        }
    }

    //Actualización de una carta
    public static void menuActualizarCarta() throws Exception{
        System.out.println("Introduzca el nº de la carta que desea actualizar:");
        int cartaActualizar = devolverInt();
        int eleccion;
        do{
            System.out.println(ANSI_RED + "Que atributo desea modificar?" + ANSI_RESET + ANSI_CYAN +  "\n 1. Nombre \n 2. Categoría \n 3. Precio \n 4. Stock" + ANSI_RESET);
            eleccion = devolverInt();
            if(eleccion < 1 || eleccion > 5){
                throw new ExcepcionFalloSubmenu("Nº Incorrecto");
            }else{
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
                        System.out.println("Volviendo al Menú Principal");
                        break;
                }
            }
        }while(eleccion!=5);
        System.out.println("Carta Actualizada");
    }

    public static void actualizarCartaBD(String atributo, int cartaActualizar) throws Exception{
        String sql = "UPDATE carta SET ? = ? WHERE n_carta = ?";
        sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, atributo);
        switch(atributo){
            case "nombre":
                sentencia.setString(2, recibirNombreCarta());
                break;
            case "categoria":
                sentencia.setString(2, recibirCategoria(st));
                break;
            case "precio":
                sentencia.setDouble(2, recibirPrecio());
                break;
            case "stock":
                System.out.println("Inserte el stock de la carta:");
                sentencia.setInt(2, devolverInt());
                break;
        };
        sentencia.setInt(3, cartaActualizar);
    }

    public static void mostrarCatalogo() throws Exception{
        ResultSet rs = st.executeQuery("SELECT n_carta, nombre, categoria, precio, stock FROM catalogo_cartas ORDER BY n_carta");
        int controladorCatalogo = 0;
        Luchador l = new Luchador(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));
        while (rs.next()) {
            if (controladorCatalogo == 6) {
                System.out.println(l.toString());
                controladorContinuar();
                controladorCatalogo = 0;
            } else{
                System.out.println(l.toString());
                controladorCatalogo++;
            }
        }
        controladorContinuar();
    }

    public static void mostrarCartas() throws Exception{
        System.out.println(ANSI_RED + "NºCarta    Nombre \t\t     Categoría \t\t  Fuerza \tResistencia \tVelocidad \tCarisma \tPrecio \tStock" + ANSI_RESET);
        int controladorSalto = 0;
        for (HashMap.Entry<Integer, Luchador> entry : luchadores.entrySet()) {
            System.out.println(entry.getValue().toString());
            if(controladorSalto > 6){
                controladorSalto = 0;
                controladorContinuar();
            }else{
                controladorSalto++;
            }
        }
        controladorContinuar();
    }

    public static void aplanarLuchadores(){
        generarCopiaHashMap();
        try{
            FileOutputStream fout = new FileOutputStream("src/CopiasCatalogo/CopiaCatalogo.txt");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(copiaLuchadores);
            out.close();
            System.out.println("Copia creada con éxito");
        }catch(Exception e){
            System.out.println("Error en el aplanamiento de Luchadores: " + e);
        }
    }

    public static void generarCopiaHashMap(){
        for(Luchador l : luchadores.values()){
            copiaLuchadores.add(l);
        }
    }

    public static void reemplazarHashMap(){
        try{
            borrarTodo();
            for(Luchador l : cargarCopia()){
                anyadirEnHashMap(l);
                anyadirEnBD(l);
            }
            System.out.println("Copia Cargada");
        }catch(Exception e){
            System.out.println("Error sql: " + e);
        }
    }

    public static ArrayList<Luchador> cargarCopia() throws Exception{
        FileInputStream fis = new FileInputStream("src/CopiasCatalogo/CopiaCatalogo.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<Luchador> copiaCargada = (ArrayList<Luchador>)ois.readObject();
        ois.close();
        return copiaCargada;
    }

    public static void borrarTodo() throws Exception{
        luchadores.clear(); 
        sentencia = conexion.prepareStatement("DELETE FROM carta");
        sentencia.executeUpdate();
    }

    public static void anyadirEnHashMap(Luchador l) throws Exception{
            luchadores.put(l.getN_carta(), l);
            String sql = "INSERT INTO carta VALUES(?, ?, ?, ?, 100)";
            sentencia = conexion.prepareStatement(sql);
            sentencia.setInt(1, l.getN_carta());
            sentencia.setString(2, l.getNombre());
            sentencia.setString(3, l.getCategoria());
            sentencia.setDouble(4, l.getPrecio());
            sentencia.executeUpdate();
    }

    public static void anyadirEnBD(Luchador l) throws Exception{
        String sql = "INSERT INTO luchador VALUES(?, ?, ?, ?, ?)";
        sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, l.getN_carta());
        sentencia.setDouble(2, l.getFuerza());
        sentencia.setDouble(3, l.getResistencia());
        sentencia.setDouble(4, l.getVelocidad());
        sentencia.setDouble(5, l.getCarisma());
        sentencia.executeUpdate();
    }

    public static void insertarNuevaCategoria() throws Exception{
        String sql = "INSERT INTO categoria VALUES(?, ?)";
        sentencia = conexion.prepareStatement(sql);
        System.out.println("Inserte la nueva categoría:");
        sentencia.setString(1, devolverString());
        System.out.println("Inserte la fecha de lanzamiento:");
        sentencia.setString(2, devolverString());
        sentencia.executeUpdate();
        System.out.println("Categoría Generada Correctamente");
        controladorContinuar();
    }

    public static void ventaCarta(){
        System.out.println("Inserte el nº de la carta vendida:");
        int cartaVendida = devolverInt();
        System.out.println("Se ha aplicado un descuento? (true o false)");
        boolean descuento = devolverBoolean();
        if(descuento = true){
            ct.venta(obtenerPrecioDescontado(cartaVendida));
        }else{
            ct.venta(luchadores.get(cartaVendida).getPrecio());
            luchadores.get(cartaVendida).venderCarta();
        }
    }

    public static double obtenerPrecioDescontado(int cartaVendida){
        System.out.println("Introduzca el descuento: (En porcentaje solo nº)");
        double precioDescontado = luchadores.get(cartaVendida).aplicarDescuento(luchadores.get(cartaVendida).getPrecio(), devolverDouble());
        return precioDescontado;
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
        System.out.println(ANSI_RED + "Bienvenido al Sistema Gestor de la Tienda Supercard. \n Que desea realizar: " + ANSI_RESET + ANSI_CYAN +  "\n 1. Registrar Nueva Carta (BD y HashMap)" + 
        "\n 2. Buscar Carta (HashMap)" + "\n 3. Eliminar Carta (BD y HashMap)" + "\n 4. Actualizar Carta (BD y HashMap)" + "\n 5. Mostrar Catalogo (BD)" + 
        "\n 6. Mostrar Cartas (Con sus Características)(HashMap)" + "\n 7. Realizar Copia del Catalogo (ArrayList + Aplanamiento + Ficheros)" + "\n 8. Cargar Copia Catalogo(Solo en el HashMap)" + 
        "\n 9. Crear nueva categoría" + "\n 10. Vender Carta" + "\n 11. Consultar Cuenta Tienda" + "\n 12. Salir" + ANSI_RESET);
        eleccion = devolverInt();
        switch (eleccion) {
            case 1:
                registrarNuevaCarta(); 
                break;
            case 2:
                buscarProducto();
                break;
            case 3:
                eliminarCarta();
                break;
            case 4:
                menuActualizarCarta();
                break;
            case 5:
                mostrarCatalogo(); 
                break;
            case 6:
                mostrarCartas();
                break;
            case 7:
                aplanarLuchadores();
                controladorContinuar();
                break;
            case 8: 
                reemplazarHashMap();
                controladorContinuar();
                break;
            case 9:
                insertarNuevaCategoria();
                controladorContinuar();
                break;
            case 10:
                ventaCarta();
                break;
            case 11:
                System.out.println("Saldo actual: " + ct.getDinero());
                break;
            case 12:
                System.out.println("Saliendo del Programa");
                break;
            default:
                break;
        }
    } while (eleccion!=12);
    System.out.println("Saliendo del Sistema Gestor de la Tienda Supercard.");
    }
}