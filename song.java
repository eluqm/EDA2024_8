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
	 public String getId_track() {
        return id_track;
    }

    public void setId_track(String id_track) {
        this.id_track = id_track;
    }

    public String getName_track() {
        return name_track;
    }

    public void setName_track(String name_track) {
        this.name_track = name_track;
    }

    public String getArtist_track() {
        return artist_track;
    }

    public void setArtist_track(String artist_track) {
        this.artist_track = artist_track;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    
}