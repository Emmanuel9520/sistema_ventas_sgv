/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.sql.Connection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UsuarioForm extends JFrame {

    private JTextField txtId, txtNombre, txtEmail, txtUsuario, txtRol;
    private JPasswordField txtContrasena;
    private JButton btnAgregar, btnConsultar, btnModificar, btnEliminar, btnCancelar;

    public UsuarioForm() {
        setTitle("Gestión de Usuarios");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---------------- PANEL FORMULARIO ----------------
        JPanel panelForm = new JPanel(new GridLayout(6, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Usuario"));

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtEmail = new JTextField();
        txtUsuario = new JTextField();
        txtContrasena = new JPasswordField();
        txtRol = new JTextField();

        panelForm.add(new JLabel("ID Usuario:"));
        panelForm.add(txtId);
        panelForm.add(new JLabel("Nombre y Apellidos:"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Email:"));
        panelForm.add(txtEmail);
        panelForm.add(new JLabel("Nombre Usuario:"));
        panelForm.add(txtUsuario);
        panelForm.add(new JLabel("Contraseña:"));
        panelForm.add(txtContrasena);
        panelForm.add(new JLabel("Rol:"));
        panelForm.add(txtRol);

        // ---------------- PANEL BOTONES ----------------
        JPanel panelBotones = new JPanel(new GridLayout(1, 5, 10, 10));
        btnAgregar = new JButton("Agregar");
        btnConsultar = new JButton("Consultar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCancelar);

        add(panelForm, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // ---------------- EVENTOS ----------------
        btnAgregar.addActionListener(e -> agregarUsuario());
        btnConsultar.addActionListener(e -> consultarUsuario());
        btnModificar.addActionListener(e -> modificarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnCancelar.addActionListener(e -> dispose());
    }

    // ---------------- FUNCIONALIDADES CRUD ----------------
    private void agregarUsuario() {
        String sql = "INSERT INTO usuario (nombre_apellidos, email, nombre_usuario, contraseña, rol) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtEmail.getText());
            ps.setString(3, txtUsuario.getText());
            ps.setString(4, new String(txtContrasena.getPassword()));
            ps.setString(5, txtRol.getText());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "✅ Usuario agregado correctamente.");
            limpiarCampos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al agregar usuario: " + e.getMessage());
        }
    }

    private void consultarUsuario() {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        try (Connection con = ConexionBD.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(txtId.getText()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtNombre.setText(rs.getString("nombre_apellidos"));
                txtEmail.setText(rs.getString("email"));
                txtUsuario.setText(rs.getString("nombre_usuario"));
                txtContrasena.setText(rs.getString("contraseña"));
                txtRol.setText(rs.getString("rol"));
                JOptionPane.showMessageDialog(this, "✅ Usuario encontrado.");
            } else {
                JOptionPane.showMessageDialog(this, "⚠ Usuario no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al consultar usuario: " + e.getMessage());
        }
    }

    private void modificarUsuario() {
        String sql = "UPDATE usuario SET nombre_apellidos=?, email=?, nombre_usuario=?, contraseña=?, rol=? WHERE id_usuario=?";
        try (Connection con = ConexionBD.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtEmail.getText());
            ps.setString(3, txtUsuario.getText());
            ps.setString(4, new String(txtContrasena.getPassword()));
            ps.setString(5, txtRol.getText());
            ps.setInt(6, Integer.parseInt(txtId.getText()));

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(this, "✅ Usuario modificado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "⚠ Usuario no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al modificar usuario: " + e.getMessage());
        }
    }

    private void eliminarUsuario() {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try (Connection con = ConexionBD.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(txtId.getText()));

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(this, "✅ Usuario eliminado.");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "⚠ Usuario no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al eliminar usuario: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtEmail.setText("");
        txtUsuario.setText("");
        txtContrasena.setText("");
        txtRol.setText("");
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UsuarioForm().setVisible(true));
    }

    private static class ConexionBD {

        private static Connection getConnection() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public ConexionBD() {
        }
    }
}








