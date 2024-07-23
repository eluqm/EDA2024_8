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
    public static Comparator<Song> byYear(){
        return (s1, s2) -> {
            try {
                int year1 = Integer.parseInt(s1.getYear());
                int year2 = Integer.parseInt(s2.getYear());
                return Integer.compare(year2, year1);
            } catch (NumberFormatException e) {
                return s2.getYear().compareTo(s1.getYear());
            }
        };
    }

    public static Comparator<Song> byArtistDescending() {
        return Comparator.comparing(Song::getArtist_track).reversed();
    }

    public static Comparator<Song> byNameDescending() {
        return Comparator.comparing(Song::getName_track).reversed();
    }

    public static Comparator<Song> byPopularityAscending() {
        return (s1, s2) -> {
            try {
                int pop1 = Integer.parseInt(s1.getPopularity());
                int pop2 = Integer.parseInt(s2.getPopularity());
                return Integer.compare(pop1, pop2); // Menor a mayor
            } catch (NumberFormatException e) {
                return s1.getPopularity().compareTo(s2.getPopularity());
            }
        };
    }

    public static Comparator<Song> byYearAscending() {
        return (s1, s2) -> {
            try {
                int year1 = Integer.parseInt(s1.getYear());
                int year2 = Integer.parseInt(s2.getYear());
                return Integer.compare(year1, year2); // Menor a mayor
            } catch (NumberFormatException e) {
                return s1.getYear().compareTo(s2.getYear());
            }
        };
    }
}
