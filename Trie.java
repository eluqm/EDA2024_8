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
}
