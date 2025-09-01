/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.sql.Connection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class InformesForm extends JFrame {

    private JTextField txtIdInforme, txtIdUsuario, txtTipoInforme, txtTitulo, txtRangoFechas, txtFormato;
    private JTextArea txtContenido;
    private JTable tabla;
    private JButton btnAgregar, btnConsultar, btnModificar, btnEliminar, btnExportarPDF;

    public InformesForm() {
        setTitle("Gestión de Informes");
        setSize(950, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ---- FORMULARIO IZQUIERDO ----
        JPanel panelForm = new JPanel(new GridLayout(7, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Informe"));

        txtIdInforme = new JTextField();
        txtIdUsuario = new JTextField();
        txtTipoInforme = new JTextField();
        txtTitulo = new JTextField();
        txtRangoFechas = new JTextField();
        txtFormato = new JTextField();
        txtContenido = new JTextArea(3, 15);

        panelForm.add(new JLabel("ID Informe:"));
        panelForm.add(txtIdInforme);
        panelForm.add(new JLabel("ID Usuario:"));
        panelForm.add(txtIdUsuario);
        panelForm.add(new JLabel("Tipo Informe:"));
        panelForm.add(txtTipoInforme);
        panelForm.add(new JLabel("Título:"));
        panelForm.add(txtTitulo);
        panelForm.add(new JLabel("Rango Fechas:"));
        panelForm.add(txtRangoFechas);
        panelForm.add(new JLabel("Formato:"));
        panelForm.add(txtFormato);
        panelForm.add(new JLabel("Contenido:"));
        panelForm.add(new JScrollPane(txtContenido));

        // ---- TABLA DERECHA ----
        tabla = new JTable();
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Lista de Informes"));

        JPanel panelCentro = new JPanel(new GridLayout(1, 2, 10, 10));
        panelCentro.add(panelForm);
        panelCentro.add(scrollTabla);

        // ---- BOTONES ----
        btnAgregar = new JButton("Agregar");
        btnConsultar = new JButton("Consultar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnExportarPDF = new JButton("Exportar PDF");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnConsultar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnExportarPDF);

        // ---- ESTRUCTURA FINAL ----
        add(panelCentro, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // ---- EVENTOS CRUD ----
        btnAgregar.addActionListener(e -> agregarInforme());
        btnConsultar.addActionListener(e -> cargarInformes());
        btnModificar.addActionListener(e -> modificarInforme());
        btnEliminar.addActionListener(e -> eliminarInforme());

        // ---- Estilo visual ----
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 12));
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 12));
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 12));
        UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 12));

        cargarInformes(); // Carga inicial de informes
    }

    // ---- FUNCIONALIDADES CURD ----

    private void agregarInforme() {
        try (Connection con = ConexionBD.getConnection()) {
            String sql = "INSERT INTO informes (id_informe, id_usuario, tipo_informe, titulo, rango_fechas, contenido, formato) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(txtIdInforme.getText()));
            ps.setInt(2, Integer.parseInt(txtIdUsuario.getText()));
            ps.setString(3, txtTipoInforme.getText());
            ps.setString(4, txtTitulo.getText());
            ps.setString(5, txtRangoFechas.getText());
            ps.setString(6, txtContenido.getText());
            ps.setString(7, txtFormato.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Informe agregado correctamente.");
            cargarInformes();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar: " + ex.getMessage());
        }
    }

    private void cargarInformes() {
        try (Connection con = ConexionBD.getConnection()) {
            String sql = "SELECT * FROM informes";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID Informe");
            model.addColumn("ID Usuario");
            model.addColumn("Tipo Informe");
            model.addColumn("Título");
            model.addColumn("Rango Fechas");
            model.addColumn("Contenido");
            model.addColumn("Formato");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_informe"),
                    rs.getInt("id_usuario"),
                    rs.getString("tipo_informe"),
                    rs.getString("titulo"),
                    rs.getString("rango_fechas"),
                    rs.getString("contenido"),
                    rs.getString("formato")
                });
            }
            tabla.setModel(model);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al consultar: " + ex.getMessage());
        }
    }

    private void modificarInforme() {
        try (Connection con = ConexionBD.getConnection()) {
            String sql = "UPDATE informes SET id_usuario=?, tipo_informe=?, titulo=?, rango_fechas=?, contenido=?, formato=? WHERE id_informe=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(txtIdUsuario.getText()));
            ps.setString(2, txtTipoInforme.getText());
            ps.setString(3, txtTitulo.getText());
            ps.setString(4, txtRangoFechas.getText());
            ps.setString(5, txtContenido.getText());
            ps.setString(6, txtFormato.getText());
            ps.setInt(7, Integer.parseInt(txtIdInforme.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Informe modificado correctamente.");
            cargarInformes();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar: " + ex.getMessage());
        }
    }

    private void eliminarInforme() {
        try (Connection con = ConexionBD.getConnection()) {
            String sql = "DELETE FROM informes WHERE id_informe=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(txtIdInforme.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Informe eliminado correctamente.");
            cargarInformes();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
        }
    }

    // ---- MAIN ----
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InformesForm().setVisible(true));
    }

    private static class ConexionBD {

        private static Connection getConnection() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public ConexionBD() {
        }
    }
}



