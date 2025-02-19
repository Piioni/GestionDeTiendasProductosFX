package Classes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Configurator {
    private Tienda tienda;

    public Configurator( Tienda tienda) {
        this.tienda = tienda;
    }

    public Scene getScene() {

        // Panel superior para los campos de texto
        VBox panelSuperiorConf = new VBox(5);
        panelSuperiorConf.setPadding(new javafx.geometry.Insets(80, 0, 5, 0));


        // Panel para los labels y textfields
        GridPane panelCamposConf = new GridPane();
        panelCamposConf.setHgap(10);
        panelCamposConf.setVgap(10);
        panelCamposConf.setAlignment(Pos.CENTER);
        panelCamposConf.setPadding(new Insets(5, 10, 20 ,15));

        // Crear los elementos de la interfaz
        Label lblNombre = new Label("Nombre de la Tienda:");
        lblNombre.getStyleClass().add("label");

        Label lblDireccion = new Label("Direccion de la Tienda:");
        lblDireccion.getStyleClass().add("label");

        Label lblDescripcion = new Label("Descripcion de la Tienda:");
        lblDescripcion.getStyleClass().add("label");

        // Inicialización de los campos de texto
        TextField txtNombreConf = new TextField(); // Inicializar la variable de instancia
        TextField txtDireccionConf = new TextField(); // Inicializar la variable de instancia
        TextField txtDescripcionConf = new TextField(); // Inicializar la variable de instancia

        // Configuración de los campos de texto
        txtNombreConf.setPrefWidth(250);
        txtDireccionConf.setPrefWidth(250);
        txtDescripcionConf.setPrefWidth(250);

        // Añadir los campos de texto al panel
        panelCamposConf.add(lblNombre, 0, 0);
        panelCamposConf.add(txtNombreConf, 1, 0);
        panelCamposConf.add(lblDireccion, 0, 1);
        panelCamposConf.add(txtDireccionConf, 1, 1);
        panelCamposConf.add(lblDescripcion, 0, 2);
        panelCamposConf.add(txtDescripcionConf, 1, 2);

        panelSuperiorConf.getChildren().addAll(panelCamposConf);

        // Panel para la imagen
        HBox panelImagen = new HBox();
        panelImagen.setAlignment(Pos.CENTER);
        panelImagen.setPadding(new Insets(20, 0, 0, 0));

        // Debugging: Print the imageLogo path
        String imagePath = "file:src/Images/product.png";
        System.out.println("Loading imageLogo from: " + imagePath);

        // Load the imageLogo
        Image imageLogo = new Image(imagePath, true);
        if (imageLogo.isError()) {
            System.out.println("Error loading imageLogo: " + imageLogo.getException().getMessage());
            imageLogo.getException().printStackTrace();
        } else {
            System.out.println("Image loaded successfully.");
        }

        ImageView imageView = new ImageView(imageLogo);
        imageView.setFitWidth(170);
        imageView.setFitHeight(170);
        imageView.setPreserveRatio(true);
        panelImagen.getChildren().add(imageView);



        // Panel inferior para los botones
        HBox panelInferiorConf = new HBox(100);
        panelInferiorConf.setAlignment(Pos.CENTER);
        panelInferiorConf.setPadding(new Insets(10, 0, 10, 0));

        VBox panelBotonesConf = new VBox(30);
        panelBotonesConf.setAlignment(Pos.CENTER);
        panelBotonesConf.setPadding(new Insets(0, 0, 0, 0));

        // Crear los botones
        Button btnGuardarConf = new Button("Guardar Cambios");
        btnGuardarConf.getStyleClass().add("button");
        btnGuardarConf.setPrefWidth(150);
        btnGuardarConf.setOnAction(e -> {
            String nombre = txtNombreConf.getText().trim();
            String direccion = txtDireccionConf.getText().trim();
            String descripcion = txtDescripcionConf.getText().trim();

            // Debugging: Print the values of the text fields
            System.out.println("Nombre: " + nombre);
            System.out.println("Direccion: " + direccion);
            System.out.println("Descripcion: " + descripcion);

            // Validar que los campos no estén vacíos
            if (nombre.isEmpty() || direccion.isEmpty() || descripcion.isEmpty()) {
                mostrarAlerta("Por favor, complete todos los campos.");
            } else {
                if (MainApp.getPath() != null) {
                    tienda.setNombre(nombre);
                    tienda.setDireccion(direccion);
                    tienda.setDescripcion(descripcion);
                    MainApp.guardarProductosXML(tienda, MainApp.getPath());
                } else {
                    mostrarAlerta("No se ha cargado ningún archivo XML.");
                }
            }
        });

        Button btnLimpiarConf = new Button("Limpiar Campos");
        btnLimpiarConf.getStyleClass().add("button");
        btnLimpiarConf.setPrefWidth(150);
        btnLimpiarConf.setOnAction(e -> {
            txtNombreConf.clear();
            txtDireccionConf.clear();
            txtDescripcionConf.clear();
        });

        Button btnVolverConf = new Button("Volver");
        btnVolverConf.getStyleClass().add("button");
        btnVolverConf.setPrefWidth(150);
        btnVolverConf.setOnAction(e -> MainApp.mostrarVentanaProductos());

        panelBotonesConf.getChildren().addAll(btnGuardarConf, btnLimpiarConf, btnVolverConf);

        panelInferiorConf.getChildren().addAll(panelImagen, panelBotonesConf);

        // Crear el panel principal
        VBox panelPrincipal = new VBox(10);
        panelPrincipal.getChildren().addAll(panelSuperiorConf, panelInferiorConf);

        // Crear la escena
        Scene scene = new Scene(panelPrincipal, 620, 500);
        scene.getStylesheets().add("file:src/Styles/StylesConfiguracion.css");

        return scene;
    }

    private void mostrarAlerta(String mensaje) {
        // Crear una alerta para mostrar un mensaje de error
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
