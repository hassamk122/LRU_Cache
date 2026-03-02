package lruCache;

public class DoublyLinkedList <T>{
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private final LinkedListNode<T> dummyNode;
    private int size;

    public DoublyLinkedList() {
        this.dummyNode = new LinkedListNode<T>();
        this.tail = null;
        this.head = null;
        this.size = 0;
    }

    public LinkedListNode<T> add(T element){
        LinkedListNode<T> newNode = new LinkedListNode<T>(element, this);

        if (this.head == null){
            this.head = newNode;
            this.tail = newNode;
        }else {
            newNode.setNext(head);
            this.head.setPrev(newNode);
            this.head = newNode;
        }

        this.size++;
        return newNode;
    }

    public void detach(LinkedListNode<T> node){

        if (node.getPrev() != null){
            node.getPrev().setNext(node.getNext());
        }else{
            this.head = node.getNext();
        }

        if (node.getNext() != null){
            node.getNext().setPrev(node.getPrev());
        }else {
            this.tail = node.getPrev();
        }

        node.setPrev(null);
        node.setNext(null);

        this.size--;
    }


    public LinkedListNode<T> updateAndMoveToFront(LinkedListNode<T> node, T newElement){
        if (node.isEmpty() || (this != node.getListReference())){
            return dummyNode;
        }

        node.setElement(newElement);
        return this.moveToFront(node);
    }

    public LinkedListNode<T> moveToFront(LinkedListNode<T> node){
        if (node.isEmpty() || node == this.head){
            return node;
        }

        if (node.getPrev() != null){
            node.getPrev().setNext(node.getNext());
        }

        if (node.getNext() != null){
            node.getNext().setPrev(node.getPrev());
        }

        if (node == this.tail){
            this.tail = node.getPrev();
        }

        node.setPrev(null);
        node.setNext(this.head);

        if (this.head == null){
            this.head.setPrev(node);
        }

        this.head = node;

        return node;
    }


    public LinkedListNode<T> removeLast(){
        if (this.tail == null){
            return dummyNode;
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

    public LinkedListNode<T> getDummyNode() {
        return dummyNode;
    }

}
