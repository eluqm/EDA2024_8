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
    public static Comparator<Song> byName(){
        return Comparator.comparing(Song::getName_track);
    }
    public static Comparator<Song> byPopularity(){
        return (s1, s2) -> {
            try {
                int pop1 = Integer.parseInt(s1.getPopularity());
                int pop2 = Integer.parseInt(s2.getPopularity());
                return Integer.compare(pop2, pop1);
            } catch (NumberFormatException e) {
                return s2.getPopularity().compareTo(s1.getPopularity());
            }
        };
    }
}
