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
        
}



