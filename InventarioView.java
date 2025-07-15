package com.snayber.login;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.table.DefaultTableCellRenderer;



public class InventarioView extends JFrame {
    private final JTable tablaProductos;
    private final DefaultTableModel modeloTabla;
    private final JButton btnAgregar, btnEditar, btnEliminar, btnActualizar;

    public InventarioView(Connection conexion) {
        // Configuración básica de la ventana
        setTitle("Inventario de Bolsas - MenchApp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Aplicar estilos generales
        EstilosUI.configurarVentana(this);

        // Panel de título
        add(EstilosUI.crearPanelTitulo("Inventario de Bolsas"), BorderLayout.NORTH);

        // Configuración de la tabla
        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: return Integer.class;    // ID
                    case 3: return Integer.class;    // Cantidad
                    case 4: // Precio Unitario
                    case 5: // Precio Total

                    default: return String.class;
                }
            }
        };

        modeloTabla.setColumnIdentifiers(new String[]{
                "ID", "Nombre", "Tipo", "Cantidad",
                "Precio Unitario", "Precio Total"
        });

        tablaProductos = new JTable(modeloTabla);
        EstilosUI.aplicarEstiloTabla(tablaProductos);

        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "MX"));
        DefaultTableCellRenderer rendererMoneda = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus,
                    int row, int column) {

                if (value != null) {
                    value = formatoMoneda.format(value);
                }
                return super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
            }
        };
        rendererMoneda.setHorizontalAlignment(SwingConstants.RIGHT);

        tablaProductos.getColumnModel().getColumn(4).setCellRenderer(rendererMoneda); // Precio Unitario
        tablaProductos.getColumnModel().getColumn(5).setCellRenderer(rendererMoneda); // Precio Total

        // Ajustar el ancho de las columnas
        tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(200); // Nombre
        tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(150); // Tipo
        tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(100); // Cantidad
        tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(150); // Precio Unitario
        tablaProductos.getColumnModel().getColumn(5).setPreferredWidth(150); // Precio Total

        // Scroll pane con estilos
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        EstilosUI.aplicarEstiloScrollPane(scrollPane);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = EstilosUI.crearPanelBotones();

        btnAgregar = EstilosUI.crearBoton("Agregar", "add.png");
        btnEditar = EstilosUI.crearBoton("Editar", "edit.png");
        btnEliminar = EstilosUI.crearBoton("Eliminar", "delete.png");
        btnActualizar = EstilosUI.crearBoton("Actualizar", "refresh.png");

        // Aplicar colores específicos a algunos botones
        EstilosUI.aplicarEstiloBoton(btnEliminar, EstilosUI.COLOR_ERROR);
        EstilosUI.aplicarEstiloBoton(btnActualizar, EstilosUI.COLOR_SECUNDARIO);

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    // Getters para los botones
    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    // Getters para la tabla y el modelo
    public JTable getTablaProductos() {
        return tablaProductos;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    // Los getters se mantienen igual...

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this,
            mensaje,
            "Información",
            JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this,
            mensaje,
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}