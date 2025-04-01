// Archivo: src/main/java/gui/SeleccionarTienda.java
package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import model.Tienda;
import service.TiendaService;

import java.util.List;

public class StoreSelecction {
    private final TiendaService tiendaService;
    private ListView<Tienda> listViewTiendas;

    public StoreSelecction() {
        tiendaService = new TiendaService();
    }

    public Scene getScene() {
        listViewTiendas = new ListView<>();
        listViewTiendas.setPrefWidth(300);
        listViewTiendas.setPrefHeight(300);
        listViewTiendas.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Tienda item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : "ID: " + item.getId() + " - " + item.getNombre());
            }
        });
        refreshListView();

        Button btnSeleccionar = new Button("Seleccionar Tienda");
        btnSeleccionar.setOnAction(e -> {
            Tienda tiendaSeleccionada = listViewTiendas.getSelectionModel().getSelectedItem();
            if (tiendaSeleccionada != null) {
                MainApp.setTienda(tiendaSeleccionada);
                MainApp.mostrarVentanaProductos();
            } else {
                mostrarAlerta("Seleccione una tienda de la lista.");
            }
        });

        VBox layout = new VBox(10, listViewTiendas, btnSeleccionar);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        return new Scene(layout, 400, 400);
    }

    private void refreshListView() {
        List<Tienda> tiendas = tiendaService.getAllTiendas();
        listViewTiendas.getItems().setAll(tiendas);
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}