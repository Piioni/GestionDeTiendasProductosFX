package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Store;
import service.StoreService;

import java.util.List;
import java.util.Objects;

public class StoreSelection {
    private final StoreService storeService;
    private ListView<Store> listViewTiendas;

    public StoreSelection() {
        storeService = new StoreService();
    }

    public Scene getScene() {
        listViewTiendas = new ListView<>();
        listViewTiendas.setPrefWidth(300);
        listViewTiendas.setPrefHeight(300);
        listViewTiendas.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Store item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : "ID: " + item.getId() + " - " + item.getNombre());
            }
        });
        refreshListView();

        Button btnSeleccionar = new Button("Seleccionar Tienda");
        btnSeleccionar.setOnAction(e -> {
            Store tiendaSeleccionada = listViewTiendas.getSelectionModel().getSelectedItem();
            if (tiendaSeleccionada != null) {
                MainApp.setTienda(tiendaSeleccionada);
                MainApp.mostrarVentanaProductos();
            } else {
                mostrarAlerta();
            }
        });

        Button btnSalir = new Button("Exit to Main Menu");
        btnSalir.setOnAction(e -> MainApp.mostrarMenuPrincipal());

        HBox buttonLayout = new HBox(10, btnSeleccionar, btnSalir);
        buttonLayout.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10, listViewTiendas, buttonLayout);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 400, 400);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/stylesSelection.css")).toExternalForm());

        return scene;
    }

    private void refreshListView() {
        List<Store> tiendas = storeService.getTiendas();
        listViewTiendas.getItems().setAll(tiendas);
    }

    private void mostrarAlerta() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Warning");
        alerta.setHeaderText(null);
        alerta.setContentText("Seleccione una tienda de la lista.");
        alerta.showAndWait();
    }
}