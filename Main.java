import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SongManager songManager = new SongManager();
        songManager.loadSongsFromCSV("spotify_data.csv");
        System.out.println(songManager.getSize());

        List<Playlist> playlists = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Opciones:");
            System.out.println("1. Crear una nueva playlist");
            System.out.println("2. Agregar canciones a una playlist existente");
            System.out.println("3. Eliminar canciones a una playlist existente");
            System.out.println("4. Mostrar todas las playlists");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    System.out.print("Ingrese el nombre de la nueva playlist: ");
                    String playlistName = scanner.nextLine();
                    Playlist newPlaylist = new Playlist(playlistName);
                    playlists.add(newPlaylist);
                    managePlaylist(songManager, newPlaylist, scanner);
                    break;
                case 2:
                    if (playlists.isEmpty()) {
                        System.out.println("No hay playlists creadas.");
                        break;
                    }
                    System.out.println("Seleccione la playlist a la cual desea agregar canciones:");
                    for (int i = 0; i < playlists.size(); i++) {
                        System.out.println((i + 1) + ". " + playlists.get(i).getName());
                    }
                    int playlistChoice = Integer.parseInt(scanner.nextLine());
                    if (playlistChoice < 1 || playlistChoice > playlists.size()) {
                        System.out.println("Seleccion invalida.");
                        break;
                    }
                    Playlist selectedPlaylist = playlists.get(playlistChoice - 1);
                    managePlaylist(songManager, selectedPlaylist, scanner);
                    break;
                case 3:
                    if (playlists.isEmpty()) {
                        System.out.println("No hay playlists creadas.");
                        break;
                    }
                    System.out.println("Seleccione la playlist a la cual desea eliminar canciones:");
                    for (int i = 0; i < playlists.size(); i++) {
                        System.out.println((i + 1) + ". " + playlists.get(i).getName());
                    }
                    playlistChoice = Integer.parseInt(scanner.nextLine());
                    if (playlistChoice < 1 || playlistChoice > playlists.size()) {
                        System.out.println("Seleccion invalida.");
                        break;
                    }
                    selectedPlaylist = playlists.get(playlistChoice - 1);
                    managePlaylist(songManager, selectedPlaylist, scanner);
                    break;

                case 4:
                    if (playlists.isEmpty()) {
                        System.out.println("No hay playlists creadas.");
                    } else {
                        System.out.println("Listas de reproduccion:");
                        for (Playlist playlist : playlists) {
                            System.out.println("- " + playlist.getName());
                        }
                    }
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opcion invalida, por favor intente de nuevo.");
            }
        }
    }

}