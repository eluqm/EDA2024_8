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

    public void insert(Song song) {
        BTreeNode r = root;
        if (r.keys.size() == 2 * t - 1) {
            BTreeNode s = new BTreeNode(false);
            s.children.add(r);
            root = s;
            splitChild(s, 0);
            insertNonFull(s, song);
        } else {
            insertNonFull(r, song);
        }
    }

    private void insertNonFull(BTreeNode x, Song k) {
        int i = x.keys.size() - 1;
        if (x.isLeaf) {
            x.keys.add(null);
            while (i >= 0 && comparator.compare(k, x.keys.get(i)) < 0) {
                x.keys.set(i + 1, x.keys.get(i));
                i--;
            }
            x.keys.set(i + 1, k);
        } else {
            while (i >= 0 && comparator.compare(k, x.keys.get(i)) < 0) {
                i--;
            }
            i++;
            if (x.children.get(i).keys.size() == 2 * t - 1) {
                splitChild(x, i);
                if (comparator.compare(k, x.keys.get(i)) > 0) {
                    i++;
                }
            }
            insertNonFull(x.children.get(i), k);
        }
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
    public void print() {
        print(root, "");
    }
    private void getAllKeys(BTreeNode node, DoublyLinkedList<Song> result) {
        if (node != null) {
            // Recorre todos los hijos del nodo actual
            for (int i = 0; i < node.keys.size(); i++) {
                if (!node.isLeaf) {
                    getAllKeys(node.children.get(i), result);
                }
                result.add(node.keys.get(i));
            }
            // Procesa el último hijo, si no es una hoja
            if (!node.isLeaf) {
                getAllKeys(node.children.get(node.children.size() - 1), result);
            }
        }
    }
}
