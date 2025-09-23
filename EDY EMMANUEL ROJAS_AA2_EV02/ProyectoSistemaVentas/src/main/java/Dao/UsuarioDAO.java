/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import Util.ConexionBD;
import modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UsuarioDAO {

    // Método para crear un nuevo usuario (para el registro)
    public boolean crearUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre_apellidos, email, nombre_usuario, contrasena, rol) VALUES (?, ?, ?, ?, ?)";
        Connection conexion = null;
        PreparedStatement ps = null;

        try {
            conexion = ConexionBD.obtenerConexion();
            ps = conexion.prepareStatement(sql);
            ps.setString(1, usuario.getNombreApellidos());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getNombreUsuario());
            ps.setString(4, usuario.getContrasena());
            ps.setString(5, usuario.getRol());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear un nuevo usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para validar las credenciales de un usuario (para el inicio de sesión)
    public Usuario validarUsuario(String email, String password) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND contrasena = ?";
        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = ConexionBD.obtenerConexion();
            ps = conexion.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombreApellidos(rs.getString("nombre_apellidos"));
                usuario.setEmail(rs.getString("email"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setContrasena(rs.getString("contrasena"));
                usuario.setRol(rs.getString("rol"));
                usuario.setUltimoAcceso(rs.getTimestamp("ultimo_acceso"));

                // 🔥 actualizar último acceso
                actualizarUltimoAcceso(usuario.getIdUsuario());

                return usuario;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error al validar usuario: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conexion != null) conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 🔍 Verificar si ya existe un usuario con ese correo
    public boolean existeUsuarioPorEmail(String email) {
        String sql = "SELECT id_usuario FROM usuarios WHERE email = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true si encontró un usuario
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔥 Actualizar el último acceso al loguear
    private void actualizarUltimoAcceso(int idUsuario) {
        String sql = "UPDATE usuarios SET ultimo_acceso = ? WHERE id_usuario = ?";
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            ps.setInt(2, idUsuario);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}