/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.sql.Connection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class VendedorForm extends JFrame {

    private JTextField txtIdVendedor, txtIdUsuario, txtCodigo, txtZona;
    private JButton btnAgregar, btnConsultar, btnModificar, btnEliminar, btnCancelar;

    public VendedorForm() {
        setTitle("Gestión de Vendedores");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---------------- PANEL FORM ----------------
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Vendedor"));

        txtIdVendedor = new JTextField();
        txtIdUsuario = new JTextField();
        txtCodigo = new JTextField();
        txtZona = new JTextField();

        panelForm.add(new JLabel("ID Vendedor:"));
        panelForm.add(txtIdVendedor);
        panelForm.add(new JLabel("ID Usuario:"));
        panelForm.add(txtIdUsuario);
        panelForm.add(new JLabel("Código Vendedor:"));
        panelForm.add(txtCodigo);
        panelForm.add(new JLabel("Zona de Venta:"));
        panelForm.add(txtZona);

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
        btnAgregar.addActionListener(e -> agregarVendedor());
        btnConsultar.addActionListener(e -> consultarVendedor());
        btnModificar.addActionListener(e -> modificarVendedor());
        btnEliminar.addActionListener(e -> eliminarVendedor());
        btnCancelar.addActionListener(e -> dispose());
    }

    // ---------------- FUNCIONALIDADES CRUD ----------------
    private void agregarVendedor() {
        String sql = "INSERT INTO vendedor (id_usuario, codigo_vendedor, zona_venta) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(txtIdUsuario.getText()));
            ps.setString(2, txtCodigo.getText());
            ps.setString(3, txtZona.getText());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "✅ Vendedor agregado correctamente.");
            limpiarCampos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al agregar vendedor: " + e.getMessage());
        }
    }

    private void consultarVendedor() {
        String sql = "SELECT * FROM vendedor WHERE id_vendedor = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(txtIdVendedor.getText()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtIdUsuario.setText(String.valueOf(rs.getInt("id_usuario")));
                txtCodigo.setText(rs.getString("codigo_vendedor"));
                txtZona.setText(rs.getString("zona_venta"));
                JOptionPane.showMessageDialog(this, "✅ Vendedor encontrado.");
            } else {
                JOptionPane.showMessageDialog(this, "⚠ Vendedor no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al consultar vendedor: " + e.getMessage());
        }
    }

    private void modificarVendedor() {
        String sql = "UPDATE vendedor SET id_usuario=?, codigo_vendedor=?, zona_venta=? WHERE id_vendedor=?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(txtIdUsuario.getText()));
            ps.setString(2, txtCodigo.getText());
            ps.setString(3, txtZona.getText());
            ps.setInt(4, Integer.parseInt(txtIdVendedor.getText()));

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(this, "✅ Vendedor modificado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "⚠ Vendedor no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al modificar vendedor: " + e.getMessage());
        }
    }

    private void eliminarVendedor() {
        String sql = "DELETE FROM vendedor WHERE id_vendedor = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(txtIdVendedor.getText()));

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(this, "✅ Vendedor eliminado.");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "⚠ Vendedor no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al eliminar vendedor: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtIdVendedor.setText("");
        txtIdUsuario.setText("");
        txtCodigo.setText("");
        txtZona.setText("");
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VendedorForm().setVisible(true));
    }

    private static class ConexionBD {

        private static Connection getConnection() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public ConexionBD() {
        }
    }
}




