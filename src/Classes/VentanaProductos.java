package Classes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;

public class VentanaProductos {
    private TextField txtCodigo, txtNombre, txtCantidad, txtPrecio, txtDescripcion;
    private ComboBox<String> cbCategoria;
    private ListView<String> lista;
    private final Tienda tienda;
    private Path PATH;

    public VentanaProductos(Tienda tienda, Path PATH) {
        this.tienda = tienda;
        this.PATH = PATH;
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
        panelCampos.setPadding(new Insets(5, 10, 20 ,15));

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
        cbCategoria.setMaxWidth(250);
        cbCategoria.getItems().addAll("Electronics", "Clothing", "Food", "Books", "Other");
        cbCategoria.getStyleClass().add("combo-box"); // Add this line to apply the CSS class


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
        panelCampos.add(cbCategoria, 1, 5);

        // Panel para la imagen y el titulo
        VBox panelSuperiorderecho = new VBox();
        panelSuperiorderecho.setAlignment(Pos.CENTER);
        panelSuperiorderecho.setPadding(new Insets(10, 0, 0, 0));

        HBox panelTitulo = new HBox();
        panelTitulo.setAlignment(Pos.CENTER);
        panelTitulo.setPadding(new Insets(0, 0, 10, 0));

        Label titulo = new Label(tienda.getNombre());
        titulo.getStyleClass().add("titulo");

        panelTitulo.getChildren().addAll(titulo);

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


        // Añadir el panel de la imagen al container superior
        panelSuperiorderecho.getChildren().addAll(panelTitulo, panelImagen);
        containerSuperior.getChildren().addAll(panelCampos, panelSuperiorderecho);

        HBox panelBotonesSuperior = new HBox(10); // Reduce el espacio entre los botones
        panelBotonesSuperior.setAlignment(Pos.CENTER);
        panelBotonesSuperior.setPadding(new Insets(15)); // Añadir margen entre categoria y botones

        // Creación y estilización de los botones
        Button btnAgregar = new Button("Add product");
        btnAgregar.getStyleClass().add("button");
        btnAgregar.setOnAction(e -> agregarProducto());

        Button btnEliminar = new Button("Delete product");
        btnEliminar.getStyleClass().add("button");
        btnEliminar.setOnAction(e -> eliminarProducto());

        Button btnBuscar = new Button("Search product");
        btnBuscar.getStyleClass().add("button");
        btnBuscar.setOnAction(e -> buscarProducto());

        Button btnModificar = new Button("Update product");
        btnModificar.getStyleClass().add("button");
        btnModificar.setOnAction(e -> modificarProducto());

        Button btnLimpiar = new Button("Clean fields");
        btnLimpiar.getStyleClass().add("button");
        btnLimpiar.setOnAction(e -> {
            txtCodigo.clear();
            txtNombre.clear();
            txtCantidad.clear();
            txtPrecio.clear();
            txtDescripcion.clear();
            cbCategoria.getSelectionModel().clearSelection();
        });

        panelBotonesSuperior.getChildren().addAll(btnAgregar, btnEliminar, btnBuscar, btnModificar, btnLimpiar);

        // Añadir el panel de botones al panel izquierdo
        panelSuperior.getChildren().addAll(containerSuperior, panelBotonesSuperior);
        panelSuperior.setAlignment(Pos.CENTER);

        // panel derecho (Listado de productos)
        VBox panelInferior = new VBox(10);
        panelInferior.setPadding(new Insets(10, 0 , 15, 0));

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
        Button btnGuardar = new Button("Save");
        btnGuardar.getStyleClass().add("button-inferior");
        btnGuardar.setOnAction(e -> guardarCambios());
        btnGuardar.setPrefWidth(150);
        Button btnSalir = new Button("Exit");
        btnSalir.getStyleClass().add("button-inferior");
        btnSalir.setOnAction(e -> MainApp.mostrarMenuPrincipal());
        btnSalir.setPrefWidth(150);

        panelBotonesInferior.getChildren().addAll(btnMostrar, btnGuardar, btnSalir);
        panelListaBotones.getChildren().addAll(panelLista, panelBotonesInferior);

        HBox panelBotonConfiguracion = new HBox();
        panelBotonConfiguracion.setAlignment(Pos.BASELINE_RIGHT);
        panelBotonConfiguracion.setPadding(new Insets(0, 10, 0, 10));

        // Load the image
        Image imageConfig = new Image("file:src/Images/config.png", true);
        if (imageConfig.isError()) {
            System.out.println("Error loading image: " + imageConfig.getException().getMessage());
            imageConfig.getException().printStackTrace();
        } else {
            System.out.println("Image loaded successfully.");
        }

        ImageView imageViewConfig = new ImageView(imageConfig);
        imageViewConfig.setFitWidth(45);
        imageViewConfig.setFitHeight(45);
        imageViewConfig.setPreserveRatio(true);

        imageViewConfig.setOnMouseClicked(e -> MainApp.MostrarConfiguracion());

        // Change the cursor to hand when hovering over the image
        imageViewConfig.setOnMouseEntered(e -> imageViewConfig.setCursor(Cursor.HAND));
        imageViewConfig.setOnMouseExited(e -> imageViewConfig.setCursor(Cursor.DEFAULT));

        // Create a tooltip and set its text
        Tooltip tooltip = new Tooltip("Click to configure settings");
        Tooltip.install(imageViewConfig, tooltip);

        panelBotonConfiguracion.getChildren().add(imageViewConfig);


        panelInferior.getChildren().addAll(panelTituloInferior, panelListaBotones, panelBotonConfiguracion);
        panelInferior.setAlignment(Pos.CENTER);

        // Creation de la ventana
        VBox tal = new VBox();
        tal.getChildren().addAll(panelSuperior, panelInferior);
        tal.getStyleClass().add("root");

        Scene scene = new Scene(tal, 870, 780);
        scene.getStylesheets().add("./Styles/StylesProductos.css");

        mostrarProductos();

        return scene;

    }

