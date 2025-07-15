package com.snayber.login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

/**
 * Clase que define los estilos visuales de la aplicación
 */
public class EstilosUI {
    // Definición de colores principales
    public static final Color COLOR_PRIMARIO = new Color(25, 210, 93, 138);
    public static final Color COLOR_SECUNDARIO = new Color(66, 165, 245);
    public static final Color COLOR_FONDO = new Color(245, 245, 245);
    public static final Color COLOR_TEXTO = new Color(33, 33, 33);
    public static final Color COLOR_EXITO = new Color(76, 175, 80);
    public static final Color COLOR_ERROR = new Color(244, 67, 54);

    // Definición de fuentes
    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FUENTE_PEQUENA = new Font("Segoe UI", Font.PLAIN, 12);

    // Aplicar estilo a botones
    public static void aplicarEstiloBoton(JButton boton, Color colorFondo) {
        boton.setFont(FUENTE_NORMAL);
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setPreferredSize(new Dimension(120, 35));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Aplicar estilo a tablas
    public static void aplicarEstiloTabla(JTable tabla) {
        tabla.setFont(FUENTE_NORMAL);
        tabla.setRowHeight(30);
        tabla.setShowGrid(true);
        tabla.setGridColor(new Color(230, 230, 230));
        tabla.setSelectionBackground(COLOR_SECUNDARIO);
        tabla.setSelectionForeground(Color.WHITE);
        
        // Estilo del encabezado
        tabla.getTableHeader().setFont(FUENTE_SUBTITULO);
        tabla.getTableHeader().setBackground(COLOR_PRIMARIO);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 35));
    }

    // Crear panel para botones
    public static JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(COLOR_FONDO);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        return panel;
    }

    // Crear panel de título
    public static JPanel crearPanelTitulo(String texto) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(COLOR_FONDO);
        
        JLabel titulo = new JLabel(texto);
        titulo.setFont(FUENTE_TITULO);
        titulo.setForeground(COLOR_TEXTO);
        panel.add(titulo);
        
        panel.setBorder(new EmptyBorder(20, 0, 20, 0));
        return panel;
    }

    // Configurar estilo de ventana
    public static void configurarVentana(JFrame ventana) {
        ventana.getContentPane().setBackground(COLOR_FONDO);
        ventana.getRootPane().setBorder(BorderFactory.createLineBorder(COLOR_PRIMARIO, 2));
    }

    // Crear botón con icono
    public static JButton crearBoton(String texto, String nombreIcono) {
        JButton boton = new JButton(texto);
        try {
            // Cargar icono desde recursos
            URL resourceUrl = EstilosUI.class.getClassLoader().getResource("icons/" + nombreIcono);
            if (resourceUrl != null) {
                ImageIcon icono = new ImageIcon(resourceUrl);
                Image img = icono.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                boton.setIcon(new ImageIcon(img));
            } else {
                System.err.println("No se encontró el recurso: icons/" + nombreIcono);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el icono " + nombreIcono + ": " + e.getMessage());
        }
        aplicarEstiloBoton(boton, COLOR_PRIMARIO);
        return boton;
    }

    // Aplicar estilo a paneles con scroll
    public static void aplicarEstiloScrollPane(JScrollPane scrollPane) {
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_PRIMARIO));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
}