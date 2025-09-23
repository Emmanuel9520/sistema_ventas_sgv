/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas;

import Util.ConexionBD;
import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        try (Connection con = ConexionBD.getConnection()) {
            if (con != null) {
                System.out.println("✅ Conexión exitosa a la BD");
            } else {
                System.out.println("❌ No se pudo conectar a la BD");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}