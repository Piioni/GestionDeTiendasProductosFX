package model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "tienda")
public class Tienda implements Iterable<Product> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private final List<Product> listaProductos = new ArrayList<>();

    public Tienda(String nombre, String direccion, String descripcion) {
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.nombre = nombre;

    }

    public Tienda() {}

    @Override
    public Iterator<Product> iterator() {
        return listaProductos.iterator();
    }

    public void addProduct(Product p) {
        listaProductos.add(p);
    }

    public void eliminarProducto(String codigo) {
        Product p = buscarProducto(codigo);
        if (p != null) {
            listaProductos.remove(p);
        } else {
            System.out.println("Producto no encontrado.");
        }

    }

    public Product buscarProducto(String codigo) {
        for (Product p : listaProductos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    public List<Product> getProductos() {
        return listaProductos;
    }

    public void clear() {
        listaProductos.clear();
    }

    public int size() {
        return listaProductos.size();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
