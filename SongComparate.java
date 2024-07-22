/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reproductormusic;
import java.util.Comparator;
/**
 *
 * @author Usuario
 */
public class SongComparate {
    public static Comparator<Song> byArtist(){
        return Comparator.comparing(Song::getArtist_track);
    }
}
