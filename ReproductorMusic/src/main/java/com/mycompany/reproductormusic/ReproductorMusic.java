/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.reproductormusic;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.util.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author Usuario
 */
public class ReproductorMusic {


    public static void main(String[] args) {
        Interfaz.launch(Interfaz.class, args);
        /*String csvFile = "src/resources/spotify_data.csv";
        Trie music = new Trie();
        try(CSVReader reader = new CSVReader(new FileReader(csvFile))){
            String line[];
            int count = 0;
            while ((line = reader.readNext()) != null && count <= 1000){
                Song cancion = new Song(line[2], line[3], line[1], line[4], line[5]);
                music.insert(cancion.getName_track(), cancion);
                count++;
            }
        } catch (IOException | CsvValidationException e){
                e.printStackTrace();
        } 
        //DoublyLinkedList<Song> songs = music.getCombinacion();
        DoublyLinkedList<Song> searchResults = music.search("Home");
        printSearchResults(searchResults);
        Interfaz.launch(Interfaz.class, args);
        /*BTree bTree = new BTree(4, SongComparate.byYear());
        bTree.insert(new Song("Song A","", "Artist A", "90", "2020"));
        bTree.insert(new Song("Song B","", "Artist B", "85", "2019"));
        bTree.insert(new Song("Song C","", "Artist C", "95", "2021"));
        bTree.insert(new Song("Song D", "", "Dado", "80", "2018"));
        bTree.insert(new Song("Song E","", "America", "88", "2017"));
        DoublyLinkedList<Song> allSongs = bTree.getAllKeys();
        allSongs.display();
        //bTree.print();*/
    }
}
