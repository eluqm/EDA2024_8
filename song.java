public class Song {
    String number;
    String artistName; // Artista
    String trackName; // Nombre de la canción
    String trackId; // ID de la canción
    String popularity; // Popularidad
    String year; // Año
    String genre; // Género
    String danceability; // Danceability
    String energy; // Energía
    String key; // Clave
    String loudness; // Sonoridad
    String mode; // Modo
    String speechiness; // Habladuría
    String acousticness; // Acústica
    String instrumentalness; // Instrumentalidad
    String liveness; // Vivacidad
    String valence; // Valencia
    String tempo; // Tempo
    String durationMs; // Duración en milisegundos
    String timeSignature; // Firma de tiempo

    public Song(String number, String artistName, String trackName, String trackId, String popularity, String year,
            String genre, String danceability, String energy, String key, String loudness,
            String mode, String speechiness, String acousticness, String instrumentalness,
            String liveness, String valence, String tempo, String durationMs, String timeSignature) {
        this.number = number;
        this.artistName = artistName;
        this.trackName = trackName;
        this.trackId = trackId;
        this.popularity = popularity;
        this.year = year;
        this.genre = genre;
        this.danceability = danceability;
        this.energy = energy;
        this.key = key;
        this.loudness = loudness;
        this.mode = mode;
        this.speechiness = speechiness;
        this.acousticness = acousticness;
        this.instrumentalness = instrumentalness;
        this.liveness = liveness;
        this.valence = valence;
        this.tempo = tempo;
        this.durationMs = durationMs;
        this.timeSignature = timeSignature;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getTrackName() {
        return trackName;
    }

    @Override
    public String toString() {
        return number + "," +
                artistName + "," +
                trackName + "," +
                trackId + "," +
                popularity + "," +
                year + "," +
                genre + "," +
                danceability + "," +
                energy + "," +
                key + "," +
                loudness + "," +
                mode + "," +
                speechiness + "," +
                acousticness + "," +
                instrumentalness + "," +
                liveness + "," +
                valence + "," +
                tempo + "," +
                durationMs + "," +
                timeSignature;
    }
}