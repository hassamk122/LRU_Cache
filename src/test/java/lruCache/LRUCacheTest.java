package lruCache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LRUCacheTest {

    @Test
    void get_existingKey_returnsValue() {
        LRUCache<String, String> cache = new LRUCache<>(3);
        cache.set("1", "hassam");

        assertTrue(cache.get("1").isPresent());
        assertEquals("hassam", cache.get("1").get());
    }

    @Test
    void get_missingKey_returnsEmpty() {
        LRUCache<String, String> cache = new LRUCache<>(3);

        assertFalse(cache.get("ghost").isPresent());
    }

    @Test
    void get_multipleKeys_returnsCorrectValues() {
        LRUCache<String, String> cache = new LRUCache<>(3);
        cache.set("1", "test1");
        cache.set("2", "test2");
        cache.set("3", "test3");

        assertEquals("test1", cache.get("1").get());
        assertEquals("test2", cache.get("2").get());
        assertEquals("test3", cache.get("3").get());
    }


    /**
     * When cache is full, inserting a new key evicts the LRU entry
     */
    @Test
    void eviction_lruKeyIsRemoved_whenCacheExceedsCapacity() {
        LRUCache<String, String> cache = new LRUCache<>(3);
        cache.set("1", "test1");
        cache.set("2", "test2");
        cache.set("3", "test3");
        cache.set("4", "test4");

        assertFalse(cache.get("1").isPresent());
        assertTrue(cache.get("4").isPresent());
    }


    /**
     * Accessing a key promotes it, so it is no longer evicted first
     */
    @Test
    void eviction_accessedKeyIsProtected_notEvicted() {
        LRUCache<String, String> cache = new LRUCache<>(3);
        cache.set("1", "test1");
        cache.set("2", "test2");
        cache.set("3", "test3");

        cache.get("1");

        cache.set("4", "test4");

        assertFalse(cache.get("2").isPresent());
        assertTrue(cache.get("1").isPresent());
    }

    @Test
    void set_existingKey_updatesValueAndKeepsSizeStable() {
        LRUCache<String, String> cache = new LRUCache<>(3);
        cache.set("a", "old");
        cache.set("a", "new");

        assertEquals("new", cache.get("a").get());
        assertEquals(1, cache.size());
    }


    @Test
    void isEmpty_afterInsert_returnsFalse() {
        LRUCache<String, String> cache = new LRUCache<>(5);
        cache.set("x", "y");

        assertFalse(cache.isEmpty());
    }


    @Test
    void clear_removesAllEntries() {
        LRUCache<String, String> cache = new LRUCache<>(3);
        cache.set("1", "a");
        cache.set("2", "b");
        cache.set("3", "c");

        cache.clear();

        assertEquals(0, cache.size());
        assertTrue(cache.isEmpty());
        assertFalse(cache.get("1").isPresent());
        assertFalse(cache.get("2").isPresent());
        assertFalse(cache.get("3").isPresent());
    }

    @Test
    void clear_cacheIsReusableAfterwards() {
        LRUCache<String, String> cache = new LRUCache<>(2);
        cache.set("1", "a");
        cache.set("2", "b");
        cache.clear();

        cache.set("3", "c");
        assertTrue(cache.get("3").isPresent());
        assertEquals(1, cache.size());
    }


    @Test
    void edgeCase_cacheSizeOne_evictsOnNewInsert() {
        LRUCache<String, String> cache = new LRUCache<>(1);
        cache.set("only", "value");
        cache.set("new",  "other");

        assertFalse(cache.get("only").isPresent());
        assertTrue(cache.get("new").isPresent());
    }


}