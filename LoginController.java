package com.snayber.login;

import javax.swing.SwingUtilities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    private final LoginView vista;
    private final Connection conexion;
    private Runnable onLoginSuccessful;

    /**
     * Constructor que recibe tanto la vista como la conexión
     */
    public LoginController(LoginView vista, Connection conexion) {
        if (vista == null || conexion == null) {
            throw new IllegalArgumentException("Vista y conexión no pueden ser null");
        }
        
        this.vista = vista;
        this.conexion = conexion;
        
        // Usar lambda para el listener del botón
        vista.setLoginListener(e -> SwingUtilities.invokeLater(this::validarLogin));
    }

    private void validarLogin() {
        String usuario = vista.getUsuario().trim();
        String contrasena = vista.getContrasena();

        // Validaciones básicas
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            vista.mostrarError("Por favor, complete todos los campos");
            return;
        }

        try (PreparedStatement stmt = conexion.prepareStatement(
                "SELECT * FROM usuarios WHERE username = ? AND password = ?")) {
            
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    manejarLoginExitoso(usuario);
                } else {
                    manejarLoginFallido();
                }
            }
        } catch (SQLException ex) {
            manejarErrorSQL(ex);
        }
    }

    private void manejarLoginExitoso(String usuario) {
        vista.mostrarMensaje("¡Bienvenido " + usuario + "!");
        if (onLoginSuccessful != null) {
            SwingUtilities.invokeLater(onLoginSuccessful);
        } else {
            System.err.println("Advertencia: callback de login exitoso no configurado");
        }
    }

    private void manejarLoginFallido() {
        vista.mostrarError("Usuario o contraseña incorrectos");
        vista.limpiarCampos();
        vista.focusUsuario();
    }

    private void manejarErrorSQL(SQLException ex) {
        String mensajeError = "Error al validar el usuario: " + ex.getMessage();
        System.err.println(mensajeError);
        ex.printStackTrace();
        vista.mostrarError(mensajeError);
    }

    /**
     * Establece el callback para ejecutar después de un login exitoso
     */
    public void setOnLoginSuccessful(Runnable callback) {
        this.onLoginSuccessful = callback;
    }
}