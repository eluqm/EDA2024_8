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

    private void splitChild(BTreeNode x, int i) {
        BTreeNode y = x.children.get(i);
        BTreeNode z = new BTreeNode(y.isLeaf);
        for (int j = 0; j < t - 1; j++) {
            z.keys.add(y.keys.remove(t));
        }
        if (!y.isLeaf) {
            for (int j = 0; j < t; j++) {
                z.children.add(y.children.remove(t));
            }
        }
        x.children.add(i + 1, z);
        x.keys.add(i, y.keys.remove(t - 1));
    }
   
}
