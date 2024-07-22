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
            while ((line = reader.readNext()) != null && count <= 50000) {
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
            "Artist",
            "Name",
            "Popularity",
            "Year"
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
                        case "Artist":
                            bTree = new BTree(4, SongComparate.byArtist());
                            break;
                        case "Name":
                            bTree = new BTree(4, SongComparate.byName());
                            break;
                        case "Popularity":
                            bTree = new BTree(4, SongComparate.byPopularity());
                            break;
                        case "Year":
                            bTree = new BTree(4, SongComparate.byYear());
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
                if (searchText.isEmpty()) {
                    String valores = comboBox.getValue();
                    orderingSearch(table, songs, valores);
                } else {
                    DoublyLinkedList<Song> searchResults = null;
                    if (activateEnter) {
                        searchResults = music.search(searchText);
                    } else {
                        searchResults = music.searchByPrefix(searchText);
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
        HBox buttonBox = new HBox();
        buttonBox.getStyleClass().add("button-container"); // Aplica el estilo CSS
        buttonBox.getChildren().addAll(textField,searchButton, addMusicButton);
        comboBox.getStyleClass().add("combo-box");
        // Crear el VBox para el resto de los componentes
        VBox root = new VBox();
        root.getChildren().addAll(buttonBox, comboBox, table);
        
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
    
}



