package lruCache;

public class DoublyLinkedList <T>{
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    public DoublyLinkedList() {
        this.tail = null;
        this.head = null;
        this.size = 0;
    }

    public LinkedListNode<T> add(T element){
        LinkedListNode<T> newNode = new LinkedListNode<>(element);

        if (head == null){
            head = newNode;
            tail = newNode;
        }else {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        }

        size++;
        return newNode;
    }

    public void detach(LinkedListNode<T> node){

        if (node.getPrev() != null){
            node.getPrev().setNext(node.getNext());
        }else{
            head = node.getNext();
        }

        if (node.getNext() != null){
            node.getNext().setPrev(node.getPrev());
        }else {
            tail = node.getPrev();
        }

        node.setPrev(null);
        node.setNext(null);

        this.size--;
    }

    public LinkedListNode<T> moveToFront(LinkedListNode<T> node){
        if (node.isEmpty() || node == head){
            return node;
        }

        if (node.getPrev() != null){
            node.getPrev().setNext(node.getNext());
        }

        if (node.getNext() != null){
            node.getNext().setPrev(node.getPrev());
        }

        if (node == tail){
            tail = node.getPrev();
        }

        node.setPrev(null);
        node.setNext(this.head);

        if (head == null){
            head.setPrev(node);
        }

        head = node;

        return node;
    }


    public LinkedListNode<T> removeLast(){
        if (tail == null){
            return null;
        }

        LinkedListNode<T> lastNode = tail;
        detach(tail);
        return lastNode;
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public LinkedListNode<T> getHead() {
        return head;
    }

    public LinkedListNode<T> getTail() {
        return tail;
    }

}
