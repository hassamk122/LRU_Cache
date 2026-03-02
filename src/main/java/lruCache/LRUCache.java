package lruCache;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCache<Key, Value> implements Cache<Key,Value> {

    private final int maxSize;
    private final Map<Key, LinkedListNode<CacheElement<Key, Value>>> linkedListNodeMap;
    private final DoublyLinkedList<CacheElement<Key, Value>> doublyLinkedList;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public LRUCache(int maxSize){
        this.maxSize = maxSize;
        this.linkedListNodeMap = new ConcurrentHashMap<>(maxSize);
        this.doublyLinkedList = new DoublyLinkedList<>();
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
        this.lock.writeLock().lock();
        try{
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
        }finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * Removes the lru element from both the doubly-linked list
     * and the HashMap to free up space for a new entry.
     */
    private void evictElement(){
        this.lock.writeLock().lock();
        try{
            LinkedListNode<CacheElement<Key, Value>> lruNode = this.doublyLinkedList.removeLast();

            if ( !lruNode.isEmpty()){
                this.linkedListNodeMap.remove(lruNode.getElement().getKey());
            }
        }finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * gets the value associated with given key or optional empty if absent.
     * On every cache hit the node is moved to the front of the list
     */
    @Override
    public Optional<Value> get(Key key) {
        this.lock.readLock().lock();
        try{
            LinkedListNode<CacheElement<Key,Value>> node = this.linkedListNodeMap.get(key);

            if (node != null && !node.isEmpty()){
                this.linkedListNodeMap.put(key, doublyLinkedList.moveToFront(node));
                return Optional.of(node.getElement().getValue());
            }

            return Optional.empty();
        }finally {
            this.lock.readLock().unlock();
        }
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
