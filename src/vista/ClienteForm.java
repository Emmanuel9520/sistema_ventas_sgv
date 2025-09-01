/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.sql.Connection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ClienteForm extends JFrame {

    private JTextField txtId, txtNombre, txtEmail, txtTelefono, txtDireccion;
    private JButton btnAgregar, btnConsultar, btnModificar, btnEliminar, btnCancelar;

    public ClienteForm() {
        setTitle("Gestión de Clientes");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---------------- PANEL FORMULARIO ----------------
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtEmail = new JTextField();
        txtTelefono = new JTextField();
        txtDireccion = new JTextField();

        panelForm.add(new JLabel("ID Cliente:"));
        panelForm.add(txtId);
        panelForm.add(new JLabel("Nombre Completo:"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Email:"));
        panelForm.add(txtEmail);
        panelForm.add(new JLabel("Teléfono:"));
        panelForm.add(txtTelefono);
        panelForm.add(new JLabel("Dirección:"));
        panelForm.add(txtDireccion);

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
        btnAgregar.addActionListener(e -> agregarCliente());
        btnConsultar.addActionListener(e -> consultarCliente());
        btnModificar.addActionListener(e -> modificarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnCancelar.addActionListener(e -> dispose());
    }

    // ---------------- FUNCIONALIDADES CRUD ----------------
    private void agregarCliente() {
        String sql = "INSERT INTO cliente (nombre_completo, email, telefono, direccion) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtEmail.getText());
            ps.setString(3, txtTelefono.getText());
            ps.setString(4, txtDireccion.getText());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "✅ Cliente agregado correctamente.");
            limpiarCampos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al agregar cliente: " + e.getMessage());
        }
    }

    private void consultarCliente() {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(txtId.getText()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtNombre.setText(rs.getString("nombre_completo"));
                txtEmail.setText(rs.getString("email"));
                txtTelefono.setText(rs.getString("telefono"));
                txtDireccion.setText(rs.getString("direccion"));
                JOptionPane.showMessageDialog(this, "✅ Cliente encontrado.");
            } else {
                JOptionPane.showMessageDialog(this, "⚠ Cliente no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al consultar cliente: " + e.getMessage());
        }
    }

    private void modificarCliente() {
        String sql = "UPDATE cliente SET nombre_completo=?, email=?, telefono=?, direccion=? WHERE id_cliente=?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtEmail.getText());
            ps.setString(3, txtTelefono.getText());
            ps.setString(4, txtDireccion.getText());
            ps.setInt(5, Integer.parseInt(txtId.getText()));

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(this, "✅ Cliente modificado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "⚠ Cliente no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al modificar cliente: " + e.getMessage());
        }
    }

    private void eliminarCliente() {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(txtId.getText()));

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(this, "✅ Cliente eliminado.");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "⚠ Cliente no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al eliminar cliente: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClienteForm().setVisible(true));
    }

    private static class ConexionBD {

        private static Connection getConnection() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public ConexionBD() {
        }
    }
}



