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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(name_track, song.name_track) &&
               Objects.equals(id_track, song.id_track) &&
               Objects.equals(artist_track, song.artist_track) &&
               Objects.equals(popularity, song.popularity) &&
               Objects.equals(year, song.year);
    }
	@Override
    public int hashCode() {
        return Objects.hash(name_track, id_track, artist_track, popularity, year);
    }
    
    public String toString(){
        return "Nombre: " + getName_track() + ", Artista: " + getArtist_track() + ", ID: " + getId_track() + ", Popularity: " + getPopularity() + ", year: " + getYear();
    }
}