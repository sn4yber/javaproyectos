package com.snayber.login;

import java.util.Objects;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Clase que representa un producto en el inventario
 */
public class Producto {
    // Atributos del producto
    private final Integer id;           // ID único del producto
    private String nombre;              // Nombre del producto
    private String tipo;                // Tipo o categoría
    private int cantidad;               // Cantidad en inventario
    private BigDecimal precio;          // Precio unitario
    
    // Constantes para validaciones
    private static final int NOMBRE_MAX_LENGTH = 100;   // Longitud máxima del nombre
    private static final int TIPO_MAX_LENGTH = 50;      // Longitud máxima del tipo
    private static final int CANTIDAD_MIN = 0;          // Cantidad mínima
    private static final int CANTIDAD_MAX = 999999;     // Cantidad máxima
    private static final BigDecimal PRECIO_MIN = BigDecimal.ZERO;  // Precio mínimo
    private static final BigDecimal PRECIO_MAX = new BigDecimal("999999.99"); // Precio máximo

    // Constructor para nuevos productos
    public Producto(String nombre, String tipo, int cantidad, double precio) {
        this(null, nombre, tipo, cantidad, precio);
    }

    // Constructor para productos existentes
    public Producto(Integer id, String nombre, String tipo, int cantidad, double precio) {
        this.id = id;
        setNombre(nombre);
        setTipo(tipo);
        setCantidad(cantidad);
        setPrecio(precio);
    }

    // Métodos getter y setter con validaciones

    // Obtener ID
    public Integer getId() { 
        return id; 
    }

    // Obtener nombre
    public String getNombre() { 
        return nombre; 
    }

    // Obtener tipo
    public String getTipo() { 
        return tipo; 
    }

    // Obtener cantidad
    public int getCantidad() { 
        return cantidad; 
    }

    // Obtener precio
    public BigDecimal getPrecio() { 
        return precio; 
    }

    // Establecer nombre con validación
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (nombre.length() > NOMBRE_MAX_LENGTH) {
            throw new IllegalArgumentException("El nombre no puede exceder " + NOMBRE_MAX_LENGTH + " caracteres");
        }
        this.nombre = nombre.trim();
    }

    // Establecer tipo con validación
    public void setTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo no puede estar vacío");
        }
        if (tipo.length() > TIPO_MAX_LENGTH) {
            throw new IllegalArgumentException("El tipo no puede exceder " + TIPO_MAX_LENGTH + " caracteres");
        }
        this.tipo = tipo.trim();
    }

    // Establecer cantidad con validación
    public void setCantidad(int cantidad) {
        if (cantidad < CANTIDAD_MIN || cantidad > CANTIDAD_MAX) {
            throw new IllegalArgumentException("La cantidad debe estar entre " + CANTIDAD_MIN + " y " + CANTIDAD_MAX);
        }
        this.cantidad = cantidad;
    }

    // Establecer precio con validación
    public void setPrecio(double precio) {
        BigDecimal precioDecimal = new BigDecimal(String.valueOf(precio)).setScale(2, RoundingMode.HALF_UP);
        if (precioDecimal.compareTo(PRECIO_MIN) < 0 || precioDecimal.compareTo(PRECIO_MAX) > 0) {
            throw new IllegalArgumentException("El precio debe estar entre " + PRECIO_MIN + " y " + PRECIO_MAX);
        }
        this.precio = precioDecimal;
    }

    // Calcular el valor total del producto
    public BigDecimal getValorTotal() {
        return precio.multiply(new BigDecimal(cantidad));
    }

    // Métodos de Object sobrescritos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return Objects.equals(id, producto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Producto{id=%d, nombre='%s', tipo='%s', cantidad=%d, precio=%s}",
                id, nombre, tipo, cantidad, precio);
    }

    // Crear una copia del producto
    public Producto copy() {
        return new Producto(this.id, this.nombre, this.tipo, this.cantidad, this.precio.doubleValue());
    }
}