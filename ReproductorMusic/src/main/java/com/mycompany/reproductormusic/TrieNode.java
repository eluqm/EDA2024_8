/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reproductormusic;

import java.util.*;

/**
 *
 * @author Usuario
 */
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
