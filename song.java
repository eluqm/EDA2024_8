import java.util.Objects;
public class Song {
    String name_track;
    String id_track;
    String artist_track;
    String popularity;
    String year;
    

    public Song(String name, String id, String artist, String p, String y){            
        this.id_track = id;
        this.name_track = name;
        this.artist_track = artist;
        this.popularity = p;
        this.year = y;
    }
}