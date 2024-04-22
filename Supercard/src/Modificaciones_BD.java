
 //Insertar Datos a la BD
 static public void insertarRegistro(Connection conexion){
    System.out.println("Inserta la tabla a la que desea añadir registros:");
    String tabla = sc.nextLine();
    String sentenciaSQL = "INSERT INTO " + tabla + " VALUES(?, ?, ?)";
    PreparedStatement sentencia = null;

    try{
        sentencia = conexion.prepareStatement(sentenciaSQL);
        for (int i = 1; i <= 3; i++) {
            System.out.println("Inserte el valor de la columna " + i + " de la tabla:");
            String valor = sc.nextLine();
            sentencia.setString(i, valor);
        }
        if (tabla.equalsIgnoreCase("mejoras")) {
            System.out.println("Inserte el cuarto valor de la tabla:");
            String cuartoValor = sc.nextLine();
            sentencia.setString(4, cuartoValor);
        }
        sentencia.executeUpdate();
    }catch (SQLException sqle) {
        sqle.printStackTrace();
    } finally {
        if (sentencia != null)
            try {
                sentencia.close();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        } 
}
//Consultar en la BD  
static public void consultaBD(Statement st, Connection conexion) throws Exception{
     //Consulta
     conexion.commit();
     System.out.println("Inserta la tabla a consultar:");
     String tabla = sc.nextLine();
     ResultSet rs = st.executeQuery("SELECT * FROM " + tabla);
     int n = 0;
     System.out.println("Mostrando Tabla " + tabla + ":");
     while (rs.next()) {
         System.out.println(ANSI_RED + rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + ANSI_RESET);
         n++;
     }
     rs.close();
}
//Eliminar registros de la base de datos
static public void eliminarRegistro(Connection conexion){
    System.out.println("Inserta la tabla de la que desea eliminar registros:");
    String tabla = sc.nextLine();
    if (tabla.equalsIgnoreCase("jokers")) {
        String sentenciaSql = "DELETE FROM " + tabla + " WHERE nombre = ?"; //Aquí es importante incluir el Statement en el if porque hay que buscar por *nombre*, que es una columna de jokers
        PreparedStatement sentencia = null;
        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            System.out.println("Introduce el nombre del joker a eliminar:");
            String nombre = sc.nextLine();
            sentencia.setString(1, nombre);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
        sqle.printStackTrace();
        } finally {
        if (sentencia != null)
            try {
            sentencia.close();
            } catch (SQLException sqle) {
            sqle.printStackTrace();
            }
        }
    }
}

public static void modificarRegistro(Connection conexion){
    System.out.println("Inserta la tabla de la que desea eliminar registros:");
    String tabla = sc.nextLine();
    if (tabla.equalsIgnoreCase("jokers")) {
        String sentenciaSQL = "UPDATE " + tabla + "SET nombre = ?, efecto = ?, rareza = ? WHERE nombre = ?";
        PreparedStatement sentencia = null;
        sentencia = conexion.prepareStatement(sentenciaSQL);
        sentencia.setString(1, sentenciaSQL);
    }
}