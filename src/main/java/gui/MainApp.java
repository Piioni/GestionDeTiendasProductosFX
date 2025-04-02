package gui;

import model.Store;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

public class MainApp extends Application {
    private static Stage primaryStage;
    private static Store tienda;
    private static Scene sceneMenuPrincipal;
    private static Scene sceneProductos;
    private static Scene sceneConfiguracion;
    private static Scene sceneSeleccionarTienda;

    @Override
    public void start(Stage primaryStage) {
        MainApp.primaryStage = primaryStage;

        // Crear las escenas una sola vez
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        sceneMenuPrincipal = menuPrincipal.getScene();

        StoreConfigurator configurator = new StoreConfigurator();
        sceneConfiguracion = configurator.getScene();

        StoreSelection selector = new StoreSelection();
        sceneSeleccionarTienda = selector.getScene();

        // Mostrar el menú principal al inicio
        mostrarMenuPrincipal();
    }

    public static void mostrarMenuPrincipal() {
        primaryStage.setTitle("Menú Principal");
        primaryStage.setScene(sceneMenuPrincipal);
        ajustarPantalla(primaryStage, sceneMenuPrincipal);
        primaryStage.show();
    }

    public static void mostrarVentanaProductos() {
        // Verificar si hay tienda seleccionada antes de mostrar la ventana de productos
        if (tienda == null) {
            mostrarSeleccionarTienda();
        } else {
            primaryStage.setTitle("Gestión de Productos");
            primaryStage.setScene(sceneProductos);
            ajustarPantalla(primaryStage, sceneProductos);
            primaryStage.show();
        }
    }

    public static void mostrarConfiguracion() {
        primaryStage.setTitle("Configuración");
        primaryStage.setScene(sceneConfiguracion);
        ajustarPantalla(primaryStage, sceneConfiguracion);
        primaryStage.show();
    }

    public static void mostrarSeleccionarTienda() {
        primaryStage.setTitle("Seleccionar Tienda");
        primaryStage.setScene(sceneSeleccionarTienda);
        ajustarPantalla(primaryStage, sceneSeleccionarTienda);
        primaryStage.show();
    }

    public static void setTienda(Store tiendaSeleccionada) {
        tienda = tiendaSeleccionada;
        // Aquí puedes agregar lógica adicional si es necesario
        VentanaProductos ventanaProductos = new VentanaProductos(tienda);
        sceneProductos = ventanaProductos.getScene();

    }

    private static void ajustarPantalla(Stage primaryStage, Scene escena) {
        Rectangle2D pantalla = Screen.getPrimary().getVisualBounds();
        double coordenadaX = (pantalla.getWidth() - escena.getWidth()) / 2;
        double coordenadaY = (pantalla.getHeight() - escena.getHeight()) / 2;
        primaryStage.setX(coordenadaX);
        primaryStage.setY(coordenadaY - 15);
    }

    public static void main(String[] args) {
        launch(args);
    }
}