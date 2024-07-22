import java.util.*;
import java.util.function.BiFunction;

public class Trie {
    private final TrieNode root;
    private DoublyLinkedList<Song> combinacion = new DoublyLinkedList<>();

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String title, Song songInfo) {
        TrieNode node = root;
        for (char c : title.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }
        node.isEndOfSong = true;
        node.songInfoList.add(songInfo);
    }

    public DoublyLinkedList<Song> search(String title) {
        TrieNode node = root;
        for (char c : title.toCharArray()) {
            node = node.children.get(c);
            if (node == null) {
                return null;
            }
        }
        return node.isEndOfSong ? node.songInfoList : null;
    }

    public DoublyLinkedList<Song> searchByPrefix(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            node = node.children.get(c);
            if (node == null) {
                return new DoublyLinkedList<>(); // Retorna lista vacía si el prefijo no se encuentra
            }
        }
        DoublyLinkedList<Song> results = new DoublyLinkedList<>();
        collectAllSongs(node, results);
        return results;
    }

    private void collectAllSongs(TrieNode node, DoublyLinkedList<Song> results) {
        if (node == null) {
            return;
        }

        if (node.isEndOfSong) {
            results.concatenate(node.songInfoList);
        }

        for (TrieNode child : node.children.values()) {
            collectAllSongs(child, results);
        }
    }

    public void delete(Song song) {
        delete(root, song, 0);
    }

    private boolean delete(TrieNode node, Song songToRemove, int index) {
        if (index == songToRemove.getName_track().length()) {
            if (!node.isEndOfSong) {
                return false; // No song exists at this node
            }
            // Eliminar solo la canción con el ID específico
            node.songInfoList.removeIf(song -> song.getId_track().equals(songToRemove.getId_track()) &&
                    song.getName_track().equals(songToRemove.getName_track()) &&
                    song.getArtist_track().equals(songToRemove.getArtist_track()) &&
                    song.getPopularity().equals(songToRemove.getPopularity()) &&
                    song.getYear().equals(songToRemove.getYear()));
            if (node.songInfoList.isEmpty()) {
                node.isEndOfSong = false;
            }
            return node.children.isEmpty() && !node.isEndOfSong;
        }

        char c = songToRemove.getName_track().charAt(index);
        TrieNode childNode = node.children.get(c);
        if (childNode == null) {
            return false; // No such song exists
        }

        boolean shouldDeleteChild = delete(childNode, songToRemove, index + 1);

        if (shouldDeleteChild) {
            node.children.remove(c);
            return node.children.isEmpty() && !node.isEndOfSong;
        }

        return false;
    }
}
