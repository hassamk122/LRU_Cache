package lruCache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LRUCache<Key, Value> implements Cache<Key,Value> {

    private final int maxSize;
    private final Map<Key, LinkedListNode<CacheElement<Key, Value>>> linkedListNodeMap;
    private final DoublyLinkedList<CacheElement<Key, Value>> doublyLinkedList;

    public LRUCache(int maxSize){
        this.maxSize = maxSize;
        this.linkedListNodeMap = new HashMap<>(maxSize);
        this.doublyLinkedList = new DoublyLinkedList<CacheElement<Key, Value>>();
    }


    /**
     * Inserts key value pair in the cache, and marks it as mru.
     * on a cache hit. we update the node's position
     * and move it to the front. On a cache miss we may
     * need to evict the lru element first if the cache is already at capacity,
     * then create and add the new node.  returns false only if the list
     * operation itself returned an empty/dummy node.
     */
    @Override
    public boolean set(Key key, Value value) {
        CacheElement<Key, Value> item = new CacheElement<>(key, value);
        LinkedListNode<CacheElement<Key,Value>> newNode;

        if  (linkedListNodeMap.containsKey(key)){
            LinkedListNode<CacheElement<Key, Value>> existingNode = this.linkedListNodeMap.get(key);
            newNode = this.doublyLinkedList.updateAndMoveToFront(existingNode, item);
        }else{
            if (this.size() >= maxSize){
                evictElement();
            }
            newNode = this.doublyLinkedList.add(item);
        }

        if (newNode.isEmpty()){
            return false;
        }

        this.linkedListNodeMap.put(key, newNode);
        return true;
    }

    /**
     * Removes the lru element from both the doubly-linked list
     * and the HashMap to free up space for a new entry.
     */
    private void evictElement(){
        LinkedListNode<CacheElement<Key, Value>> lruNode = this.doublyLinkedList.removeLast();

        if ( !lruNode.isEmpty()){
            this.linkedListNodeMap.remove(lruNode.getElement().getKey());
        }
    }

    /**
     * gets the value associated with given key or optional empty if absent.
     * On every cache hit the node is moved to the front of the list
     */
    @Override
    public Optional<Value> get(Key key) {
        LinkedListNode<CacheElement<Key,Value>> node = this.linkedListNodeMap.get(key);

        if (node != null && !node.isEmpty()){
           this.linkedListNodeMap.put(key, doublyLinkedList.moveToFront(node));
           return Optional.of(node.getElement().getValue());
        }

        return Optional.empty();
    }

    @Override
    public int size() {
        return doublyLinkedList.getSize();
    }

    @Override
    public boolean isEmpty() {
        return doublyLinkedList.isEmpty();
    }



    @Override
    public void clear() {
        this.linkedListNodeMap.clear();

        while (!this.doublyLinkedList.isEmpty()){
            this.doublyLinkedList.removeLast();
        }
    }


}
