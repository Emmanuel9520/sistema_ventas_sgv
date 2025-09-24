/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
// Paquete donde se encuentra la clase

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// Importaciones necesarias para interfaz gráfica (Swing, AWT) y manejo de eventos

// Clase principal que representa el formulario de Login
public class LoginForm extends JFrame {

    // ---------------- COMPONENTES DE LA INTERFAZ ----------------
    private JTextField txtUsuario;        // Campo para ingresar el usuario
    private JPasswordField txtContrasena; // Campo para la contraseña enmascarada
    private JButton btnLogin;             // Botón para iniciar sesión
    private JLabel lblRegistrar, lblOlvido; // Labels con links de acción

    // ---------------- CONSTRUCTOR ----------------
    public LoginForm() {
        // Configuración básica de la ventana
        setTitle("Login - Sistema de Ventas S.G.V."); // Título de la ventana
        setSize(400, 250);                            // Dimensiones
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Finaliza el programa al cerrar
        setLocationRelativeTo(null); // Centra la ventana en pantalla

        // ---------------- PANEL PRINCIPAL ----------------
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Distribución flexible con GridBag
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Márgenes
        GridBagConstraints gbc = new GridBagConstraints(); // Restricciones de colocación
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre elementos
        gbc.fill = GridBagConstraints.HORIZONTAL; // Ocupar ancho disponible

        // ---------------- CAMPO USUARIO ----------------
        gbc.gridx = 0; // Columna 0
        gbc.gridy = 0; // Fila 0
        panel.add(new JLabel("Usuario:"), gbc); // Etiqueta Usuario

        gbc.gridx = 1; // Columna 1
        txtUsuario = new JTextField(15); // Campo de texto para usuario
        panel.add(txtUsuario, gbc);

        // ---------------- CAMPO CONTRASEÑA ----------------
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Contraseña:"), gbc); // Etiqueta Contraseña

        gbc.gridx = 1;
        txtContrasena = new JPasswordField(15); // Campo de texto seguro
        panel.add(txtContrasena, gbc);

        // ---------------- BOTÓN LOGIN ----------------
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Botón ocupa 2 columnas
        gbc.anchor = GridBagConstraints.CENTER; // Centrado
        btnLogin = new JButton("Iniciar Sesión"); // Botón principal
        panel.add(btnLogin, gbc);

        // ---------------- LINKS DE ACCIÓN ----------------
        JPanel panelLinks = new JPanel(); // Panel para contener links
        lblRegistrar = new JLabel("<HTML><U>Registrarse</U></HTML>"); 
        // Label con texto subrayado simulando un link
        lblRegistrar.setForeground(Color.BLUE); // Azul estilo hipervínculo

        lblOlvido = new JLabel("<HTML><U>¿Olvidó su contraseña?</U></HTML>");
        lblOlvido.setForeground(Color.BLUE);

        // Se agregan los links al panel
        panelLinks.add(lblRegistrar);
        panelLinks.add(new JLabel("  |  ")); // Separador visual
        panelLinks.add(lblOlvido);

        // Posición de los links en el GridBag
        gbc.gridy = 3;
        panel.add(panelLinks, gbc);

        // Se agrega el panel principal al JFrame
        add(panel);

        // ---------------- EVENTOS ----------------

        // Acción del botón Login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText(); 
                String contrasena = new String(txtContrasena.getPassword());

                // Validación sencilla (usuario: admin, contraseña: 1234)
                if (usuario.equals("admin") && contrasena.equals("1234")) {
                    JOptionPane.showMessageDialog(null, "Bienvenido " + usuario);
                    dispose(); // Cierra la ventana Login
                    // Aquí podría abrirse el menú principal del sistema
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                }
            }
        });

        // Acción al hacer clic en "Registrarse"
        lblRegistrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(null, "Funcionalidad de registro aún no implementada.");
            }
        });

        // Acción al hacer clic en "¿Olvidó su contraseña?"
        lblOlvido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(null, "Funcionalidad de recuperación aún no implementada.");
            }
        });
    }

    // ---------------- MÉTODO MAIN ----------------
    public static void main(String[] args) {
        // Ejecuta la aplicación en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}



