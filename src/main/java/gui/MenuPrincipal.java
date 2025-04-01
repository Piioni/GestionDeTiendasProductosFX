package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Objects;

public class MenuPrincipal {

    public MenuPrincipal() {}

    public Scene getScene() {
        // Creation de la estructura de la escena
        VBox menuLayout = new VBox(20);
        menuLayout.setPadding(new Insets(20));
        menuLayout.setAlignment(Pos.CENTER);

        // Creation del título
        Label titulo = new Label("Menú Principal");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titulo.getStyleClass().add("label-title");

        VBox tituloBox = new VBox(titulo);
        tituloBox.setPadding(new Insets(0, 0, 25, 0));
        tituloBox.setAlignment(Pos.CENTER);

        // Creation de los botones
        Button btnProductos = new Button("Products management");
        btnProductos.setOnAction(e -> MainApp.mostrarVentanaProductos());
        btnProductos.setPrefWidth(200);

        Button btnCargar = new Button("Store configuration");
        btnCargar.setOnAction(e -> MainApp.mostrarConfiguracion());
        btnCargar.setPrefWidth(200);

        Button btnSalir = new Button("Exit");
        btnSalir.setOnAction(e -> System.exit(0));
        btnSalir.setPrefWidth(200);

        menuLayout.getChildren().addAll(tituloBox, btnProductos, btnCargar, btnSalir);

        Scene scene = new Scene(menuLayout, 450, 400);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/stylesMenu.css")).toExternalForm());
        return scene;
    }


}