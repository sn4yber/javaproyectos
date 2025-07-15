package com.snayber.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que maneja la conexión a la base de datos usando el patrón Singleton
 */
public class ConexionBaseDatos {
    // Instancia única de la clase (patrón Singleton)
    private static volatile ConexionBaseDatos instancia;
    
    // Objeto que mantiene la conexión con la base de datos
    private Connection conexion;
    
    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "456789";

    // Constructor privado - solo se puede crear una instancia dentro de la clase
    private ConexionBaseDatos() {
        try {
            // Crear la conexión usando los datos definidos
            this.conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión establecida con la base de datos.");
        } catch (SQLException e) {
            // Si hay error, mostrar mensaje y lanzar excepción
            System.err.println("❌ Error al conectar a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al inicializar la conexión", e);
        }
    }

    /**
     * Método para obtener la única instancia de la conexión
     */
    public static ConexionBaseDatos getInstancia() {
        // Verificar si ya existe una instancia
        if (instancia == null) {
            // Bloquear para que solo un hilo pueda crear la instancia
            synchronized (ConexionBaseDatos.class) {
                // Verificar nuevamente dentro del bloque sincronizado
                if (instancia == null) {
                    instancia = new ConexionBaseDatos();
                }
            }
        }
        return instancia;
    }

    // Obtener el objeto Connection
    public Connection getConexion() {
        return conexion;
    }

    // Método para cerrar la conexión de forma segura
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}