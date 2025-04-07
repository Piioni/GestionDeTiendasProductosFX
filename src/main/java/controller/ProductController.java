package controller;

import model.Product;
import model.Store;
import service.ProductService;
import service.StoreService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductController {
    private final Store store;
    private final ProductService productService;
    private final StoreService storeService;

    public ProductController(Store store) {
        this.store = store;
        this.productService = new ProductService();
        this.storeService = new StoreService();
    }

    public void agregarProducto(String codigo, String nombre, String cantidadStr, String precioStr,
                                String descripcion, String categoria) {
        ComfirmData(codigo, nombre, cantidadStr, precioStr, descripcion, categoria, false);

        int cantidad;
        double precio;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            precio = Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cantidad y precio deben ser valores numéricos.");
        }

        Product p = new Product(codigo, nombre, precio, cantidad, descripcion, categoria);
        store.addProduct(p);
        storeService.update(store);
    }


    public void modificarProducto(String codigo, String nombre, String cantidadStr,
                                  String precioStr, String descripcion, String categoria) {
        ComfirmData(codigo, nombre, cantidadStr, precioStr, descripcion, categoria, true);

        Product p = productService.getProductById(codigo, store);
        if (p != null) {
            int cantidad;
            double precio;
            try {
                cantidad = Integer.parseInt(cantidadStr);
                precio = Double.parseDouble(precioStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Cantidad y precio deben ser valores numéricos.");
            }

            p.setNombre(nombre);
            p.setPrecio(precio);
            p.setCantidad(cantidad);
            p.setDescripcion(descripcion);
            p.setCategoria(categoria);

            storeService.update(store);
        } else {
            throw new IllegalArgumentException("El producto con el código " + codigo + " no existe.");
        }
    }

    public List<Product> buscarProducto(String codigo) {
        return productService.getAllProducts(store).stream()
                .filter(p -> p.getCodigo().toLowerCase().contains(codigo.toLowerCase().trim()))
                .collect(Collectors.toList());

    }

    public List<Product> buscarPorCategoria(String categoria) {
        return productService.getProductsByCategory(categoria, store);
    }

    public void eliminarProducto(String codigo) {
        Product p = productService.getProductById(codigo, store);
        if (p != null) {
            store.eliminarProducto(codigo);
            storeService.update(store);
            productService.removeProduct(p, store);
        } else {

            throw new IllegalArgumentException("El producto con el código " + codigo + " no existe.");
        }
    }

    private void ComfirmData(String codigo, String nombre, String cantidadStr, String precioStr,
                             String descripcion, String categoria, boolean isUpdate) {
        if (codigo.isEmpty() || nombre.isEmpty() || cantidadStr.isEmpty() || precioStr.isEmpty()
                || descripcion.isEmpty() || categoria == null) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }

        // Solo valida si el producto ya existe en caso de agregar, no de modificar
        if (!isUpdate && productService.getProductById(codigo, store) != null) {
            throw new IllegalArgumentException("Este producto ya existe en esta tienda.");
        }

        if (!codigo.matches("[A-Z]{2,3}\\d{1,3}")) {
            throw new IllegalArgumentException("El código debe contener 2 o 3 letras mayúsculas seguidas de 1 número.");
        } else if (codigo.length() < 3 || codigo.length() > 6) {
            throw new IllegalArgumentException("El código debe tener entre 3 y 6 caracteres.");
        }
    }

    public List<Product> mostrarProductos() {
        List<Product> productos = productService.getAllProducts(store);
        Collections.sort(productos);

        return productos;
    }
}

