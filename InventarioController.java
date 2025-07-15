package com.snayber.login;

import javax.swing.*;
import java.sql.*;
import java.math.BigDecimal;

public class InventarioController {
    private final InventarioView vista;
    private final Connection conexion;

    public InventarioController(InventarioView vista, Connection conexion) {
        this.vista = vista;
        this.conexion = conexion;
        inicializarListeners();
        cargarDatos();
    }

    private void inicializarListeners() {
        vista.getBtnAgregar().addActionListener(e -> mostrarDialogoAgregar());
        vista.getBtnEditar().addActionListener(e -> mostrarDialogoEditar());
        vista.getBtnEliminar().addActionListener(e -> eliminarProducto());
        vista.getBtnActualizar().addActionListener(e -> cargarDatos());
    }

    private void cargarDatos() {
        try {
            PreparedStatement stmt = conexion.prepareStatement(
                "SELECT * FROM productos ORDER BY id"
            );
            ResultSet rs = stmt.executeQuery();
            vista.getModeloTabla().setRowCount(0);

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("tipo"),
                    rs.getInt("cantidad"),
                        rs.getDouble("precio_unitario"),
                        rs.getDouble("precio_total")

                };
                vista.getModeloTabla().addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista, 
                "Error al cargar datos: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDialogoAgregar() {
        JTextField nombreField = new JTextField();
        JTextField tipoField = new JTextField();
        JTextField cantidadField = new JTextField();
        JTextField precioField = new JTextField();

        Object[] mensaje = {
            "Nombre:", nombreField,
            "Tipo:", tipoField,
            "Cantidad:", cantidadField,
            "Precio:", precioField
        };

        int opcion = JOptionPane.showConfirmDialog(vista, mensaje, "Nuevo Producto", 
            JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                Producto producto = new Producto(
                    nombreField.getText(),
                    tipoField.getText(),
                    Integer.parseInt(cantidadField.getText()),
                    Double.parseDouble(precioField.getText())
                );
                insertarProducto(producto);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista,
                    "Por favor, ingrese valores numéricos válidos para cantidad y precio",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(vista,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostrarDialogoEditar() {
        int fila = vista.getTablaProductos().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista,
                "Por favor, seleccione un producto para editar",
                "Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        JTextField nombreField = new JTextField(vista.getModeloTabla().getValueAt(fila, 1).toString());
        JTextField tipoField = new JTextField(vista.getModeloTabla().getValueAt(fila, 2).toString());
        JTextField cantidadField = new JTextField(vista.getModeloTabla().getValueAt(fila, 3).toString());
        JTextField precioField = new JTextField(vista.getModeloTabla().getValueAt(fila, 4).toString());

        Object[] mensaje = {
            "Nombre:", nombreField,
            "Tipo:", tipoField,
            "Cantidad:", cantidadField,
            "Precio:", precioField
        };

        int opcion = JOptionPane.showConfirmDialog(vista, mensaje, "Editar Producto", 
            JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            try {
                int id = (Integer) vista.getModeloTabla().getValueAt(fila, 0);
                Producto producto = new Producto(
                    id,
                    nombreField.getText(),
                    tipoField.getText(),
                    Integer.parseInt(cantidadField.getText()),
                    Double.parseDouble(precioField.getText())
                );
                actualizarProducto(producto);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista,
                    "Por favor, ingrese valores numéricos válidos para cantidad y precio",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(vista,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarProducto() {
        int fila = vista.getTablaProductos().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista,
                "Por favor, seleccione un producto para eliminar",
                "Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) vista.getModeloTabla().getValueAt(fila, 0);
        String nombre = (String) vista.getModeloTabla().getValueAt(fila, 1);

        int confirmacion = JOptionPane.showConfirmDialog(vista,
            "¿Está seguro que desea eliminar el producto '" + nombre + "'?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                PreparedStatement stmt = conexion.prepareStatement(
                    "DELETE FROM productos WHERE id = ?"
                );
                stmt.setInt(1, id);
                stmt.executeUpdate();
                cargarDatos();
                JOptionPane.showMessageDialog(vista,
                    "Producto eliminado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(vista,
                    "Error al eliminar el producto: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void insertarProducto(Producto producto) {
        try {
            PreparedStatement stmt = conexion.prepareStatement(
                    "INSERT INTO productos (nombre, tipo, cantidad, precio_unitario, precio_total) " +
                            "VALUES (?, ?, ?, ?, ?)"

            );
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getTipo());
            stmt.setInt(3, producto.getCantidad());
            stmt.setBigDecimal(4, producto.getPrecio());
            // Calcular precio total
            stmt.setBigDecimal(5, producto.getValorTotal());

            stmt.executeUpdate();
            cargarDatos();
            JOptionPane.showMessageDialog(vista,
                "Producto agregado exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista,
                "Error al guardar el producto: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    private void actualizarPrecioTotal(int id, BigDecimal precioUnitario, int cantidad) {
        try {
            PreparedStatement stmt = conexion.prepareStatement(
                    "UPDATE productos SET precio_total = precio_unitario * cantidad " +
                            "WHERE id = ?"
            );
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            vista.mostrarError("Error al actualizar el precio total: " + e.getMessage());
        }
    }


    private void actualizarProducto(Producto producto) {
        try {
            PreparedStatement stmt = conexion.prepareStatement(
                "UPDATE productos SET nombre=?, tipo=?, cantidad=?, precio=? WHERE id=?"
            );
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getTipo());
            stmt.setInt(3, producto.getCantidad());
            stmt.setBigDecimal(4, producto.getPrecio());
            stmt.setInt(5, producto.getId());
            stmt.executeUpdate();
            cargarDatos();
            JOptionPane.showMessageDialog(vista,
                "Producto actualizado exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vista,
                "Error al actualizar el producto: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}