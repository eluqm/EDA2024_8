
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

   
}

