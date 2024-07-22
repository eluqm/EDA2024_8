/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reproductormusic;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
/**
 *
 * @author Usuario
 */
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
