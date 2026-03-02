package lruCache;

public class CacheElement<Key, Value> {
    private Key key;
    private Value value;

    public CacheElement(Key key, Value value){
        this.key = key;
        this.value = value;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Value getValue(){
        return value;
    }

    public Key getKey(){
        return key;
    }

}
