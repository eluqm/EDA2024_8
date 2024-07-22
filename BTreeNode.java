import java.util.ArrayList;
import java.util.List;

public class BTreeNode {
    List<Song> keys;
    List<BTreeNode> children;
    boolean isLeaf;

    BTreeNode(boolean isLeaf) {
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.isLeaf = isLeaf;
        
    }
}