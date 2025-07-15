package com.snayber.login;

import javax.swing.*;
import java.awt.*;

public class ProductoDialog {
    private final JDialog dialog;
    private final JTextField nombreField;
    private final JTextField tipoField;
    private final JTextField cantidadField;
    private final JTextField precioField;
    private boolean aceptado = false;

    public ProductoDialog(JFrame parent, String titulo) {
        this(parent, titulo, "", "", "", "");
    }

    public ProductoDialog(JFrame parent, String titulo, 
            String nombre, String tipo, String cantidad, String precio) {
        
        dialog = new JDialog(parent, titulo, true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Crear campos
        nombreField = new JTextField(nombre, 20);
        tipoField = new JTextField(tipo, 20);
        cantidadField = new JTextField(cantidad, 20);
        precioField = new JTextField(precio, 20);

        // Agregar campos al diálogo
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        dialog.add(nombreField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        dialog.add(tipoField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        dialog.add(cantidadField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Precio:"), gbc);
        gbc.gridx = 1;
        dialog.add(precioField, gbc);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        JButton aceptarBtn = new JButton("Aceptar");
        JButton cancelarBtn = new JButton("Cancelar");

        aceptarBtn.addActionListener(e -> {
            aceptado = true;
            dialog.dispose();
        });
        cancelarBtn.addActionListener(e -> dialog.dispose());

        buttonPanel.add(aceptarBtn);
        buttonPanel.add(cancelarBtn);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);

        dialog.pack();
        dialog.setLocationRelativeTo(parent);
    }

    public boolean mostrar() {
        dialog.setVisible(true);
        return aceptado;
    }

    public String getNombre() {
        return nombreField.getText().trim();
    }

    public String getTipo() {
        return tipoField.getText().trim();
    }

    public int getCantidad() {
        return Integer.parseInt(cantidadField.getText().trim());
    }

    public double getPrecio() {
        return Double.parseDouble(precioField.getText().trim());
    }
}