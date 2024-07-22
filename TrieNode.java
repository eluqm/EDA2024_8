import java.util.*;

public class TrieNode {
    Map<Character, TrieNode> children;
    boolean isEndOfSong;
    DoublyLinkedList<Song> songInfoList;
    
    public TrieNode(){
        children = new HashMap<>();
        isEndOfSong = false;
        songInfoList = new DoublyLinkedList<>();
    }
}