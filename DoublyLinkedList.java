public class DoublyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    // Método para agregar un elemento al final
    public void add(T data) {
        Node<T> newNode = new Node<>(data, null, tail);
        if (tail != null) {
            tail.setNext(newNode);
        }
        tail = newNode;
        if (head == null) {
            head = newNode;
        }
        size++;
    }

    // Método para eliminar un elemento
    public void remove(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.getData().equals(data)) {
                if (current.getPrev() != null) {
                    current.getPrev().setNext(current.getNext());
                } else {
                    head = current.getNext();
                }
                if (current.getNext() != null) {
                    current.getNext().setPrev(current.getPrev());
                } else {
                    tail = current.getPrev();
                }
                size--;
                return;
            }
            current = current.getNext();
        }
    }

    // Método para obtener el tamaño de la lista
    public int size() {
        return size;
    }

    // Método para obtener un nodo en una posición específica
    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index fuera de rango: " + index);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current;
    }

    // Método para obtener un elemento en una posición específica
    public T get(int index) {
        return getNode(index).getData();
    }

    // Método para obtener el primer nodo (head) de la lista
    public Node<T> getHead() {
        return head;
    }

    private Node<T> cloneNodes(Node<T> head) {
        Node<T> current = head;
        Node<T> newHead = null;
        Node<T> newTail = null;
        
        while (current != null) {
            Node<T> newNode = new Node<>(current.getData(), null, newTail);
            if (newHead == null) {
                newHead = newNode;
            }
            if (newTail != null) {
                newTail.setNext(newNode);
            }
            newTail = newNode;
            current = current.getNext();
        }
        return newHead;
    }

    private Node<T> findTail(Node<T> head) {
        Node<T> current = head;
        while (current != null && current.getNext() != null) {
            current = current.getNext();
        }
        return current;
    }
    
    public void concatenate(DoublyLinkedList<T> otherList) {
        if (otherList.head == null) {
            return; // La lista a concatenar está vacía
        }
        
        if (this.head == null) {
            // La lista actual está vacía, simplemente asigna la otra lista
            this.head = cloneNodes(otherList.head);
            this.tail = findTail(this.head);
        }
        this.size += otherList.size;
    }
    // Método para mostrar la lista
    public void display() {
        Node<T> current = head;
        while (current != null) {
            System.out.println(current.getData());
            current = current.getNext();
        }
    }
}
