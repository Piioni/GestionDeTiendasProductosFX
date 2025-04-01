package model;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product implements Comparable<Product> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String codigo;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "precio", nullable = false)
    private double precio;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "categoria", nullable = false)
    private String categoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tienda_id", nullable = false)
    private Tienda tienda;

    public Product(String codigo, String nombre, double precio, int cantidad, String descripcion, String categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public Product() {

    }

    @Override
    public int compareTo(Product o) {
        return this.categoria.compareTo(o.categoria) == 0 ? this.codigo.compareTo(o.codigo) : this.categoria.compareTo(o.categoria);
    }

    public String toString() {
        return  "Código: " + codigo + "\n" +
                "Nombre: " + nombre + "\n" +
                "Precio: " + precio + "\n" +
                "Cantidad: " + cantidad + "\n" +
                "Descripción: " + descripcion + "\n" +
                "Categoría: " + categoria + "\n";

    }

    // Getters & Setters

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
