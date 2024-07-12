public class LinkedList<T> implements ListIt<T>{
    private Node<T> root;
  
  public LinkedList(){
    this.root = null;
  }
  
  public void insertFirst(T x){
    this.root = new Node<T>(x, this.root);
  }
  public void insertLast(T x){
    if (isEmpty()){
      insertFirst(x);
      return;
    }
    Node<T> i = this.root;
    while (i.getNext() != null){
      i = getNext();
    }
    Node<T> n = new Node<T>(x, null);
    i.setNext(n);
    
  }
  
  public boolean isEmpty(){
    return root == null;
  }
}