    // metodos de los botones
    private void agregarProducto() {
        // Obtener los valores de los textfields
        String codigo = txtCodigo.getText();
        String nombre = txtNombre.getText();
        String cantidadStr = txtCantidad.getText();
        String precioStr = txtPrecio.getText();
        String descripcion = txtDescripcion.getText();
        String categoria = cbCategoria.getValue();
        System.out.println(categoria);

        // Verificar que los campos no estén vacíos
        if (codigo.isEmpty() || nombre.isEmpty() || cantidadStr.isEmpty() || precioStr.isEmpty() || descripcion.isEmpty() || categoria == null) {
            mostrarAlerta("Todos los campos son obligatorios.");
            return;
        }
        // Verificar que el código no esté duplicado
        if (tienda.buscarProducto(codigo) != null) {
            mostrarAlerta("El código ya existe.");
            return;
        }
        // Verificar que el codigo cumpla la siguiente expresion regular (2 o 3 letras mayúsculas seguidas de 1 número)
        if (!codigo.matches("[A-Z]{2,3}\\d{1,2}") ) {
            mostrarAlerta("El código debe contener 2 o 3 letras mayúsculas seguidas de 1 número.");
            return;
        } else if (codigo.length() < 3 || codigo.length() > 6) {
            mostrarAlerta("El código debe tener entre 3 y 6 caracteres.");
            return;
        }

        // Verificar que los valores numéricos sean válidos
        int cantidad;
        double precio;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            precio = Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Cantidad y precio deben ser valores numéricos.");
            return;
        }

        Producto p = new Producto(codigo, nombre, precio, cantidad, descripcion, categoria);
        tienda.addProduct(p);

        // Limpiar los textfields
        txtCodigo.clear();
        txtNombre.clear();
        txtCantidad.clear();
        txtPrecio.clear();
        txtDescripcion.clear();
        cbCategoria.getSelectionModel().clearSelection();

