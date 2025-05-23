package gui;

import controller.ProductController;
import model.Product;
import model.Store;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.*;

public class VentanaProductos {
    private TextField txtCodigo, txtNombre, txtCantidad, txtPrecio, txtDescripcion;
    private ComboBox<String> cbCategoria;
    private ListView<String> lista;
    private final Store store;
    private final ProductController productController ;


    public VentanaProductos(Store store) {
        this.store = store;
        this.productController = new ProductController(store);
    }

    public Scene getScene() {

        // Panel superior para los campos de texto y botones
        VBox panelSuperior = new VBox(5);
        panelSuperior.setPadding(new Insets(15, 0, 5, 0));

        // Panel que contendra labels y textfields como la imagen
        HBox containerSuperior = new HBox(100);
        containerSuperior.setAlignment(Pos.CENTER);
        containerSuperior.setPadding(new Insets(20, 0, 0, 0));

        // Panel para los labels y textfields
        GridPane panelCampos = new GridPane();
        panelCampos.setHgap(10);
        panelCampos.setVgap(10);
        panelCampos.setAlignment(Pos.CENTER);
        panelCampos.setPadding(new Insets(5, 10, 20, 15));

        // Creación y estilización de los labels y textfields
        Label lblCodigo = new Label("Code");
        lblCodigo.getStyleClass().add("label");
        txtCodigo = new TextField();
        txtCodigo.setPrefWidth(250);
        txtCodigo.getStyleClass().add("text-field");

        Label lblNombre = new Label("Name");
        lblNombre.getStyleClass().add("label");
        txtNombre = new TextField();
        txtNombre.setPrefWidth(250);
        txtNombre.getStyleClass().add("text-field");

        Label lblCantidad = new Label("Quantity");
        lblCantidad.getStyleClass().add("label");
        txtCantidad = new TextField();
        txtCantidad.setPrefWidth(250);
        txtCantidad.getStyleClass().add("text-field");

        Label lblPrecio = new Label("Price");
        lblPrecio.getStyleClass().add("label");
        txtPrecio = new TextField();
        txtPrecio.setPrefWidth(250);
        txtPrecio.getStyleClass().add("text-field");

        Label lblDescripcion = new Label("Description");
        lblDescripcion.getStyleClass().add("label");
        txtDescripcion = new TextField();
        txtDescripcion.setPrefWidth(250);
        txtDescripcion.getStyleClass().add("text-field");

        Label lblCategoria = new Label("Category");
        lblCategoria.getStyleClass().add("label");
        cbCategoria = new ComboBox<>();
        cbCategoria.setPrefWidth(250);

        // Añadir las categorías al ComboBox
        List<String> categorias = new ArrayList<>();
        categorias.add("Fijaciones y conectores");
        categorias.add("Materiales de fabricación");
        categorias.add("Suministros de jardinería");
        categorias.add("Materiales de construcción");
        categorias.add("Elementos de montaje");

        cbCategoria.getItems().addAll(categorias);
        cbCategoria.getStyleClass().add("combo-box");

        // Crear la imagen
        Image imageFind = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/find.png")));        ImageView imageViewFind = new ImageView(imageFind);
        imageViewFind.setFitWidth(20);
        imageViewFind.setFitHeight(20);

        // Crear un Region para aumentar la hitbox
        Region hitbox = new Region();
        hitbox.setPrefSize(30, 30); // Tamaño de la hitbox cuadrada
        hitbox.setStyle("-fx-background-color: transparent;"); // Hacer la hitbox transparente

        // Crear un StackPane para contener la imagen y la hitbox
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(hitbox, imageViewFind);

        // Cambiar el cursor a mano cuando se pasa sobre la imagen
        stackPane.setOnMouseEntered(e -> stackPane.setCursor(Cursor.HAND));
        stackPane.setOnMouseExited(e -> stackPane.setCursor(Cursor.DEFAULT));

        // Crear un Tooltip y establecer su texto
        Tooltip tooltipFind = new Tooltip("Click to search by category");
        tooltipFind.setShowDelay(javafx.util.Duration.millis(100));
        Tooltip.install(stackPane, tooltipFind);

        // Buscar productos por categoría al hacer clic en la imagen
        stackPane.setOnMouseClicked(e -> {
            String categoriaSeleccionada = cbCategoria.getValue();
            if (categoriaSeleccionada != null) {
                List<Product> productosPorCategoria = productController.buscarPorCategoria(categoriaSeleccionada);
                lista.getItems().clear();
                for (Product producto : productosPorCategoria) {
                    imprimirProducto(producto);
                }
            } else {
                mostrarAlerta("Seleccione una categoría para buscar.");
            }
        });

        // Crear un HBox para contener el ComboBox y el StackPane
        HBox hBoxCategoria = new HBox(5); // 5 es el espacio entre el ComboBox y el StackPane
        hBoxCategoria.getChildren().addAll(cbCategoria, stackPane);

        // Añadir labels y textfields al GridPane
        panelCampos.add(lblCodigo, 0, 0);
        panelCampos.add(txtCodigo, 1, 0);
        panelCampos.add(lblNombre, 0, 1);
        panelCampos.add(txtNombre, 1, 1);
        panelCampos.add(lblCantidad, 0, 2);
        panelCampos.add(txtCantidad, 1, 2);
        panelCampos.add(lblPrecio, 0, 3);
        panelCampos.add(txtPrecio, 1, 3);
        panelCampos.add(lblDescripcion, 0, 4);
        panelCampos.add(txtDescripcion, 1, 4);
        panelCampos.add(lblCategoria, 0, 5);
        panelCampos.add(hBoxCategoria, 1, 5);

        // Panel para la imagen y el titulo
        VBox panelSuperiorderecho = new VBox();
        panelSuperiorderecho.setAlignment(Pos.CENTER);
        panelSuperiorderecho.setPadding(new Insets(10, 0, 0, 0));

        HBox panelTitulo = new HBox();
        panelTitulo.setAlignment(Pos.CENTER);
        panelTitulo.setPadding(new Insets(0, 0, 10, 0));

        Label titulo = new Label(store.getNombre());
        titulo.getStyleClass().add("titulo");

        panelTitulo.getChildren().addAll(titulo);

        // Panel para la imagen
        HBox panelImagen = new HBox();
        panelImagen.setAlignment(Pos.CENTER);
        panelImagen.setPadding(new Insets(20, 0, 0, 0));

        // Load the imageLogo
        Image imageLogo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/product.png")));


        ImageView imageView = new ImageView(imageLogo);
        imageView.setFitWidth(170);
        imageView.setFitHeight(170);
        imageView.setPreserveRatio(true);
        panelImagen.getChildren().add(imageView);

        // Añadir el panel de la imagen al container superior
        panelSuperiorderecho.getChildren().addAll(panelTitulo, panelImagen);
        containerSuperior.getChildren().addAll(panelCampos, panelSuperiorderecho);

        HBox panelBotonesSuperior = new HBox(10);
        panelBotonesSuperior.setAlignment(Pos.CENTER);
        panelBotonesSuperior.setPadding(new Insets(15));

        // Creación y estilización de los botones
        Button btnAgregar = new Button("Add product");
        btnAgregar.getStyleClass().add("button");
        btnAgregar.setOnAction(e -> {
            try {
                productController.agregarProducto(
                        txtCodigo.getText(),
                        txtNombre.getText(),
                        txtCantidad.getText(),
                        txtPrecio.getText(),
                        txtDescripcion.getText(),
                        cbCategoria.getValue()
                );
                mostrarAlerta("Producto agregado correctamente.");
                limpiarCampos();
                mostrarProductos();
            } catch (IllegalArgumentException ex) {
                mostrarAlerta(ex.getMessage());
            }
        });

        Button btnEliminar = new Button("Delete product");
        btnEliminar.getStyleClass().add("button");
        btnEliminar.setOnAction(e -> {
            String codigo = txtCodigo.getText();
            if (codigo.isEmpty()) {
                mostrarAlerta("Ingrese el código del producto a eliminar.");
            } else {
                try {
                    productController.eliminarProducto(codigo);
                    mostrarAlerta("Producto eliminado correctamente.");
                    limpiarCampos();
                    mostrarProductos(); // Actualizar la lista
                } catch (IllegalArgumentException ex) {
                    mostrarAlerta(ex.getMessage());
                }
            }
        });

        Button btnBuscar = new Button("Search product");
        btnBuscar.getStyleClass().add("button");
        btnBuscar.setOnAction(e -> {
            String codigo = txtCodigo.getText();
            if (codigo.isEmpty()) {
                mostrarAlerta("Ingrese el código del producto a buscar.");
            } else {
                List<Product> resultados = productController.buscarProducto(codigo);
                lista.getItems().clear();
                if (resultados.isEmpty()) {
                    mostrarAlerta("No se encontraron productos con ese código.");
                } else {
                    for (Product producto : resultados) {
                        imprimirProducto(producto);
                    }
                    // Rellenar los TextField con los datos del primer producto encontrado
                    Product productoEncontrado = resultados.getFirst();
                    txtCodigo.setText(productoEncontrado.getCodigo());
                    txtNombre.setText(productoEncontrado.getNombre());
                    txtCantidad.setText(String.valueOf(productoEncontrado.getCantidad()));
                    txtPrecio.setText(String.valueOf(productoEncontrado.getPrecio()));
                    txtDescripcion.setText(productoEncontrado.getDescripcion());
                    cbCategoria.setValue(productoEncontrado.getCategoria());
                }
            }
        });

        Button btnModificar = new Button("Update product");
        btnModificar.getStyleClass().add("button");
        btnModificar.setOnAction(e -> {
            try {
                productController.modificarProducto(
                        txtCodigo.getText(),
                        txtNombre.getText(),
                        txtCantidad.getText(),
                        txtPrecio.getText(),
                        txtDescripcion.getText(),
                        cbCategoria.getValue()
                );
                mostrarAlerta("Producto modificado correctamente.");
                limpiarCampos();
                mostrarProductos();
            } catch (IllegalArgumentException ex) {
                mostrarAlerta(ex.getMessage());
            }
        });

        Button btnLimpiar = new Button("Clean fields");
        btnLimpiar.getStyleClass().add("button");
        btnLimpiar.setOnAction(e -> limpiarCampos());

        panelBotonesSuperior.getChildren().addAll(btnAgregar, btnEliminar, btnBuscar, btnModificar, btnLimpiar);

        // Añadir los paneles al panel superior
        panelSuperior.getChildren().addAll(containerSuperior, panelBotonesSuperior);
        panelSuperior.setAlignment(Pos.CENTER);

        // panel inferior para la lista de productos y botones
        VBox panelInferior = new VBox(10);
        panelInferior.setPadding(new Insets(10, 0, 15, 0));

        HBox panelTituloInferior = new HBox();
        panelTituloInferior.setAlignment(Pos.BASELINE_LEFT);
        panelTituloInferior.setPadding(new Insets(0, 0, 5, 88));

        Label listadoProductos = new Label("Products list");
        listadoProductos.getStyleClass().add("label-title");

        panelTituloInferior.getChildren().addAll(listadoProductos);

        HBox panelListaBotones = new HBox(45);
        panelListaBotones.setAlignment(Pos.CENTER);
        panelListaBotones.setPadding(new Insets(0, 0, 10, 0));

        HBox panelLista = new HBox(10);
        panelLista.setAlignment(Pos.CENTER);
        panelLista.setPadding(new Insets(7, 0, 7, 0));

        lista = new ListView<>();
        lista.setPrefWidth(500);
        lista.setMaxWidth(600);
        lista.getStyleClass().add("list-view");

        panelLista.getChildren().add(lista);

        // Panel (inferior) de botones
        VBox panelBotonesInferior = new VBox(25);
        panelBotonesInferior.setAlignment(Pos.CENTER);
        panelBotonesInferior.setPadding(new Insets(0, 0, 10, 0));

        // Creación y estilización de los botones
        Button btnMostrar = new Button("Display all");
        btnMostrar.getStyleClass().add("button-inferior");
        btnMostrar.setOnAction(e -> mostrarProductos());
        btnMostrar.setPrefWidth(150);
        Button btnSalir = new Button("Exit");
        btnSalir.getStyleClass().add("button-inferior");
        btnSalir.setOnAction(e -> MainApp.mostrarMenuPrincipal());
        btnSalir.setPrefWidth(150);

        // Añadir los botones al panel de botones inferior y al panel inferior
        panelBotonesInferior.getChildren().addAll(btnMostrar, btnSalir);
        panelListaBotones.getChildren().addAll(panelLista, panelBotonesInferior);

        HBox panelBotonConfiguracion = new HBox();
        panelBotonConfiguracion.setAlignment(Pos.BASELINE_RIGHT);
        panelBotonConfiguracion.setPadding(new Insets(0, 10, 0, 10));

        // Load the image
        Image imageConfig = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/config.png")));
        ImageView imageViewConfig = new ImageView(imageConfig);
        imageViewConfig.setFitWidth(45);
        imageViewConfig.setFitHeight(45);
        imageViewConfig.setPreserveRatio(true);

        imageViewConfig.setOnMouseClicked(e -> MainApp.mostrarConfiguracion());

        // Change the cursor to hand when hovering over the image
        imageViewConfig.setOnMouseEntered(e -> imageViewConfig.setCursor(Cursor.HAND));
        imageViewConfig.setOnMouseExited(e -> imageViewConfig.setCursor(Cursor.DEFAULT));

        // Create a tooltip and set its text
        Tooltip tooltip = new Tooltip("Click to configure settings");
        tooltip.setShowDelay(javafx.util.Duration.millis(100));
        Tooltip.install(imageViewConfig, tooltip);

        panelBotonConfiguracion.getChildren().add(imageViewConfig);

        panelInferior.getChildren().addAll(panelTituloInferior, panelListaBotones, panelBotonConfiguracion);
        panelInferior.setAlignment(Pos.CENTER);

        // Creation de la ventana
        VBox tal = new VBox();
        tal.getChildren().addAll(panelSuperior, panelInferior);
        tal.getStyleClass().add("root");

        Scene scene = new Scene(tal, 870, 780);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/stylesProductos.css")).toExternalForm());

        mostrarProductos();
        return scene;


    }

    private void mostrarProductos() {
        List<Product> products = productController.mostrarProductos();
        lista.getItems().clear();
        for (Product producto : products) {
            imprimirProducto(producto);
        }
    }

    private void imprimirProducto(Product producto) {
        // Add the product to the list
        lista.getItems().add(producto.toString());
        // Print the product
        System.out.println(producto);
    }

    private void limpiarCampos() {
        txtCodigo.clear();
        txtNombre.clear();
        txtCantidad.clear();
        txtPrecio.clear();
        txtDescripcion.clear();
        cbCategoria.getSelectionModel().clearSelection();
        cbCategoria.setValue(null);
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}