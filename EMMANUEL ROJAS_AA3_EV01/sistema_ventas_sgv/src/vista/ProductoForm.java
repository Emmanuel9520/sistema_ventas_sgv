/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
// Paquete donde está ubicada la clase

import conexion.Conexion; 
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
// Importaciones necesarias: conexión a BD, Swing y modelo de tabla

// Clase principal para gestionar productos (CRUD)
public class ProductoForm extends JFrame {

    // ----------------- COMPONENTES DE LA INTERFAZ -----------------
    private JTextField txtId, txtNombre, txtPrecio, txtStock, txtCategoria; // Campos de texto
    private JTextArea txtDescripcion;                                       // Campo de texto largo
    private JComboBox<String> cbEstado;                                     // Lista desplegable (estado)
    private JTable tabla;                                                   // Tabla para mostrar productos
    private DefaultTableModel modelo;                                       // Modelo de la tabla
    private JButton btnAgregar, btnActualizar, btnEliminar, btnListar;      // Botones CRUD
    private Conexion conexion = new Conexion();                             // Objeto de conexión

    // ----------------- CONSTRUCTOR -----------------
    public ProductoForm() {
        // Configuración inicial de la ventana
        setTitle("Gestión de Productos - Sistema de Ventas");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Distribución vertical

        // ---------- PANEL SUPERIOR (FORMULARIO) ----------
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));

        // Campos del formulario
        txtId = new JTextField(10); txtId.setEnabled(false); // ID no editable
        txtNombre = new JTextField(20);
        txtPrecio = new JTextField(10);
        txtStock = new JTextField(10);
        txtCategoria = new JTextField(15);
        txtDescripcion = new JTextArea(3, 20); // Área para varias líneas
        cbEstado = new JComboBox<>(new String[]{"Disponible", "Agotado"}); // Opciones predefinidas

        // Se agregan componentes al panel de formulario
        panelForm.add(new JLabel("ID Producto:")); panelForm.add(txtId);
        panelForm.add(new JLabel("Nombre:")); panelForm.add(txtNombre);
        panelForm.add(new JLabel("Descripción:")); panelForm.add(new JScrollPane(txtDescripcion));
        panelForm.add(new JLabel("Precio:")); panelForm.add(txtPrecio);
        panelForm.add(new JLabel("Stock:")); panelForm.add(txtStock);
        panelForm.add(new JLabel("Categoría:")); panelForm.add(txtCategoria);
        panelForm.add(new JLabel("Estado:")); panelForm.add(cbEstado);

        add(panelForm); // Se añade el formulario a la ventana

        // ---------- PANEL BOTONES ----------
        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnListar = new JButton("Listar");

        // Se agregan botones al panel
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListar);
        add(panelBotones); // Se añade el panel de botones

        // ---------- TABLA ----------
        modelo = new DefaultTableModel(); // Modelo de datos
        tabla = new JTable(modelo);       // Tabla visual
        modelo.setColumnIdentifiers(new String[]{
            "ID", "Nombre", "Descripción", "Precio", "Stock", "Categoría", "Estado"
        });
        add(new JScrollPane(tabla)); // La tabla va dentro de un scroll

        // ---------- EVENTOS ----------
        btnAgregar.addActionListener(e -> agregarProducto());     // Agregar
        btnActualizar.addActionListener(e -> actualizarProducto());// Actualizar
        btnEliminar.addActionListener(e -> eliminarProducto());   // Eliminar
        btnListar.addActionListener(e -> listarProductos());      // Listar

        // Evento al hacer clic en una fila de la tabla
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    // Se pasan los valores de la fila seleccionada a los campos
                    txtId.setText(modelo.getValueAt(fila, 0).toString());
                    txtNombre.setText(modelo.getValueAt(fila, 1).toString());
                    txtDescripcion.setText(modelo.getValueAt(fila, 2).toString());
                    txtPrecio.setText(modelo.getValueAt(fila, 3).toString());
                    txtStock.setText(modelo.getValueAt(fila, 4).toString());
                    txtCategoria.setText(modelo.getValueAt(fila, 5).toString());
                    cbEstado.setSelectedItem(modelo.getValueAt(fila, 6).toString());
                }
            }
        });
    }

    // ----------------- FUNCIONALIDADES CRUD -----------------

    // Agregar producto a la BD
    private void agregarProducto() {
        try (Connection con = conexion.getConexion()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO producto(nombre_producto,descripcion,precio,stock,categoria,estado) VALUES (?,?,?,?,?,?)"
            );
            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtDescripcion.getText());
            ps.setDouble(3, Double.parseDouble(txtPrecio.getText()));
            ps.setInt(4, Integer.parseInt(txtStock.getText()));
            ps.setString(5, txtCategoria.getText());
            ps.setString(6, cbEstado.getSelectedItem().toString());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Producto agregado");
            listarProductos(); // Refresca la tabla
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error agregar: " + e.getMessage());
        }
    }

    // Actualizar producto en la BD
    private void actualizarProducto() {
        try (Connection con = conexion.getConexion()) {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE producto SET nombre_producto=?, descripcion=?, precio=?, stock=?, categoria=?, estado=? WHERE id_producto=?"
            );
            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtDescripcion.getText());
            ps.setDouble(3, Double.parseDouble(txtPrecio.getText()));
            ps.setInt(4, Integer.parseInt(txtStock.getText()));
            ps.setString(5, txtCategoria.getText());
            ps.setString(6, cbEstado.getSelectedItem().toString());
            ps.setInt(7, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Producto actualizado");
            listarProductos(); // Refresca la tabla
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error actualizar: " + e.getMessage());
        }
    }

    // Eliminar producto de la BD
    private void eliminarProducto() {
        try (Connection con = conexion.getConexion()) {
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM producto WHERE id_producto=?"
            );
            ps.setInt(1, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Producto eliminado");
            listarProductos(); // Refresca la tabla
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error eliminar: " + e.getMessage());
        }
    }

    // Listar productos desde la BD
    private void listarProductos() {
        try (Connection con = conexion.getConexion()) {
            modelo.setRowCount(0); // Limpia la tabla
            PreparedStatement ps = con.prepareStatement("SELECT * FROM producto");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Cada fila de la BD se agrega a la tabla
                modelo.addRow(new Object[]{
                    rs.getInt("id_producto"),
                    rs.getString("nombre_producto"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    rs.getString("categoria"),
                    rs.getString("estado")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error listar: " + e.getMessage());
        }
    }

    // ----------------- MÉTODO MAIN -----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductoForm().setVisible(true));
    }
}




