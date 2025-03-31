package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import model.Tienda;
import service.TiendaService;

public class TiendaSelectionWindow {
    private Scene scene;
    private Tienda tiendaSeleccionada;

    public TiendaSelectionWindow(TiendaService tiendaService) {
        ComboBox<Tienda> comboTiendas = new ComboBox<>();
        comboTiendas.getItems().addAll(tiendaService.getTiendas());
        // Se asume que la clase Tienda tiene implementado el método toString() para mostrar el nombre

        Button btnAceptar = new Button("Aceptar");
        btnAceptar.setOnAction(e -> {
            tiendaSeleccionada = comboTiendas.getValue();
            comboTiendas.getScene().getWindow().hide();
        });

        VBox layout = new VBox(10, comboTiendas, btnAceptar);
        layout.setPadding(new Insets(20));
        scene = new Scene(layout, 300, 150);
    }

    public Scene getScene() {
        return scene;
    }

    public Tienda getTiendaSeleccionada() {
        return tiendaSeleccionada;
    }
}