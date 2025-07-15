package com.snayber.login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Clase que maneja la interfaz gráfica del login
 */
public class LoginView extends JFrame {
    // Componentes de la interfaz
    private final JTextField txtUsuario;         // Campo para el usuario
    private final JPasswordField txtContrasena;  // Campo para la contraseña
    private final JButton btnLogin;              // Botón de inicio de sesión

    public LoginView() {
        // Configuración básica de la ventana
        setTitle("MenchApp - Inicio de Sesión");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);  // Centrar en pantalla
        setLayout(new BorderLayout());
        
        // Aplicar estilos visuales a la ventana
        EstilosUI.configurarVentana(this);

        // Crear panel principal con padding
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(EstilosUI.COLOR_FONDO);
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Agregar título
        add(EstilosUI.crearPanelTitulo("Iniciar Sesión"), BorderLayout.NORTH);

        // Configurar el layout para los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Crear y agregar campo de usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(EstilosUI.FUENTE_NORMAL);
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(lblUsuario, gbc);
        
        txtUsuario = new JTextField();
        txtUsuario.setFont(EstilosUI.FUENTE_NORMAL);
        txtUsuario.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 0; gbc.gridy = 1;
        mainPanel.add(txtUsuario, gbc);

        // Crear y agregar campo de contraseña
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(EstilosUI.FUENTE_NORMAL);
        gbc.gridx = 0; gbc.gridy = 2;
        mainPanel.add(lblContrasena, gbc);
        
        txtContrasena = new JPasswordField();
        txtContrasena.setFont(EstilosUI.FUENTE_NORMAL);
        txtContrasena.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 0; gbc.gridy = 3;
        mainPanel.add(txtContrasena, gbc);

        // Crear y agregar botón de login
        btnLogin = new JButton("Iniciar Sesión");
        EstilosUI.aplicarEstiloBoton(btnLogin, EstilosUI.COLOR_PRIMARIO);
        btnLogin.setPreferredSize(new Dimension(200, 40));
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.insets = new Insets(20, 10, 10, 10);
        mainPanel.add(btnLogin, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Configurar eventos de Enter en los campos
        txtUsuario.addActionListener(e -> btnLogin.doClick());
        txtContrasena.addActionListener(e -> btnLogin.doClick());
    }

    // Métodos para obtener datos y controlar la interfaz
    
    // Obtener el texto del campo usuario
    public String getUsuario() {
        return txtUsuario.getText();
    }

    // Obtener el texto del campo contraseña
    public String getContrasena() {
        return new String(txtContrasena.getPassword());
    }

    // Establecer el listener para el botón de login
    public void setLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }

    // Mostrar mensaje informativo
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Mostrar mensaje de error
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Limpiar los campos de texto
    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContrasena.setText("");
        txtUsuario.requestFocusInWindow();
    }

    // Poner el foco en el campo de usuario
    public void focusUsuario() {
        txtUsuario.requestFocusInWindow();
    }
}