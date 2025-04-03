package model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "tienda")
public class Store implements Iterable<Product> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<Product> listaProductos = new ArrayList<>();

    public Store(String nombre, String direccion, String descripcion) {
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.nombre = nombre;

    }

    public Store() {}


    public void addProduct(Product p) {
        listaProductos.add(p);
        p.setTienda(this);
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

    @Override
    public Iterator<Product> iterator() {
        return listaProductos.iterator();
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
