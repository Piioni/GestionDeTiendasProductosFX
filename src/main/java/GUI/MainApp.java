package GUI;

import Classes.Producto;
import Classes.Tienda;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.nio.file.Path;
import javafx.geometry.Rectangle2D;
public class MainApp extends Application {
    private static Stage primaryStage;
    private static Tienda tienda;
    private static Path PATH ;
    private static Scene sceneMenuPrincipal;
    private static Scene sceneProductos;
    private static Scene sceneConfiguracion;

    @Override
    public void start(Stage primaryStage) {
        MainApp.primaryStage = primaryStage;
        MainApp.tienda = new Tienda();
        primaryStage.setTitle("Main Menu");
        primaryStage.setResizable(false);

        MenuPrincipal menuPrincipal = new MenuPrincipal(primaryStage, tienda);
        sceneMenuPrincipal = menuPrincipal.getScene();

        VentanaProductos ventanaProductos = new VentanaProductos(tienda, PATH);
        sceneProductos = ventanaProductos.getScene();

        Configurator configurator = new Configurator(tienda);
        sceneConfiguracion = configurator.getScene();

        mostrarMenuPrincipal();
    }

    public static void mostrarMenuPrincipal() {
        primaryStage.setScene(sceneMenuPrincipal);
        ajustarPantalla(primaryStage, sceneMenuPrincipal);
        primaryStage.show();
    }

    public static void mostrarVentanaProductos() {
        primaryStage.setTitle("Product Management");
        primaryStage.setScene(sceneProductos);
        ajustarPantalla(primaryStage, sceneProductos);
        primaryStage.show();
    }

    public static void MostrarConfiguracion() {
        primaryStage.setTitle("Configuration");
        primaryStage.setScene(sceneConfiguracion);
        ajustarPantalla(primaryStage, sceneConfiguracion);
        primaryStage.show();
    }


    private static void ajustarPantalla(Stage primaryStage, Scene escena) {
        Rectangle2D pantalla = Screen.getPrimary().getVisualBounds();
        double coordenadaX = (pantalla.getWidth()- escena.getWidth())/2;
        double coordenadaY = (pantalla.getHeight()- escena.getHeight())/2;
        primaryStage.setX(coordenadaX);
        primaryStage.setY(coordenadaY - 15);
    }

    public static Tienda cargarProductosXML(Path path){
        try{
            // Crear el DocumentBuilderFactory y el DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parsear el archivo XML
            Document doc = builder.parse(path.toFile());

            // Obtener el elemento raíz <tienda>
            Element tiendaElement = (Element) doc.getElementsByTagName("tienda").item(0);

            // Extraer los atributos de la tienda
            String nombreTienda = tiendaElement.getElementsByTagName("nombre").item(0).getTextContent();
            String direccionTienda = tiendaElement.getElementsByTagName("direccion").item(0).getTextContent();
            String descripcionTienda = tiendaElement.getElementsByTagName("descripcion").item(0).getTextContent();

            // Crear la instancia de la tienda con los atributos cargados
            tienda = new Tienda(nombreTienda, direccionTienda, descripcionTienda);

            // Obtener los productos
            NodeList productosNodeList = tiendaElement.getElementsByTagName("producto");
            // Recorrer cada producto y agregarlo a la tienda
            for (int i = 0; i < productosNodeList.getLength(); i++) {
                Element productoElement = (Element) productosNodeList.item(i);

                // Extraer los datos de cada producto como String
                String codigo = productoElement.getAttribute("codigo");
                String nombre = productoElement.getElementsByTagName("nombre").item(0).getTextContent();
                String precioStr = productoElement.getElementsByTagName("precio").item(0).getTextContent();
                double precio = Double.parseDouble(precioStr);
                String cantidadStr = productoElement.getElementsByTagName("cantidad").item(0).getTextContent();
                int cantidad = Integer.parseInt(cantidadStr);
                String descripcion = productoElement.getElementsByTagName("descripcion").item(0).getTextContent();
                String categoria = productoElement.getElementsByTagName("categoria").item(0).getTextContent();

                // Crear el objeto Producto y agregarlo a la tienda
                Producto producto = new Producto(codigo, nombre, precio, cantidad, descripcion, categoria);
                tienda.addProduct(producto);
            }

            VentanaProductos ventanaProductos = new VentanaProductos(tienda, path);
            sceneProductos = ventanaProductos.getScene();
            Configurator configurator = new Configurator(tienda);
            sceneConfiguracion = configurator.getScene();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        return tienda;
    }

    public static void recargarVentanaProductos(){
        VentanaProductos ventanaProductos = new VentanaProductos(tienda, PATH);
        sceneProductos = ventanaProductos.getScene();
        mostrarVentanaProductos();
    }

    public static void guardarProductosXML(Tienda tienda, Path path){
        try{
            // Crear un nuevo documento XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Crear el elemento raíz <productos>
            Element rootElement = doc.createElement("tienda");
            doc.appendChild(rootElement);

            Element nombreTienda = doc.createElement("nombre");
            nombreTienda.setTextContent(tienda.getNombre());
            rootElement.appendChild(nombreTienda);

            Element direccionTienda = doc.createElement("direccion");
            direccionTienda.setTextContent(tienda.getDireccion());
            rootElement.appendChild(direccionTienda);

            Element descripcionTienda = doc.createElement("descripcion");
            descripcionTienda.setTextContent(tienda.getDescripcion());
            rootElement.appendChild(descripcionTienda);

            Element productos = doc.createElement("productos");
            productos.setAttribute("num", String.valueOf(tienda.size()));

            // Crear un elemento <producto> por cada producto en la tienda
            for (Producto producto : tienda){
                Element productoElement = doc.createElement("producto");
                productoElement.setAttribute("codigo", producto.getCodigo());

                Element nombre = doc.createElement("nombre");
                nombre.setTextContent(producto.getNombre());
                productoElement.appendChild(nombre);

                Element precio = doc.createElement("precio");
                precio.setTextContent(String.valueOf(producto.getPrecio()));
                productoElement.appendChild(precio);

                Element cantidad = doc.createElement("cantidad");
                cantidad.setTextContent(String.valueOf(producto.getCantidad()));
                productoElement.appendChild(cantidad);

                Element descripcion = doc.createElement("descripcion");
                descripcion.setTextContent(producto.getDescripcion());
                productoElement.appendChild(descripcion);

                Element categoria = doc.createElement("categoria");
                categoria.setTextContent(producto.getCategoria());
                productoElement.appendChild(categoria);

                productos.appendChild(productoElement);

            }
            rootElement.appendChild(productos);

            // Guardar el documento en un archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(path.toFile());
            transformer.transform(source, result);

            // Guardar el path del archivo
            PATH = path;

        } catch (ParserConfigurationException | TransformerException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Path getPath() {
        return PATH;
    }

    public static void setPath(Path path) {
        PATH = path;
    }

    public static void main(String[] args) {
        launch(args);
    }


}