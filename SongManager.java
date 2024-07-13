import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SongManager {
    private HashMap<String, List<Song>> artistMap;
    private HashMap<String, List<Song>> songNameMap;

    public SongManager() {
        artistMap = new HashMap<>();
        songNameMap = new HashMap<>();
    }

    public void loadSongsFromCSV(String filePath) {
        String line;
        String delimiter = ","; // Delimitador CSV

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Leer la cabecera
            br.readLine(); // Ignorar la primera línea (encabezados)

            while ((line = br.readLine()) != null) {
                String[] attributes = line.split(delimiter);

                // Usar los índices correctos
                String number = attributes[0];
                String artistName = attributes[1]; // Artista
                String trackName = attributes[2]; // Nombre de la canción
                String trackId = attributes[3]; // ID de la canción
                String popularity = attributes[4];
                String year = attributes[5]; // Año
                String genre = attributes[6]; // Género
                String danceability = attributes[7]; // Danceability
                String energy = attributes[8]; // Energía
                String key = attributes[9]; // Clave
                String loudness = attributes[10]; // Sonoridad
                String mode = attributes[11]; // Modo
                String speechiness = attributes[12]; // Habladuría
                String acousticness = attributes[13]; // Acústica
                String instrumentalness = attributes[14]; // Instrumentalidad
                String liveness = attributes[15]; // Vivacidad
                String valence = attributes[16]; // Valencia
                String tempo = attributes[17]; // Tempo
                String durationMs = attributes[18]; // Duración en milisegundos
                String timeSignature = attributes[19]; // Firma de tiempo

                // Crear un nuevo objeto Song
                Song song = new Song(number, artistName, trackName, trackId, popularity, year, genre,
                        danceability, energy, key, loudness, mode,
                        speechiness, acousticness, instrumentalness,
                        liveness, valence, tempo, durationMs, timeSignature);

                // Normalizar las claves a minúsculas
                artistName = artistName.toLowerCase();
                trackName = trackName.toLowerCase();

                // Agregar la canción al HashMap por nombre de artista
                if (!artistMap.containsKey(artistName)) {
                    artistMap.put(artistName, new ArrayList<>());
                }
                artistMap.get(artistName).add(song);

                // Agregar la canción al HashMap por nombre de canción
                if (!songNameMap.containsKey(trackName)) {
                    songNameMap.put(trackName, new ArrayList<>());
                }
                songNameMap.get(trackName).add(song);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error de formato en los datos: " + e.getMessage());
        }
    }

    public List<Song> getSongsByArtist(String artistName) {
        return artistMap.getOrDefault(artistName.toLowerCase(), new ArrayList<>());
    }

    public List<Song> getSongsBySongName(String songName) {
        return songNameMap.getOrDefault(songName.toLowerCase(), new ArrayList<>());
    }

    public List<Song> getAllSongs() {
        List<Song> allSongs = new ArrayList<>();
        for (List<Song> songs : artistMap.values()) {
            allSongs.addAll(songs);
        }
        return allSongs;
    }

    public int getSize() {
        return artistMap.values().size();
    }

}
