package lruCache;

public class LinkedListNode<T> {

    private T element;
    private LinkedListNode<T> next;
    private LinkedListNode<T> prev;


    public LinkedListNode(){
        this.element = null;
    }


    public LinkedListNode(T element) {
        this.element = element;
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

    public LinkedListNode<T> getPrev() {
        return prev;
    }

    public boolean isEmpty(){
        return element == null;
    }

}