        // Print and add the product to the list
        imprimirProducto(p);

    }

    // metodo para buscar uno o varios productos
    private void buscarProducto() {
        String codigo = txtCodigo.getText();
        String nombre = txtNombre.getText();
        String cantidadStr = txtCantidad.getText();
        String precioStr = txtPrecio.getText();
        String descripcion = txtDescripcion.getText();

        if (codigo.isEmpty() && nombre.isEmpty() && cantidadStr.isEmpty() && precioStr.isEmpty() && descripcion.isEmpty()) {
            mostrarAlerta("Por favor, llene al menos un campo para buscar.");
            return;
        }

        lista.getItems().clear();

        // Recorrer la lista de productos
        for (Producto p : tienda.getListaProductos()) {
            // Inicializa el match a true
            boolean match = true;

            // Verificar si los campos de búsqueda no están vacíos y si coinciden con los valores del producto
            if (!codigo.isEmpty() && !p.getCodigo().equals(codigo)) {
                match = false;
            }
            if (!nombre.isEmpty() && !p.getNombre().equals(nombre)) {
                match = false;
            }
            if (!cantidadStr.isEmpty()) {
                try {
                    int cantidad = Integer.parseInt(cantidadStr);
                    if (p.getCantidad() != cantidad) {
                        match = false;
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta("Cantidad debe ser un valor numérico.");
                    return;
                }
            }
            if (!precioStr.isEmpty()) {
                try {
                    double precio = Double.parseDouble(precioStr);
                    if (p.getPrecio() != precio) {
                        match = false;
                    }
                } catch (NumberFormatException e) {
                    mostrarAlerta("Precio debe ser un valor numérico.");
                    return;
                }
            }
            if (!descripcion.isEmpty() && !p.getDescripcion().equals(descripcion)) {
                match = false;
            }

            if (match) {
                lista.getItems().add(p.toString());
                txtCodigo.setText(p.getCodigo());
                txtNombre.setText(p.getNombre());
                txtCantidad.setText(String.valueOf(p.getCantidad()));
                txtPrecio.setText(String.valueOf(p.getPrecio()));
                txtDescripcion.setText(p.getDescripcion());
                return;
            }
        }

        if (lista.getItems().isEmpty()) {
            mostrarAlerta("No se encontraron productos que coincidan con los criterios de búsqueda.");
        }


    }

    private void eliminarProducto() {
        // Obtener el producto seleccionado en la lista
        String selectedItem = lista.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Obtener el código del producto seleccionado
            String codigo = selectedItem.split("\n")[0].split(":")[1].trim();
            tienda.eliminarProducto(codigo);
            lista.getItems().remove(selectedItem);
        } else {
            mostrarAlerta("Seleccione un producto para eliminar.");
        }

    }

    // Metodo para modificar un producto existente
    private void modificarProducto() {
        String codigo = txtCodigo.getText();
        String nombre = txtNombre.getText();
        String cantidadStr = txtCantidad.getText();
        String precioStr = txtPrecio.getText();
        String descripcion = txtDescripcion.getText();

        // Verificar que los campos no estén vacíos
        if (codigo.isEmpty() || nombre.isEmpty() || cantidadStr.isEmpty() || precioStr.isEmpty() || descripcion.isEmpty()) {
            mostrarAlerta("Todos los campos son obligatorios.");
            return;
        }

        // Verificar que los valores numéricos sean válidos
        int cantidad;
        double precio;

        try {
            cantidad = Integer.parseInt(cantidadStr);
            precio = Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Cantidad y precio deben ser valores numéricos.");
            return;
        }

        // Buscar el producto en la lista
        Producto p = tienda.buscarProducto(codigo);

        if (p != null) {
            // Modificar los valores del producto
            p.setNombre(nombre);
            p.setCantidad(cantidad);
            p.setPrecio(precio);
            p.setDescripcion(descripcion);

            // Actualizar la lista
            lista.getItems().clear();
            mostrarProductos();
        } else {
            mostrarAlerta("Producto no encontrado.");
        }
    }

    // Metodo para guardar los cambios en el archivo sin necesidad de seleccionar la ubicación
    public void guardarCambios() {
        // Verificar si se ha seleccionado una ubicación para guardar el archivo anteriormente
        if (PATH == null) {
            mostrarAlerta("Por favor, seleccione una ubicación para guardar el archivo.");
            guardar();
        } else {
            MainApp.guardarProductosXML(tienda, PATH);
            System.out.println("Cambios guardados en: " + PATH.toAbsolutePath());
        }
    }

    private void guardar() {
        // Permite al usuario seleccionar la ubicación para guardar el archivo
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Archivo");

        FileChooser.ExtensionFilter extFilter = new FileChooser.
                ExtensionFilter("Archivos XML (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                // Asegurarse de que el archivo tenga la extensión .json
                if (!file.getPath().endsWith(".xml")) {
                    file = new File(file.getPath() + ".xml");
                }

                // Guardar la lista de productos en un archivo JSON
                MainApp.guardarProductosXML(tienda, file.toPath());
                PATH = file.toPath(); // Guardar la ubicación del archivo para futuras referencias
                mostrarAlerta("Cambios guardados correctamente.");
                System.out.println("Guardado en: " + file.getAbsolutePath());

            } catch (SecurityException e) {
                mostrarAlerta("Permiso denegado: " + e.getMessage());
            } catch (Exception e) {
                mostrarAlerta("Error inesperado: " + e.getMessage());
            }
        } else {
            mostrarAlerta("Por favor, seleccione una ubicación para guardar el archivo.");
        }
    }

    private void mostrarProductos() {
        lista.getItems().clear();
        for (Producto p : tienda.getListaProductos()) {
            // Print and add each product to the list
            imprimirProducto(p);
        }
    }

    private void imprimirProducto(Producto producto) {
        // Add the product to the list
        lista.getItems().add(producto.toString());
        // Print the product
        System.out.println(producto.toString());
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