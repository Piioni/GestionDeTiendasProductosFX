package Classes;

public class Producto implements Comparable<Producto> {
    private final String codigo;
    private String nombre;
    private double precio;
    private int cantidad;
    private String descripcion;
    private String categoria;

    public Producto(String codigo, String nombre, double precio, int cantidad, String descripcion, String categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    @Override
    public int compareTo(Producto o) {
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

    // Getters
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
