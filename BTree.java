import java.util.*;
public class BTree {
    private BTreeNode root;
    private int t;  // Minimum degree
    private Comparator<Song> comparator;

    public BTree(int t, Comparator<Song> comparator) {
        this.root = new BTreeNode(true);
        this.t = t;
        this.comparator = comparator;
    }
}
