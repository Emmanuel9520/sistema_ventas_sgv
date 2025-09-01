/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentasForm extends JFrame {
    
    Connection con;
    DefaultTableModel modelo;
    JTable tabla;
    
    // Campos
    JTextField txtIdVenta, txtIdProducto, txtFecha, txtIdVendedor, txtIdCliente, txtCantidad,
               txtDescuento, txtSubtotal, txtImpuesto, txtTotal, txtFormaPago, txtEstado, txtObs;
    
    public VentasForm() {
        setTitle("Módulo Ventas - CRUD");
        setSize(950,550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // ==== TABS ====
        JTabbedPane tabs = new JTabbedPane();
        
        // ==== PANEL FORMULARIO ====
        JPanel panelForm = new JPanel(new BorderLayout(5,5));
        
        // Campos
        JPanel panelCampos = new JPanel(new GridLayout(7,4,5,5));
        
        panelCampos.add(new JLabel("ID Venta:")); txtIdVenta=new JTextField(); panelCampos.add(txtIdVenta);
        panelCampos.add(new JLabel("ID Producto:")); txtIdProducto=new JTextField(); panelCampos.add(txtIdProducto);
        
        panelCampos.add(new JLabel("Fecha (YYYY-MM-DD):")); txtFecha=new JTextField(); panelCampos.add(txtFecha);
        panelCampos.add(new JLabel("ID Vendedor:")); txtIdVendedor=new JTextField(); panelCampos.add(txtIdVendedor);
        
        panelCampos.add(new JLabel("ID Cliente:")); txtIdCliente=new JTextField(); panelCampos.add(txtIdCliente);
        panelCampos.add(new JLabel("Cantidad:")); txtCantidad=new JTextField(); panelCampos.add(txtCantidad);
        
        panelCampos.add(new JLabel("Descuento:")); txtDescuento=new JTextField(); panelCampos.add(txtDescuento);
        panelCampos.add(new JLabel("Subtotal:")); txtSubtotal=new JTextField(); panelCampos.add(txtSubtotal);
        
        panelCampos.add(new JLabel("Impuesto:")); txtImpuesto=new JTextField(); panelCampos.add(txtImpuesto);
        panelCampos.add(new JLabel("Total:")); txtTotal=new JTextField(); panelCampos.add(txtTotal);
        
        panelCampos.add(new JLabel("Forma Pago:")); txtFormaPago=new JTextField(); panelCampos.add(txtFormaPago);
        panelCampos.add(new JLabel("Estado:")); txtEstado=new JTextField(); panelCampos.add(txtEstado);
        
        panelCampos.add(new JLabel("Observaciones:")); txtObs=new JTextField(); panelCampos.add(txtObs);
        panelForm.add(panelCampos, BorderLayout.CENTER);
        
        // Botones
        JPanel panelBotones = new JPanel(new GridLayout(1,4,10,10));
        JButton btnAgregar=new JButton("Agregar");
        JButton btnActualizar=new JButton("Actualizar");
        JButton btnEliminar=new JButton("Eliminar");
        JButton btnListar=new JButton("Listar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnListar);
        panelForm.add(panelBotones, BorderLayout.SOUTH);
        
        tabs.addTab("Formulario", panelForm);
        
        // ==== PANEL LISTADO ====
        JPanel panelListado = new JPanel(new BorderLayout());
        tabla = new JTable();
        panelListado.add(new JScrollPane(tabla), BorderLayout.CENTER);
        tabs.addTab("Listado de Ventas", panelListado);
        
        // ==== AÑADIR TABS A LA VENTANA ====
        add(tabs);
        
        // ==== EVENTOS ====
        btnAgregar.addActionListener(e->agregar());
        btnActualizar.addActionListener(e->actualizar());
        btnEliminar.addActionListener(e->eliminar());
        btnListar.addActionListener(e->listar());
    }
    
    // ==== CONEXIÓN ====
    public Connection getConexion(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_ventas_s_g_v","root","");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error conexión: "+e.getMessage());
            return null;
        }
    }
    
    // ==== FUNCIONALIDADES CRUD ====
    void agregar(){
        String sql="INSERT INTO ventas(id_producto,fecha_venta,id_vendedor,id_cliente,cantidad_producto,descuento_aplicado,monto_subtotal,impuesto_aplicado,monto_total,forma_pago,estado_venta,observaciones) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection c=getConexion(); PreparedStatement ps=c.prepareStatement(sql)) {
            ps.setInt(1,Integer.parseInt(txtIdProducto.getText()));
            ps.setString(2,txtFecha.getText());
            ps.setInt(3,Integer.parseInt(txtIdVendedor.getText()));
            ps.setInt(4,Integer.parseInt(txtIdCliente.getText()));
            ps.setInt(5,Integer.parseInt(txtCantidad.getText()));
            ps.setDouble(6,Double.parseDouble(txtDescuento.getText()));
            ps.setDouble(7,Double.parseDouble(txtSubtotal.getText()));
            ps.setDouble(8,Double.parseDouble(txtImpuesto.getText()));
            ps.setDouble(9,Double.parseDouble(txtTotal.getText()));
            ps.setString(10,txtFormaPago.getText());
            ps.setString(11,txtEstado.getText());
            ps.setString(12,txtObs.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Venta agregada");
        } catch (Exception e) { JOptionPane.showMessageDialog(null,"Error agregar: "+e.getMessage()); }
    }
    
    void actualizar(){
        String sql="UPDATE ventas SET id_producto=?,fecha_venta=?,id_vendedor=?,id_cliente=?,cantidad_producto=?,descuento_aplicado=?,monto_subtotal=?,impuesto_aplicado=?,monto_total=?,forma_pago=?,estado_venta=?,observaciones=? WHERE id_venta=?";
        try (Connection c=getConexion(); PreparedStatement ps=c.prepareStatement(sql)) {
            ps.setInt(1,Integer.parseInt(txtIdProducto.getText()));
            ps.setString(2,txtFecha.getText());
            ps.setInt(3,Integer.parseInt(txtIdVendedor.getText()));
            ps.setInt(4,Integer.parseInt(txtIdCliente.getText()));
            ps.setInt(5,Integer.parseInt(txtCantidad.getText()));
            ps.setDouble(6,Double.parseDouble(txtDescuento.getText()));
            ps.setDouble(7,Double.parseDouble(txtSubtotal.getText()));
            ps.setDouble(8,Double.parseDouble(txtImpuesto.getText()));
            ps.setDouble(9,Double.parseDouble(txtTotal.getText()));
            ps.setString(10,txtFormaPago.getText());
            ps.setString(11,txtEstado.getText());
            ps.setString(12,txtObs.getText());
            ps.setInt(13,Integer.parseInt(txtIdVenta.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Venta actualizada");
        } catch (Exception e) { JOptionPane.showMessageDialog(null,"Error actualizar: "+e.getMessage()); }
    }
    
    void eliminar(){
        String sql="DELETE FROM ventas WHERE id_venta=?";
        try (Connection c=getConexion(); PreparedStatement ps=c.prepareStatement(sql)) {
            ps.setInt(1,Integer.parseInt(txtIdVenta.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null,"Venta eliminada");
        } catch (Exception e) { JOptionPane.showMessageDialog(null,"Error eliminar: "+e.getMessage()); }
    }
    
    void listar(){
        String[] titulos={"ID Venta","ID Prod","Fecha","ID Vend","ID Cliente","Cant","Desc","Subt","Imp","Total","Pago","Estado","Obs"};
        modelo=new DefaultTableModel(null,titulos);
        String sql="SELECT * FROM ventas";
        try (Connection c=getConexion(); Statement st=c.createStatement(); ResultSet rs=st.executeQuery(sql)) {
            while(rs.next()){
                Object[] fila=new Object[13];
                fila[0]=rs.getInt("id_venta");
                fila[1]=rs.getInt("id_producto");
                fila[2]=rs.getString("fecha_venta");
                fila[3]=rs.getInt("id_vendedor");
                fila[4]=rs.getInt("id_cliente");
                fila[5]=rs.getInt("cantidad_producto");
                fila[6]=rs.getDouble("descuento_aplicado");
                fila[7]=rs.getDouble("monto_subtotal");
                fila[8]=rs.getDouble("impuesto_aplicado");
                fila[9]=rs.getDouble("monto_total");
                fila[10]=rs.getString("forma_pago");
                fila[11]=rs.getString("estado_venta");
                fila[12]=rs.getString("observaciones");
                modelo.addRow(fila);
            }
            tabla.setModel(modelo);
        } catch (Exception e) { JOptionPane.showMessageDialog(null,"Error listar: "+e.getMessage()); }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentasForm().setVisible(true));
    }
}



