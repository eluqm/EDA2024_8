/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reproductormusic;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.*;
import javafx.event.*;
import java.util.Collections;
import javafx.scene.control.*;
import javafx.stage.Window;
import javafx.scene.input.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.Modality;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import javafx.application.Platform;





/**
 *
 * @author Usuario
 */
public class Interfaz extends Application {
    boolean activateEnter = false;

    public void start(Stage primaryStage) throws Exception {
        Trie trie = new Trie();
        String csvFile = "src/resources/spotify_data.csv";
        Trie music = new Trie();
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            String line[];
            int count = 0;
            while ((line = reader.readNext()) != null && count <= 20000) {
                Song cancion = new Song(line[2], line[3], line[1], line[4], line[5]);
                music.insert(cancion.getName_track(), cancion);
                count++;
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        DoublyLinkedList<Song> songs = music.getCombinacion();

        TextField textField = new TextField();
        textField.setPromptText("Search");

        TableView<Song> table = new TableView<>();

        ComboBox<String> comboBox = new ComboBox<>();
        ObservableList<String> options = FXCollections.observableArrayList(
            "Artist ->",
            "Name ->",
            "Popularity <-",
            "Year <-",
            "Artist <-",
            "Name <-",
            "Popularity ->",
            "Year ->"
        );
        comboBox.setItems(options);
        comboBox.setPromptText("Select an option");
        comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String selectedOption = comboBox.getValue();
                if (selectedOption != null) {
                    BTree bTree;
                    ObservableList<Song> tableItems = table.getItems();
                    switch (selectedOption) {
                        case "Artist ->":
                            bTree = new BTree(4, SongComparate.byArtist());
                            break;
                        case "Name ->":
                            bTree = new BTree(4, SongComparate.byName());
                            break;
                        case "Popularity <-":
                            bTree = new BTree(4, SongComparate.byPopularity());
                            break;
                        case "Year <-":
                            bTree = new BTree(4, SongComparate.byYear());
                            break;
                        case "Artist <-":
                            bTree = new BTree(4, SongComparate.byArtistDescending());
                            break;
                        case "Name <-":
                            bTree = new BTree(4, SongComparate.byNameDescending());
                            break;
                        case "Popularity ->":
                            bTree = new BTree(4, SongComparate.byPopularityAscending());
                            break;
                        case "Year ->":
                            bTree = new BTree(4, SongComparate.byYearAscending());
                            break;
                        default:
                            System.out.println("Unknown option selected");
                            return;
                    }
                    for (Song song : tableItems) {
                        bTree.insert(song);
                    }
                    DoublyLinkedList<Song> allSongs = bTree.getAllKeys();
                    updateTable(table, allSongs);
                }
            }
        });

        // Load and resize icons
        Image searchIcon = new Image(new FileInputStream("src/main/resources/icons/search_icon.png"), 20, 20, true, true);
        Image addIcon = new Image(new FileInputStream("src/main/resources/icons/add_icon.png"), 20, 20, true, true);
        Image updateIcon = new Image(new FileInputStream("src/main/resources/icons/update_icon.png"), 20, 20, true, true);
        Image deleteIcon = new Image(new FileInputStream("src/main/resources/icons/delete_icon.png"), 20, 20, true, true);

        Button searchButton = new Button();
        searchButton.setGraphic(new ImageView(searchIcon));
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                String searchText = textField.getText().trim();
                DoublyLinkedList<Song> searchResults = new DoublyLinkedList<>();
                if (searchText.isEmpty()) {
                    String valores = comboBox.getValue();
                    orderingSearch(table, songs, valores);
                } else {
                    if (activateEnter) {
                        searchResults = music.search(searchText);
                        if (searchResults == null) {
                            searchResults.add(new Song("No hay música disponible", "", "", "", ""));
                        }
                    } else {
                        searchResults = music.searchByPrefix(searchText);
                        if (searchResults.isEmpty()) {
                            // Agrega un mensaje de que no hay música disponible
                            searchResults.add(new Song("No hay música disponible", "", "", "", ""));
                        }
                    }
                    String valores = comboBox.getValue();
                    orderingSearch(table, searchResults, valores);
                }
            }
        });

        Button addMusicButton = new Button();
        addMusicButton.setGraphic(new ImageView(addIcon));
        addMusicButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showAddMusicDialog(primaryStage, music);
            }
        });

        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    activateEnter = true;
                } else if (event.getCode() != KeyCode.SHIFT && event.getCode() != KeyCode.CONTROL &&
                        event.getCode() != KeyCode.ALT && event.getCode() != KeyCode.META) {
                    activateEnter = false;
                }
                searchButton.fire();
            }
        });

        TableColumn<Song, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Song, String> artistColumn = new TableColumn<>("Artist");
        TableColumn<Song, String> popularityColumn = new TableColumn<>("Popularity");
        TableColumn<Song, String> yearColumn = new TableColumn<>("Year");
        TableColumn<Song, String> idColumn = new TableColumn<>("ID");

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name_track"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist_track"));
        popularityColumn.setCellValueFactory(new PropertyValueFactory<>("popularity"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_track"));

        TableColumn<Song, Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellFactory(param -> new TableCell<Song, Void>() {
            private final Button deleteButton = new Button();

            {
                deleteButton.setGraphic(new ImageView(deleteIcon));
                deleteButton.setOnAction(event -> {
                    Song song = getTableView().getItems().get(getIndex());
                    table.getItems().remove(song);
                    music.delete(song);
                    System.out.println("Deleted: " + song.getName_track());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        TableColumn<Song, Void> updateColumn = new TableColumn<>("Update");
        updateColumn.setCellFactory(param -> new TableCell<Song, Void>() {
            private final Button updateButton = new Button();

            {
                updateButton.setGraphic(new ImageView(updateIcon));
                updateButton.setOnAction(event -> {
                    Song song = getTableView().getItems().get(getIndex());
                    showUpdateMusicDialog((Stage) getTableView().getScene().getWindow(), music, song);
                    orderingSearch(table, songs, comboBox.getValue());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });

        table.getColumns().addAll(nameColumn, artistColumn, popularityColumn, yearColumn, idColumn, deleteColumn, updateColumn);
        updateTable(table, songs);
        table.setId("table");
        table.setRowFactory(tv -> {
            TableRow<Song> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(row.getItem().getName_track());
                    db.setContent(content);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                if (event.getGestureSource() != row && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    Song draggedSong = findSongByName(db.getString(), music);
                    if (draggedSong != null) {
                        table.getItems().remove(draggedSong);
                        int index = row.getIndex();
                        if (index >= 0) {
                            table.getItems().add(index, draggedSong);
                        } else {
                            table.getItems().add(draggedSong); // Add to the end if index is invalid
                        }
                        success = true;
                        updateTable(table, convertToDoublyLinkedList(table.getItems())); // Ensure styles are updated
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            });

            row.setOnDragDone(event -> {
                if (event.getTransferMode() == TransferMode.MOVE) {
                    // Maneja lo que ocurre después de que el arrastre se completa
                }
                event.consume();
            });

            row.setOnMouseEntered(event -> {
                if (row.isEmpty()) {
                    row.setStyle("");
                } else {
                    row.setStyle("-fx-background-color: lightblue; -fx-text-fill: #000000;"); // Apply any style you want to keep during the drag
                }
            });

            row.setOnMouseExited(event -> {
                if (row.isEmpty()) {
                    row.setStyle("");
                }
            });

            return row;
        });
        Button grapho = new Button("Colaboraciones");
        grapho.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showCollaborationDialog(primaryStage, music);
            }
        });
        HBox buttonBox = new HBox();
        buttonBox.getStyleClass().add("button-container"); // Aplica el estilo CSS
        buttonBox.getChildren().addAll(textField,searchButton, addMusicButton);
        comboBox.getStyleClass().add("combo-box");
        // Crear el VBox para el resto de los componentes
        VBox root = new VBox();
        root.getChildren().addAll(buttonBox, comboBox, table, grapho);
        
        searchButton.getStyleClass().add("button");
        addMusicButton.getStyleClass().add("button");

        // Aplica la clase CSS al TableView y sus columnasDeath Waiver
        table.getStyleClass().add("table-view");

        // Aplica la clase CSS a los contenedores
        buttonBox.getStyleClass().add("hbox");
        root.getStyleClass().add("vbox");
        searchButton.getStyleClass().add("search-button"); // Aplica el estilo CSS

        addMusicButton.getStyleClass().add("add-button"); 
        // Aplica la clase CSS a los elementos de imagen
        searchButton.setGraphic(new ImageView(searchIcon) {{
            getStyleClass().add("image-view");
        }});
        comboBox.getStyleClass().add("combo-box");
        // Cambiar el tamaño de la ventana
        String css = new File("src/main/resources/styles.css").toURI().toURL().toExternalForm();
        Scene scene = new Scene(root, 800, 600); // Cambia el tamaño aquí
        scene.getStylesheets().add(css);

        primaryStage.setTitle("Music Player");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void updateTable(TableView<Song> table, DoublyLinkedList<Song> songs) {
        table.getItems().clear();
        Node<Song> current = songs.getHead();
        while (current != null) {
            table.getItems().add(current.getData());
            current = current.getNext();
        }
    }
    private void printSearchResults(DoublyLinkedList<Song> searchResults) {
        if (searchResults == null || searchResults.getHead() == null) {
            System.out.println("No search results found.");
            return;
        }
        Node<Song> current = searchResults.getHead();
        while (current != null) {
            System.out.println(current.getData());
            current = current.getNext();
        }
    }
    private void printTableItems(TableView<Song> table) {
        // Obtiene la lista observable de elementos actuales en la tabla
        ObservableList<Song> items = table.getItems();

        // Itera sobre la lista y imprime cada elemento
        for (Song song : items) {
            System.out.println(song);
        }
    }
    public DoublyLinkedList<Song> convertToDoublyLinkedList(ObservableList<Song> observableList) {
        DoublyLinkedList<Song> doublyLinkedList = new DoublyLinkedList<>();
        for (Song song : observableList) {
            doublyLinkedList.add(song); // Asumiendo que DoublyLinkedList tiene un método add
        }
        return doublyLinkedList;
    }
    
    public void orderingSearch(TableView<Song> table, DoublyLinkedList<Song> searchResults, String valores){
        BTree bTree;
        ObservableList<Song> tableItems = table.getItems();
        if (valores != null) {
            switch (valores) {
                case "Artist ->":
                    bTree = new BTree(4, SongComparate.byArtist());
                    break;
                case "Name ->":
                    bTree = new BTree(4, SongComparate.byName());
                    break;
                case "Popularity <-":
                    bTree = new BTree(4, SongComparate.byPopularity());
                    break;
                case "Year <-":
                    bTree = new BTree(4, SongComparate.byYear());
                    break;
                case "Artist <-":
                    bTree = new BTree(4, SongComparate.byArtistDescending());
                    break;
                case "Name <-":
                    bTree = new BTree(4, SongComparate.byNameDescending());
                    break;
                case "Popularity ->":
                    bTree = new BTree(4, SongComparate.byPopularityAscending());
                    break;
                case "Year ->":
                    bTree = new BTree(4, SongComparate.byYearAscending());
                    break;
                default:
                    System.out.println("Unknown option selected");
                    return;
                }
            Node<Song> current = searchResults.getHead();
            while (current != null) {
                Song song = current.getData();
                bTree.insert(song);
                current = current.getNext();
            }
            DoublyLinkedList<Song> allSongs = bTree.getAllKeys();
            updateTable(table, allSongs);
        } else {
            System.out.println("No option selected in ComboBox");
            updateTable(table, searchResults);
        }
        
    }
    
    private void showAddMusicDialog(Stage owner, Trie music) {
        Dialog<Song> dialog = new Dialog<>();
        dialog.initOwner(owner);
        dialog.setTitle("Add New Music");

        VBox vbox = new VBox();
        Label name_track = new Label("Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Song Name");
        Label artist_track = new Label("Artist:");
        TextField artistField = new TextField();
        artistField.setPromptText("Artist");
        Label popularity_track = new Label("Popularity:");
        TextField popularityField = new TextField();
        popularityField.setPromptText("Popularity");
        Label year_track = new Label("Year:");
        TextField yearField = new TextField();
        yearField.setPromptText("Year");
        Label id_track = new Label("ID:");
        TextField idField = new TextField();
        idField.setPromptText("ID");
        

        vbox.getChildren().addAll(name_track, nameField, artist_track, artistField, popularity_track, popularityField, year_track, yearField, id_track, idField);

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Song(
                    nameField.getText(),
                    idField.getText(),
                    artistField.getText(),
                    popularityField.getText(),
                    yearField.getText()
                );
            }
            return null;
        });

        Optional<Song> result = dialog.showAndWait();
        result.ifPresent(song -> {
            music.insert(song.getName_track(), song);
            updateTable((TableView<Song>) owner.getScene().lookup("#table"), music.getCombinacion());
        });
    }
    private void showUpdateMusicDialog(Stage owner, Trie music, Song song){
        Dialog<Song> dialog = new Dialog<>();
        dialog.initOwner(owner);
        dialog.setTitle("Update Music");

        VBox vbox = new VBox();
        Label name_track = new Label("Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Song Name");
        nameField.setText(song.getName_track());
        Label artist_track = new Label("Artist:");
        TextField artistField = new TextField();
        artistField.setPromptText("Artist");
        artistField.setText(song.getArtist_track());
        Label popularity_track = new Label("Popularity:");
        TextField popularityField = new TextField();
        popularityField.setPromptText("Popularity");
        popularityField.setText(song.getPopularity());
        Label year_track = new Label("Year:");
        TextField yearField = new TextField();
        yearField.setPromptText("Year");
        yearField.setText(song.getYear());
        Label id_track = new Label("ID:");
        TextField idField = new TextField();
        idField.setPromptText("ID");
        idField.setText(song.getId_track());
        
        vbox.getChildren().addAll(name_track, nameField, artist_track, artistField, popularity_track, popularityField, year_track, yearField, id_track, idField);

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(vbox);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Song(
                    nameField.getText(),
                    idField.getText(),
                    artistField.getText(),
                    popularityField.getText(),
                    yearField.getText()
                );
            }
            return null;
        });

        Optional<Song> result = dialog.showAndWait();
        result.ifPresent(updatedSong -> {
            music.update(song, updatedSong); 
            updateTable((TableView<Song>) owner.getScene().lookup("#table"), music.getCombinacion());
        });
    }
    private Song findSongByName(String name, Trie music) {
        // Buscar en el Trie
        DoublyLinkedList<Song> searchResults = music.search(name);
        if (searchResults != null) {
            Node<Song> current = searchResults.getHead();
            while (current != null) {
                Song song = current.getData();
                if (song.getName_track().equals(name)) {
                    return song;
                }
                current = current.getNext();
            }
        }
        return null;
    }
    private void showCollaborationDialog(Stage primaryStage, Trie music) {
        Stage collaborationStage = new Stage();
        collaborationStage.setTitle("Agregar Colaboración");

        // Crear etiquetas para los campos
        Label artistLabel = new Label("Artista:");
        Label songLabel = new Label("Canción:");
        Label yearLabel = new Label("Año:");
        Label popularityLabel = new Label("Popularidad:");
        Label idLabel = new Label("ID:");

        // Crear campos de texto
        TextField artistField = new TextField();
        artistField.setPromptText("Agregar Artista");

        TextField songField = new TextField();
        songField.setPromptText("Agregar Canción");

        TextField yearField = new TextField();
        yearField.setPromptText("Agregar Año");

        TextField popularityField = new TextField();
        popularityField.setPromptText("Agregar Popularidad");

        TextField idField = new TextField();
        idField.setPromptText("Agregar ID");

        // Lista doblemente ligada para colaboraciones
        DoublyLinkedList<String> collaborations = new DoublyLinkedList<>();

        // Botón para agregar artista
        Button addArtistButton = new Button("Agregar Artista");
        addArtistButton.setOnAction(event -> {
            String artist = artistField.getText().trim();
            if (!artist.isEmpty()) {
                collaborations.add(artist);
                artistField.clear();
            }
        });

        // Botón para guardar
        Button saveButton = new Button("Guardar");
        saveButton.setOnAction(event -> {
            // Obtener los datos de los campos de texto
            String artistas = "";
            String song = songField.getText().trim();
            String year = yearField.getText().trim();
            String popularity = popularityField.getText().trim();
            String id = idField.getText().trim();
            Node<String> current = collaborations.getHead();
            while (current != null) {
                artistas += current.getData() + ", ";
                current = current.getNext();
            }
            // Verificar que todos los campos estén llenos
            if (artistas.isEmpty() || song.isEmpty() || year.isEmpty() || popularity.isEmpty() || id.isEmpty()) {
                // Puedes mostrar una alerta si alguno de los campos está vacío
                new Alert(Alert.AlertType.WARNING, "Por favor, completa todos los campos.").showAndWait();
            } else {
                // Imprimir los datos en la consola (o procesarlos de otra manera)
                System.out.print("Artistas: " + artistas);
                System.out.println("Canción: " + song);
                System.out.println("Año: " + year);
                System.out.println("Popularidad: " + popularity);
                System.out.println("ID: " + id);
                music.insert(song, new Song(song, id, artistas, popularity, year));
                // Cerrar el diálogo
                collaborationStage.close();
            }
        });

        // Botón para cancelar
        Button cancelButton = new Button("Cancelar");
        cancelButton.setOnAction(event -> collaborationStage.close());

        // Botón para hacer gráfico
        Button graphButton = new Button("Hacer Gráfico");
        graphButton.setOnAction(event -> {
            MyGraph graph = new MyGraph();
            if (collaborations.getHead() == null) {
                System.out.println("No search results found.");
                return;
            }
            String song = songField.getText();
            graph.addNode("Song " + song);
            Node<String> current = collaborations.getHead();
            while (current != null) {
                String artist = current.getData();
                graph.addNode("Artist " + artist);
                graph.addEdge("Song " + song, "Artist " + artist);
                current = current.getNext();
            }
            graph.printGraph();

            // Crear y configurar el gráfico
            Platform.runLater(() -> {
                new Thread(() -> {
                    Graph graphStream = graph.toGraphStream();
                    System.setProperty("org.graphstream.ui", "swing");
                    graphStream.display();
                }).start();
            });
        });

        // Crear y configurar el diseño
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        // Diseños para los campos de entrada
        HBox artistLayout = new HBox(10);
        artistLayout.getChildren().addAll(artistLabel, artistField, addArtistButton);
        artistLayout.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        HBox songLayout = new HBox(10);
        songLayout.getChildren().addAll(songLabel, songField);
        songLayout.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        HBox yearLayout = new HBox(10);
        yearLayout.getChildren().addAll(yearLabel, yearField);
        yearLayout.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        HBox popularityLayout = new HBox(10);
        popularityLayout.getChildren().addAll(popularityLabel, popularityField);
        popularityLayout.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        HBox idLayout = new HBox(10);
        idLayout.getChildren().addAll(idLabel, idField);
        idLayout.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        // Botones en un HBox y centrado
        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(saveButton, cancelButton, graphButton);
        buttonLayout.setAlignment(javafx.geometry.Pos.CENTER);

        // Añadir todos los elementos al diseño principal
        layout.getChildren().addAll(artistLayout, songLayout, yearLayout, popularityLayout, idLayout, buttonLayout);

        // Aplicar estilos
        layout.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 15;");
        artistField.setStyle("-fx-border-color: #aaa; -fx-border-radius: 5;");
        songField.setStyle("-fx-border-color: #aaa; -fx-border-radius: 5;");
        yearField.setStyle("-fx-border-color: #aaa; -fx-border-radius: 5;");
        popularityField.setStyle("-fx-border-color: #aaa; -fx-border-radius: 5;");
        idField.setStyle("-fx-border-color: #aaa; -fx-border-radius: 5;");
        addArtistButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        saveButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        graphButton.setStyle("-fx-background-color: #FFC107; -fx-text-fill: white;");

        // Crear la escena y mostrarla
        Scene scene = new Scene(layout, 350, 250);
        collaborationStage.setScene(scene);
        collaborationStage.initOwner(primaryStage);
        collaborationStage.initModality(Modality.WINDOW_MODAL);
        collaborationStage.showAndWait();
    }


}

  

