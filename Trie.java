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
}
