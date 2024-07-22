/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reproductormusic;

/**
 *
 * @author Usuario
 */
public class Node<T> {
    private T data;
    private Node<T> next;
    private Node<T> prev; 

    public Node(T data, Node<T> next, Node<T> prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getPrev() {
        return prev; 
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev; 
    }

    @Override
    public String toString() {
        return this.data.toString();
    }
}
