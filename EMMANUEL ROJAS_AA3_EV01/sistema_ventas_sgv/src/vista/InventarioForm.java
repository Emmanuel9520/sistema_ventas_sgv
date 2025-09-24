/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
// Paquete donde se encuentra esta clase.

import java.sql.Connection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
// Importaciones necesarias para interfaz gráfica (Swing, AWT) y conexión a BD (JDBC).

public class InventarioForm extends JFrame {
    // Clase principal que extiende JFrame para crear una ventana.

    // ---------------- DECLARACIÓN DE COMPONENTES ----------------
    private JTextField txtIdInventario, txtIdProducto, txtSKU, txtFechaEntrada, txtFechaSalida,
            txtCosto, txtStockDisp, txtStockMax, txtStockMin, txtValorTotal; // Campos de texto
    private JComboBox<String> comboEstado; // Desplegable para estado del inventario
    private JButton btnAgregar, btnConsultar, btnModificar, btnEliminar, btnCancelar; // Botones CRUD

    // ---------------- CONSTRUCTOR ----------------
    public InventarioForm() {
        // Configuración general de la ventana
        setTitle("Gestión de Inventario");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana
        setLayout(new BorderLayout(10, 10));

        // ---------------- PANEL FORMULARIO ----------------
        JPanel panelForm = new JPanel(new GridLayout(11, 2, 10, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Inventario"));

        // Inicialización de campos de texto
        txtIdInventario = new JTextField();
        txtIdProducto = new JTextField();
        txtSKU = new JTextField();
        txtFechaEntrada = new JTextField();
        txtFechaSalida = new JTextField();
        txtCosto = new JTextField();
        txtStockDisp = new JTextField();
        txtStockMax = new JTextField();
        txtStockMin = new JTextField();
        txtValorTotal = new JTextField();

        // Inicialización del combo box
        comboEstado = new JComboBox<>(new String[]{"Disponible", "Agotado"});

        // Se agregan etiquetas y campos al formulario
        panelForm.add(new JLabel("ID Inventario:")); panelForm.add(txtIdInventario);
        panelForm.add(new JLabel("ID Producto:")); panelForm.add(txtIdProducto);
        panelForm.add(new JLabel("Código SKU:")); panelForm.add(txtSKU);
        panelForm.add(new JLabel("Fecha Entrada (YYYY-MM-DD):")); panelForm.add(txtFechaEntrada);
        panelForm.add(new JLabel("Fecha Salida (YYYY-MM-DD):")); panelForm.add(txtFechaSalida);
        panelForm.add(new JLabel("Costo Unitario:")); panelForm.add(txtCosto);
        panelForm.add(new JLabel("Stock Disponible:")); panelForm.add(txtStockDisp);
        panelForm.add(new JLabel("Stock Máximo:")); panelForm.add(txtStockMax);
        panelForm.add(new JLabel("Stock Mínimo:")); panelForm.add(txtStockMin);
        panelForm.add(new JLabel("Valor Total:")); panelForm.add(txtValorTotal);
        panelForm.add(new JLabel("Estado:")); panelForm.add(comboEstado);

        // ---------------- PANEL BOTONES ----------------
        JPanel panelBotones = new JPanel(new GridLayout(1, 5, 10, 10));
        btnAgregar = new JButton("Agregar");
        btnConsultar = new JButton("Consultar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnCancelar = new JButton("Cancelar");

        // Agregamos los botones al panel
        panelBotones.add(btnAgregar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCancelar);

        // Se añaden los paneles al JFrame principal
        add(panelForm, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // ---------------- EVENTOS ----------------
        btnAgregar.addActionListener(e -> agregarInventario());
        btnConsultar.addActionListener(e -> consultarInventario());
        btnModificar.addActionListener(e -> modificarInventario());
        btnEliminar.addActionListener(e -> eliminarInventario());
        btnCancelar.addActionListener(e -> dispose()); // Cierra la ventana
    }

    // ---------------- FUNCIONALIDADES CRUD ----------------
    // INSERTAR inventario
    private void agregarInventario() {
        String sql = "INSERT INTO inventario (id_producto, codigo_sku, fecha_entrada, fecha_salida, costo_unitario, " +
                "stock_disponible, stock_maximo, stock_minimo, valor_total, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Se asignan los valores desde los campos de texto
            ps.setInt(1, Integer.parseInt(txtIdProducto.getText()));
            ps.setString(2, txtSKU.getText());
            ps.setDate(3, Date.valueOf(txtFechaEntrada.getText()));
            ps.setDate(4, Date.valueOf(txtFechaSalida.getText()));
            ps.setDouble(5, Double.parseDouble(txtCosto.getText()));
            ps.setInt(6, Integer.parseInt(txtStockDisp.getText()));
            ps.setInt(7, Integer.parseInt(txtStockMax.getText()));
            ps.setInt(8, Integer.parseInt(txtStockMin.getText()));
            ps.setDouble(9, Double.parseDouble(txtValorTotal.getText()));
            ps.setString(10, comboEstado.getSelectedItem().toString());

            ps.executeUpdate(); // Ejecuta el INSERT
            JOptionPane.showMessageDialog(this, "✅ Inventario agregado correctamente.");
            limpiarCampos(); // Limpia los campos
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al agregar inventario: " + e.getMessage());
        }
    }

    // CONSULTAR inventario por ID
    private void consultarInventario() {
        String sql = "SELECT * FROM inventario WHERE id_inventario = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(txtIdInventario.getText()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Se cargan los valores en los campos
                txtIdProducto.setText(String.valueOf(rs.getInt("id_producto")));
                txtSKU.setText(rs.getString("codigo_sku"));
                txtFechaEntrada.setText(rs.getDate("fecha_entrada").toString());
                txtFechaSalida.setText(rs.getDate("fecha_salida").toString());
                txtCosto.setText(String.valueOf(rs.getDouble("costo_unitario")));
                txtStockDisp.setText(String.valueOf(rs.getInt("stock_disponible")));
                txtStockMax.setText(String.valueOf(rs.getInt("stock_maximo")));
                txtStockMin.setText(String.valueOf(rs.getInt("stock_minimo")));
                txtValorTotal.setText(String.valueOf(rs.getDouble("valor_total")));
                comboEstado.setSelectedItem(rs.getString("estado"));

                JOptionPane.showMessageDialog(this, "✅ Inventario encontrado.");
            } else {
                JOptionPane.showMessageDialog(this, "⚠ Inventario no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al consultar inventario: " + e.getMessage());
        }
    }

    // MODIFICAR inventario existente
    private void modificarInventario() {
        String sql = "UPDATE inventario SET id_producto=?, codigo_sku=?, fecha_entrada=?, fecha_salida=?, costo_unitario=?, " +
                "stock_disponible=?, stock_maximo=?, stock_minimo=?, valor_total=?, estado=? WHERE id_inventario=?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Se asignan los valores actualizados
            ps.setInt(1, Integer.parseInt(txtIdProducto.getText()));
            ps.setString(2, txtSKU.getText());
            ps.setDate(3, Date.valueOf(txtFechaEntrada.getText()));
            ps.setDate(4, Date.valueOf(txtFechaSalida.getText()));
            ps.setDouble(5, Double.parseDouble(txtCosto.getText()));
            ps.setInt(6, Integer.parseInt(txtStockDisp.getText()));
            ps.setInt(7, Integer.parseInt(txtStockMax.getText()));
            ps.setInt(8, Integer.parseInt(txtStockMin.getText()));
            ps.setDouble(9, Double.parseDouble(txtValorTotal.getText()));
            ps.setString(10, comboEstado.getSelectedItem().toString());
            ps.setInt(11, Integer.parseInt(txtIdInventario.getText()));

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(this, "✅ Inventario modificado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "⚠ Inventario no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al modificar inventario: " + e.getMessage());
        }
    }

    // ELIMINAR inventario por ID
    private void eliminarInventario() {
        String sql = "DELETE FROM inventario WHERE id_inventario = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(txtIdInventario.getText()));

            int filas = ps.executeUpdate();
            if (filas > 0) {
                JOptionPane.showMessageDialog(this, "✅ Inventario eliminado.");
                limpiarCampos(); // Limpia los campos
            } else {
                JOptionPane.showMessageDialog(this, "⚠ Inventario no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al eliminar inventario: " + e.getMessage());
        }
    }

    // LIMPIAR campos del formulario
    private void limpiarCampos() {
        txtIdInventario.setText("");
        txtIdProducto.setText("");
        txtSKU.setText("");
        txtFechaEntrada.setText("");
        txtFechaSalida.setText("");
        txtCosto.setText("");
        txtStockDisp.setText("");
        txtStockMax.setText("");
        txtStockMin.setText("");
        txtValorTotal.setText("");
        comboEstado.setSelectedIndex(0);
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {
        // Lanza la ventana en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> new InventarioForm().setVisible(true));
    }

    // ---------------- CLASE INTERNA DE CONEXIÓN ----------------
    private static class ConexionBD {
        // Método placeholder para obtener la conexión (aún no implementado)
        private static Connection getConnection() {
            throw new UnsupportedOperationException("Not supported yet."); 
        }
        public ConexionBD() {
        }
    }
}



