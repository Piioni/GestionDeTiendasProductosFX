package Classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Tienda implements Iterable<Producto> {
    private String nombre = "Tienda de Productos Paquito";
    private String direccion = "Calle Falsa 123";
    private String descripcion = "Tienda de productos varios!";
    private final List<Producto> listaProductos;

    public Tienda(String nombre, String direccion, String descripcion) {
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.nombre = nombre;
        listaProductos = new ArrayList<>();
    }

    public Tienda() {
        listaProductos = new ArrayList<>();
    }

    @Override
    public Iterator<Producto> iterator() {
        return listaProductos.iterator();
    }

    public void addProduct(Producto p) {
        listaProductos.add(p);
    }

    public void eliminarProducto(String codigo) {
        Producto p = buscarProducto(codigo);
        if (p != null) {
            listaProductos.remove(p);
        } else {
            System.out.println("Producto no encontrado.");
        }

    }

    public Producto buscarProducto(String codigo) {
        for (Producto p : listaProductos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    public void clear() {
        listaProductos.clear();
    }

    public int size() {
        return listaProductos.size();
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
