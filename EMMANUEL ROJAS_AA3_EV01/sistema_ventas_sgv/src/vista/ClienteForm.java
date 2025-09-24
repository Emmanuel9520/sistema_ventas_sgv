/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista; 
// Paquete donde se ubica la clase

import java.sql.Connection; 
import javax.swing.*;
import java.awt.*;
import java.sql.*;
// Importaciones necesarias para manejar la interfaz gráfica (Swing),
// disposición de componentes (AWT) y conexiones SQL.

// Clase que hereda de JFrame para representar una ventana de gestión de clientes
public class ClienteForm extends JFrame {

    // ---------------- DECLARACIÓN DE COMPONENTES ----------------
    private JTextField txtId, txtNombre, txtEmail, txtTelefono, txtDireccion; 
    // Campos de texto para capturar datos del cliente
    private JButton btnAgregar, btnConsultar, btnModificar, btnEliminar, btnCancelar; 
    // Botones de operaciones CRUD y cancelar

    // ---------------- CONSTRUCTOR ----------------
    public ClienteForm() {
        setTitle("Gestión de Clientes"); // Título de la ventana
        setSize(500, 300); // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Finaliza la app al cerrar
        setLocationRelativeTo(null); // Centra la ventana en pantalla
        setLayout(new BorderLayout(10, 10)); // Distribución de la interfaz

        // ---------------- PANEL FORMULARIO ----------------
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10)); 
        // GridLayout con 5 filas y 2 columnas, espacio entre componentes
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Cliente")); 
        // Borde con título descriptivo

        // Inicialización de campos de texto
        txtId = new JTextField();
        txtNombre = new JTextField();
        txtEmail = new JTextField();
        txtTelefono = new JTextField();
        txtDireccion = new JTextField();

        // Se añaden etiquetas y campos de entrada al panel
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
        // Panel con 1 fila y 5 columnas para los botones

        // Inicialización de botones CRUD
        btnAgregar = new JButton("Agregar");
        btnConsultar = new JButton("Consultar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnCancelar = new JButton("Cancelar");

        // Se añaden botones al panel
        panelBotones.add(btnAgregar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnCancelar);

        // Se integran los paneles al JFrame
        add(panelForm, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // ---------------- EVENTOS ----------------
        // Se asignan acciones a cada botón
        btnAgregar.addActionListener(e -> agregarCliente());
        btnConsultar.addActionListener(e -> consultarCliente());
        btnModificar.addActionListener(e -> modificarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnCancelar.addActionListener(e -> dispose()); // Cierra ventana
    }

    // ---------------- FUNCIONALIDADES CRUD ----------------

    // Método para insertar un cliente en la BD
    private void agregarCliente() {
        String sql = "INSERT INTO cliente (nombre_completo, email, telefono, direccion) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Se asignan valores capturados en el formulario
            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtEmail.getText());
            ps.setString(3, txtTelefono.getText());
            ps.setString(4, txtDireccion.getText());

            // Se ejecuta el INSERT
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "✅ Cliente agregado correctamente.");
            limpiarCampos(); // Limpia los campos después de insertar
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al agregar cliente: " + e.getMessage());
        }
    }

    // Método para consultar cliente por ID
    private void consultarCliente() {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(txtId.getText())); // Se usa el ID ingresado
            ResultSet rs = ps.executeQuery(); // Se ejecuta la consulta

            if (rs.next()) { 
                // Si se encuentra el cliente, se muestran los datos en los campos
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

    // Método para actualizar datos de un cliente
    private void modificarCliente() {
        String sql = "UPDATE cliente SET nombre_completo=?, email=?, telefono=?, direccion=? WHERE id_cliente=?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Se asignan los nuevos valores ingresados en los campos
            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtEmail.getText());
            ps.setString(3, txtTelefono.getText());
            ps.setString(4, txtDireccion.getText());
            ps.setInt(5, Integer.parseInt(txtId.getText()));

            int filas = ps.executeUpdate(); // Ejecuta el UPDATE
            if (filas > 0) {
                JOptionPane.showMessageDialog(this, "✅ Cliente modificado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "⚠ Cliente no encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al modificar cliente: " + e.getMessage());
        }
    }

    // Método para eliminar cliente por ID
    private void eliminarCliente() {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try (Connection con = ConexionBD.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(txtId.getText())); // Se pasa el ID del cliente

            int filas = ps.executeUpdate(); // Ejecuta el DELETE
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

    // Método para limpiar los campos de texto del formulario
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
        // Lanza la aplicación de manera segura en el hilo de eventos de Swing
    }

    // ---------------- CLASE INTERNA DE CONEXIÓN ----------------
    private static class ConexionBD {
        private static Connection getConnection() {
            // Método simulado que debería retornar una conexión a la BD
            throw new UnsupportedOperationException("Not supported yet."); 
        }

        public ConexionBD() {
            // Constructor vacío (no implementado)
        }
    }
}




