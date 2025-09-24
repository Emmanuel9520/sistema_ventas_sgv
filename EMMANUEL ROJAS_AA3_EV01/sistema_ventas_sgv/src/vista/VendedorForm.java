/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista; // Paquete donde se encuentra la clase

// Importaciones necesarias para la conexión, interfaz gráfica y SQL
import java.sql.Connection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

// Clase principal que extiende de JFrame para crear una ventana gráfica
public class VendedorForm extends JFrame {

    // Declaración de los campos de texto y botones para el formulario
    private JTextField txtIdVendedor, txtIdUsuario, txtCodigo, txtZona;
    private JButton btnAgregar, btnConsultar, btnModificar, btnEliminar, btnCancelar;

    // ---------------- CONSTRUCTOR ----------------
    public VendedorForm() {
        setTitle("Gestión de Vendedores"); // Título de la ventana
        setSize(500, 250); // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Finaliza la app al cerrar
        setLocationRelativeTo(null); // Centra la ventana en pantalla
        setLayout(new BorderLayout(10, 10)); // Layout general

        // ---------------- PANEL FORM ----------------
        // Panel con un grid de 4 filas y 2 columnas para el formulario
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Vendedor")); // Título del panel

        // Inicialización de campos de texto
        txtIdVendedor = new JTextField();
        txtIdUsuario = new JTextField();
        txtCodigo = new JTextField();
        txtZona = new JTextField();

        // Etiquetas y campos agregados al panel
        panelForm.add(new JLabel("ID Vendedor:"));
        panelForm.add(txtIdVendedor);
        panelForm.add(new JLabel("ID Usuario:"));
        panelForm.add(txtIdUsuario);
        panelForm.add(new JLabel("Código Vendedor:"));
        panelForm.add(txtCodigo);
        panelForm.add(new JLabel("Zona de Venta:"));
        panelForm.add(txtZona);

        // ---------------- PANEL BOTONES ----------------
        // Panel para los botones de acciones CRUD
        JPanel panelBotones = new JPanel(new GridLayout(1, 5, 10, 10));
        btnAgregar = new JButton("Agregar");
        btnConsultar = new JButton("Consultar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnCancelar = new JButton("Cancelar");

        // Se añaden los botones al panel
        panelBotones.add(btnAgregar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCancelar);

        // Se añaden paneles a la ventana principal
        add(panelForm, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // ---------------- EVENTOS ----------------
        // Se asocian los métodos CRUD a los botones
        btnAgregar.addActionListener(e -> agregarVendedor());
        btnConsultar.addActionListener(e -> consultarVendedor());
        btnModificar.addActionListener(e -> modificarVendedor());
        btnEliminar.addActionListener(e -> eliminarVendedor());
        btnCancelar.addActionListener(e -> dispose()); // Cierra la ventana
    }

    // ---------------- FUNCIONALIDADES CRUD ----------------

    // Método para AGREGAR un vendedor en la base de datos
    private void agregarVendedor() {
        String sql = "INSERT INTO vendedor (id_usuario, codigo_vendedor, zona_venta) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Se asignan valores desde los campos de texto
            ps.setInt(1, Integer.parseInt(txtIdUsuario.getText()));
            ps.setString(2, txtCodigo.getText());
            ps.setString(3, txtZona.getText());

            ps.executeUpdate(); // Ejecuta inserción
            JOptionPane.showMessageDialog(this, "✅ Vendedor agregado correctamente.");
            limpiarCampos(); // Limpia el formulario
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al agregar vendedor: " + e.getMessage());
        }
    }

    // Método para CONSULTAR un vendedor según su ID
    private void consultarVendedor() {
        String sql = "SELECT * FROM vendedor WHERE id_vendedor = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(txtIdVendedor.getText()));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Se cargan los valores consultados en los campos del formulario
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

    // Método para MODIFICAR los datos de un vendedor
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

    // Método para ELIMINAR un vendedor según su ID
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

    // Método para LIMPIAR los campos del formulario
    private void limpiarCampos() {
        txtIdVendedor.setText("");
        txtIdUsuario.setText("");
        txtCodigo.setText("");
        txtZona.setText("");
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {
        // Ejecuta el formulario en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> new VendedorForm().setVisible(true));
    }

    // ---------------- CLASE INTERNA DE CONEXIÓN ----------------
    private static class ConexionBD {

        // Método de conexión (aún no implementado)
        private static Connection getConnection() {
            throw new UnsupportedOperationException("Not supported yet."); 
            // Mensaje generado automáticamente, debe ser reemplazado por lógica de conexión real
        }

        public ConexionBD() {
        }
    }
}





