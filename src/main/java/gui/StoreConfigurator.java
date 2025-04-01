// Archivo: src/main/java/gui/TiendaConfigurator.java
package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Tienda;
import service.TiendaService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StoreConfigurator {
    private TextField txtNombreConf;
    private TextField txtDireccionConf;
    private TextField txtDescripcionConf;
    private TextField txtBuscar;
    private final TiendaService tiendaService;
    private ListView<Tienda> listViewTiendas;

    public StoreConfigurator() {
        tiendaService = new TiendaService();
    }

    public Scene getScene() {
        double BUTTON_WIDTH = 140;

        // ListView para mostrar todas las tiendas
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
        listViewTiendas.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                cargarDetallesTienda(newVal);
            }
        });

        // Panel para los campos de detalle
        GridPane panelCamposConf = new GridPane();
        panelCamposConf.setHgap(10);
        panelCamposConf.setVgap(10);
        panelCamposConf.setPadding(new Insets(10));
        panelCamposConf.setAlignment(Pos.CENTER);

        Label lblNombre = new Label("Store name:");
        txtNombreConf = new TextField();
        Label lblDireccion = new Label("Store address:");
        txtDireccionConf = new TextField();
        Label lblDescripcion = new Label("Store description:");
        txtDescripcionConf = new TextField();

        panelCamposConf.add(lblNombre, 0, 0);
        panelCamposConf.add(txtNombreConf, 1, 0);
        panelCamposConf.add(lblDireccion, 0, 1);
        panelCamposConf.add(txtDireccionConf, 1, 1);
        panelCamposConf.add(lblDescripcion, 0, 2);
        panelCamposConf.add(txtDescripcionConf, 1, 2);

        // Campo y botón de búsqueda
        Label lblBuscar = new Label("Buscar:");
        txtBuscar = new TextField();
        Button btnBuscarTienda = new Button("Buscar Tienda");
        btnBuscarTienda.setOnAction(e -> buscarTienda());
        btnBuscarTienda.setPrefWidth(BUTTON_WIDTH);
        HBox panelBusqueda = new HBox(10, lblBuscar, txtBuscar, btnBuscarTienda);
        panelBusqueda.setAlignment(Pos.CENTER);

        // Botones para operaciones divididos en 2 filas
        Button btnGuardarCambios = new Button("Guardar Cambios");
        btnGuardarCambios.setPrefWidth(BUTTON_WIDTH);
        btnGuardarCambios.setOnAction(e -> guardarCambios());

        Button btnSeleccionarTienda = new Button("Seleccionar Tienda");
        btnSeleccionarTienda.setPrefWidth(BUTTON_WIDTH);
        btnSeleccionarTienda.setOnAction(e -> {
            Tienda tiendaSeleccionada = listViewTiendas.getSelectionModel().getSelectedItem();
            if (tiendaSeleccionada != null) {
                tiendaSeleccionada.setNombre(txtNombreConf.getText().trim());
                tiendaSeleccionada.setDireccion(txtDireccionConf.getText().trim());
                tiendaSeleccionada.setDescripcion(txtDescripcionConf.getText().trim());
                MainApp.setTienda(tiendaSeleccionada);
                MainApp.mostrarVentanaProductos();
            } else {
                mostrarAlerta("Seleccione una tienda de la lista.");
            }
        });

        Button btnAgregarTienda = new Button("Agregar Tienda");
        btnAgregarTienda.setPrefWidth(BUTTON_WIDTH);
        btnAgregarTienda.setOnAction(e -> agregarTienda());

        Button btnEliminarTienda = new Button("Eliminar Tienda");
        btnEliminarTienda.setPrefWidth(BUTTON_WIDTH);
        btnEliminarTienda.setOnAction(e -> eliminarTienda());

        // Nuevo botón para limpiar los campos
        Button btnLimpiarCampos = new Button("Limpiar Campos");
        btnLimpiarCampos.setPrefWidth(BUTTON_WIDTH);
        btnLimpiarCampos.setOnAction(e -> limpiarCampos());

        HBox fila1Botones = new HBox(10, btnGuardarCambios, btnSeleccionarTienda);
        fila1Botones.setAlignment(Pos.CENTER);
        HBox fila2Botones = new HBox(10, btnAgregarTienda, btnEliminarTienda);
        fila2Botones.setAlignment(Pos.CENTER);
        HBox fila3Botones = new HBox(10, btnLimpiarCampos);
        fila3Botones.setAlignment(Pos.CENTER);

        VBox panelBotones = new VBox(10, fila1Botones, fila2Botones, fila3Botones);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setPadding(new Insets(10));

        VBox panelIzquierdo = new VBox(10, panelCamposConf, panelBotones);
        panelIzquierdo.setAlignment(Pos.CENTER);

        // Panel izquierdo con búsqueda y ListView centrado verticalmente
        VBox panelDerecho = new VBox(10, panelBusqueda, listViewTiendas);
        panelDerecho.setAlignment(Pos.CENTER);

        HBox panelPrincipal = new HBox(20, panelIzquierdo, panelDerecho);
        panelPrincipal.setPadding(new Insets(20));
        panelPrincipal.setAlignment(Pos.CENTER);

        Scene scene = new Scene(panelPrincipal, 800, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/stylesConfig.css")).toExternalForm());
        return scene;
    }

    private void cargarDetallesTienda(Tienda tienda) {
        txtNombreConf.setText(tienda.getNombre());
        txtDireccionConf.setText(tienda.getDireccion());
        txtDescripcionConf.setText(tienda.getDescripcion());
    }

    private void guardarCambios() {
        Tienda tiendaSeleccionada = listViewTiendas.getSelectionModel().getSelectedItem();
        if (tiendaSeleccionada != null) {
            tiendaSeleccionada.setNombre(txtNombreConf.getText().trim());
            tiendaSeleccionada.setDireccion(txtDireccionConf.getText().trim());
            tiendaSeleccionada.setDescripcion(txtDescripcionConf.getText().trim());
            tiendaService.update(tiendaSeleccionada);
            mostrarAlerta("Cambios guardados correctamente.");
            refreshListView();
        } else {
            mostrarAlerta("Primero seleccione una tienda de la lista.");
        }
    }

    private void agregarTienda() {
        String nombre = txtNombreConf.getText().trim();
        String direccion = txtDireccionConf.getText().trim();
        String descripcion = txtDescripcionConf.getText().trim();
        if (nombre.isEmpty()) {
            mostrarAlerta("El nombre es obligatorio.");
            return;
        }
        Tienda nuevaTienda = new Tienda();
        nuevaTienda.setNombre(nombre);
        nuevaTienda.setDireccion(direccion);
        nuevaTienda.setDescripcion(descripcion);
        tiendaService.save(nuevaTienda);
        mostrarAlerta("Tienda agregada correctamente.");
        refreshListView();
    }

    private void buscarTienda() {
        String termino = txtBuscar.getText().trim().toLowerCase();
        List<Tienda> tiendasFiltradas = tiendaService.getTiendas().stream()
                .filter(t -> t.getNombre().toLowerCase().contains(termino))
                .collect(Collectors.toList());
        listViewTiendas.getItems().setAll(tiendasFiltradas);
    }

    private void eliminarTienda() {
        Tienda tiendaSeleccionada = listViewTiendas.getSelectionModel().getSelectedItem();
        if (tiendaSeleccionada != null) {
            tiendaService.delete(tiendaSeleccionada);
            mostrarAlerta("Tienda eliminada correctamente.");
            refreshListView();
        } else {
            mostrarAlerta("Seleccione una tienda para eliminar.");
        }
    }

    private void limpiarCampos() {
        txtNombreConf.clear();
        txtDireccionConf.clear();
        txtDescripcionConf.clear();
    }

    private void refreshListView() {
        List<Tienda> tiendas = tiendaService.getTiendas();
        if (listViewTiendas == null) {
            return;
        }
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