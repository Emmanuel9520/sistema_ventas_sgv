/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    
    // Configuración de la conexión a la base de datos
    // ¡IMPORTANTE! Reemplaza estos valores con los tuyos
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_ventas_s_g_v";
    private static final String USUARIO = "root";
    private static final String CLAVE = "Samara082020$";

    /**
     * Establece y retorna una conexión a la base de datos.
     * @return Objeto Connection si la conexión es exitosa, de lo contrario null.
     */
    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            // Se establece la conexión con los parámetros definidos
            conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
            System.out.println("✅ ¡Conexión a la base de datos exitosa!");
        } catch (SQLException e) {
            System.out.println("❌ ¡Error de conexión a la base de datos!");
            // Muestra la causa raíz del error, esto es CRÍTICO para depurar
            e.printStackTrace(); 
            // En un entorno de producción, esta línea se reemplazaría por un logger
        }
        return conexion;
    }
    
    // Este método es opcional, pero ayuda a cerrar la conexión
    public static void cerrarConexion(Connection conexion) {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Cerrada la conexión a la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
