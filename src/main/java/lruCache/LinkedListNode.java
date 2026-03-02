package lruCache;

public class LinkedListNode<T> {

    private T element;
    private LinkedListNode<T> next;
    private LinkedListNode<T> prev;
    private DoublyLinkedList<T> listReference;

    public DoublyLinkedList<T> getListReference() {
        return listReference;
    }

    public LinkedListNode(){
        this.element = null;
        this.listReference = null;
    }


    public LinkedListNode(T element, DoublyLinkedList<T> listReference) {
        this.element = element;
        this.listReference = listReference;
    }

    public T getElement() {
        return element;
    }

    public LinkedListNode<T> getNext() {
        return next;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public void setNext(LinkedListNode<T> next) {
        this.next = next;
    }

    public void setPrev(LinkedListNode<T> prev) {
        this.prev = prev;
    }

    public void setListReference(DoublyLinkedList<T> listReference) {
        this.listReference = listReference;
    }

    public LinkedListNode<T> getPrev() {
        return prev;
    }

    public boolean isEmpty(){
        return element == null;
    }

}
