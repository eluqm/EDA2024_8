import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Playlist {
    private String name;
    private DoublyLinkedList<Song> songs;

    public Playlist(String name) {
        this.name = name;
        songs = new DoublyLinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void addSongFromManager(SongManager songManager, String searchType, String searchTerm) {
        List<Song> searchResult = new ArrayList<>();

        if (searchType.equalsIgnoreCase("artist")) {
            searchResult = songManager.getSongsByArtist(searchTerm);
        } else if (searchType.equalsIgnoreCase("song")) {
            searchResult = songManager.getSongsBySongName(searchTerm);
        }

        if (searchResult.isEmpty()) {
            System.out.println("No se encontraron canciones para el termino de busqueda proporcionado.");
            return;
        }

        System.out.println("Canciones encontradas:");
        int index = 1;
        for (Song song : searchResult) {
            System.out.println(index + ". " + song.getArtistName() + " - " + song.getTrackName());
            index++;
        }

        // Permitir al usuario elegir que cancion agregar
        int choice = getUserChoice(searchResult.size());
        if (choice == -1) {
            System.out.println("Seleccion invalida.");
            return;
        }

        Song selectedSong = searchResult.get(choice - 1);
        addSong(selectedSong);
    }

    private int getUserChoice(int maxChoice) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el numero de la cancion que desea agregar: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice < 1 || choice > maxChoice) {
                return -1; // Seleccion invalida
            }
            return choice;
        } catch (NumberFormatException e) {
            return -1; // Seleccion invalida
        }
    }

    public void addSong(Song song) {
        songs.add(song);
    }

}
