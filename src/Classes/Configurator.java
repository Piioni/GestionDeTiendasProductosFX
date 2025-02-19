package Classes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class Configurator {
    private TextField txtNombre, txtDireccion, txtDescripcion;
    private Button btnGuardar, btnLimpiar, btnVolver;
    private Stage stage;


    public Configurator(Stage stage, Tienda tienda) {
        this.stage = stage;
    }

    public Scene getScene() {

        // Panel superior para los campos de texto
        VBox panelSuperior = new VBox(5);
        panelSuperior.setPadding(new javafx.geometry.Insets(80, 0, 5, 0));


        // Panel para los labels y textfields
        GridPane panelCampos = new GridPane();
        panelCampos.setHgap(10);
        panelCampos.setVgap(10);
        panelCampos.setAlignment(Pos.CENTER);
        panelCampos.setPadding(new Insets(5, 10, 20 ,15));

        // Crear los elementos de la interfaz
        Label lblNombre = new Label("Nombre de la Tienda:");
        lblNombre.getStyleClass().add("label");
        txtNombre = new TextField();
        txtNombre.setPrefWidth(250);
        txtNombre.getStyleClass().add("text-field");


        Label lblDireccion = new Label("Direccion de la Tienda:");
        lblDireccion.getStyleClass().add("label");
        txtDireccion = new TextField();
        txtDireccion.setPrefWidth(250);
        txtDireccion.getStyleClass().add("text-field");

        Label lblDescripcion = new Label("Descripcion de la Tienda:");
        lblDescripcion.getStyleClass().add("label");
        txtDescripcion = new TextField();
        txtDescripcion.setPrefWidth(250);
        txtDescripcion.getStyleClass().add("text-field");

        // Agregar los elementos al panel
        panelCampos.add(lblNombre, 0, 0);
        panelCampos.add(txtNombre, 1, 0);
        panelCampos.add(lblDireccion, 0, 1);
        panelCampos.add(txtDireccion, 1, 1);
        panelCampos.add(lblDescripcion, 0, 2);
        panelCampos.add(txtDescripcion, 1, 2);

        panelSuperior.getChildren().addAll(panelCampos);

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
        HBox panelInferior = new HBox(100);
        panelInferior.setAlignment(Pos.CENTER);
        panelInferior.setPadding(new Insets(10, 0, 10, 0));

        VBox panelBotones = new VBox(30);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setPadding(new Insets(0, 0, 0, 0));

        // Crear los botones
        btnGuardar = new Button("Guardar Cambios");
        btnGuardar.getStyleClass().add("button");
        btnGuardar.setPrefWidth(150);

        btnLimpiar = new Button("Limpiar Campos");
        btnLimpiar.getStyleClass().add("button");
        btnLimpiar.setPrefWidth(150);

        btnVolver = new Button("Volver");
        btnVolver.getStyleClass().add("button");
        btnVolver.setPrefWidth(150);

        panelBotones.getChildren().addAll(btnGuardar, btnLimpiar, btnVolver);

        panelInferior.getChildren().addAll(panelImagen, panelBotones);

        // Crear el panel principal
        VBox panelPrincipal = new VBox(10);
        panelPrincipal.getChildren().addAll(panelSuperior, panelInferior);

        // Crear la escena
        Scene scene = new Scene(panelPrincipal, 620, 500);
        scene.getStylesheets().add("file:src/Styles/StylesConfiguracion.css");

        return scene;
    }
}
