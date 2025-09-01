/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnLogin;
    private JLabel lblRegistrar, lblOlvido;

    public LoginForm() {
        setTitle("Login - Sistema de Ventas S.G.V.");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Usuario
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Usuario:"), gbc);

        gbc.gridx = 1;
        txtUsuario = new JTextField(15);
        panel.add(txtUsuario, gbc);

        // Contraseña
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        txtContrasena = new JPasswordField(15);
        panel.add(txtContrasena, gbc);

        // Botón Iniciar Sesión
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnLogin = new JButton("Iniciar Sesión");
        panel.add(btnLogin, gbc);

        // Links de acción
        JPanel panelLinks = new JPanel();
        lblRegistrar = new JLabel("<HTML><U>Registrarse</U></HTML>");
        lblRegistrar.setForeground(Color.BLUE);
        lblOlvido = new JLabel("<HTML><U>¿Olvidó su contraseña?</U></HTML>");
        lblOlvido.setForeground(Color.BLUE);

        panelLinks.add(lblRegistrar);
        panelLinks.add(new JLabel("  |  "));
        panelLinks.add(lblOlvido);

        gbc.gridy = 3;
        panel.add(panelLinks, gbc);

        add(panel);

        // Acción botón login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String contrasena = new String(txtContrasena.getPassword());

                if (usuario.equals("admin") && contrasena.equals("1234")) {
                    JOptionPane.showMessageDialog(null, "Bienvenido " + usuario);
                    dispose(); // Cierra login
                    // Aquí puedes abrir el menú principal del sistema
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                }
            }
        });

        // Acción registrar
        lblRegistrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(null, "Funcionalidad de registro aún no implementada.");
            }
        });

        // Acción recuperar contraseña
        lblOlvido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.showMessageDialog(null, "Funcionalidad de recuperación aún no implementada.");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}


