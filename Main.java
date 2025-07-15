package com.snayber.login;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.sql.Connection;  // Agregamos esta importación
import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    private static void mostrarError(String mensaje, Exception e) {
        e.printStackTrace();
        SwingUtilities.invokeLater(() -> 
            JOptionPane.showMessageDialog(null, 
                mensaje + ": " + e.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE));
    }

    public static void main(String[] args) {
        try {
    UIManager.setLookAndFeel(new FlatLightLaf());
} catch (Exception e) {
    e.printStackTrace();
}
        try {
            // Obtener conexión a la base de datos
            Connection conn = ConexionBaseDatos.getInstancia().getConexion();

            SwingUtilities.invokeLater(() -> {
                try {
                    // Crear la vista de login
                    LoginView loginVista = new LoginView();
                    
                    // Crear el controlador de login pasando la conexión
                    LoginController loginController = new LoginController(loginVista, conn);
                    
                    // Configurar el callback de login exitoso
                    loginController.setOnLoginSuccessful(() -> {
                        try {
                            loginVista.setVisible(false);
                            loginVista.dispose();
                            
                            SwingUtilities.invokeLater(() -> {
                                try {
                                    InventarioView inventarioVista = new InventarioView(conn);
                                    InventarioController inventarioController = 
                                        new InventarioController(inventarioVista, conn);
                                    inventarioVista.setVisible(true);
                                } catch (Exception e) {
                                    mostrarError("Error al abrir el inventario", e);
                                }
                            });
                        } catch (Exception e) {
                            mostrarError("Error en la transición", e);
                        }
                    });
                    
                    loginVista.setVisible(true);
                    
                } catch (Exception e) {
                    mostrarError("Error al iniciar la aplicación", e);
                }
            });
        } catch (Exception e) {
            mostrarError("Error de conexión a la base de datos", e);
        }
    }
private void agregarProducto() {
    // TODO: Implementar
}

private void editarProducto() {
    // TODO: Implementar
}

private void eliminarProducto() {
    // TODO: Implementar
}

private void cargarProductos() {
    // TODO: Implementar
}
}