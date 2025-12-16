package MyMap00;

import FnList.*;
import LnList.*;
import LnStrm.*;
import FnTuple.*;
import MyMap00.*;

public class MyMapSeparateChain<V>
        implements MyMap00<String, V> {
    // HX-2025-11-12:
    // Please give an implementation of hash table
    // that uses separate chaining for handling collisions.
    private LnList<FnTupl2<String, FnList<V>>>[] table;
    private int capacity;
    private int keyCount;

    @SuppressWarnings("unchecked")
    public MyMapSeparateChain() {
        this(16);
    }

    @SuppressWarnings("unchecked")
    public MyMapSeparateChain(int cap) {
        capacity = cap;
        keyCount = 0;
        table = (LnList<FnTupl2<String, FnList<V>>>[]) new LnList[capacity];

        for (int i = 0; i < capacity; i++) {
            table[i] = new LnList<FnTupl2<String, FnList<V>>>();
        }
    }

    private int hash(String key) {
        int h = key.hashCode();
        return (h & 0x7FFFFFFF) % capacity;
    }

    private FnTupl2<String, FnList<V>> findEntry(LnList<FnTupl2<String, FnList<V>>> bucket, String key) {
        LnList<FnTupl2<String, FnList<V>>> current = bucket;

        while (!current.nilq1()) {
            FnTupl2<String, FnList<V>> entry = current.hd1();
            if (entry.sub0.equals(key)) {
                return entry;
            }
            current = current.tl1();
        }
        return null;
    }

    @Override
    public int size() {
        return keyCount;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return keyCount == 0;
    }

    @Override
    public LnStrm<FnTupl2<String, FnList<V>>> strmize() {
        LnList<FnTupl2<String, FnList<V>>> allEntries = new LnList<FnTupl2<String, FnList<V>>>();

        for (int i = capacity - 1; i >= 0; i--) {
            LnList<FnTupl2<String, FnList<V>>> bucket = table[i];
            LnList<FnTupl2<String, FnList<V>>> current = bucket;

            while (!current.nilq1()) {
                FnTupl2<String, FnList<V>> entry = current.hd1();
                allEntries = new LnList<FnTupl2<String, FnList<V>>>(entry, allEntries);
                current = current.tl1();
            }
        }

        return convertListToStream(allEntries);
    }

    private LnStrm<FnTupl2<String, FnList<V>>> convertListToStream(
            final LnList<FnTupl2<String, FnList<V>>> list) {
        LnStrm<FnTupl2<String, FnList<V>>> toReturn = new LnStrm<FnTupl2<String, FnList<V>>>(
                () -> {
                    if (list.nilq1()) {
                        return new LnStcn<FnTupl2<String, FnList<V>>>();
                    } else {
                        return new LnStcn<FnTupl2<String, FnList<V>>>(
                                list.hd1(),
                                convertListToStream(list.tl1()));
                    }
                });
        return toReturn;
    }

    @Override
    public FnList<V> search$raw(String key) {
        int idx = hash(key);
        LnList<FnTupl2<String, FnList<V>>> bucket = table[idx];
        FnTupl2<String, FnList<V>> entry = findEntry(bucket, key);
        return (entry != null) ? entry.sub1 : null;
    }

    @Override
    public FnList<V> search$exn(String key) {
        int idx = hash(key);
        LnList<FnTupl2<String, FnList<V>>> bucket = table[idx];
        FnTupl2<String, FnList<V>> entry = findEntry(bucket, key);

        if (entry == null) {
            throw new MyMap00NoKeyExn();
        }
        return entry.sub1;
    }

    @Override
    public FnList<V> search$opt(String key) {
        int idx = hash(key);
        LnList<FnTupl2<String, FnList<V>>> bucket = table[idx];
        FnTupl2<String, FnList<V>> entry = findEntry(bucket, key);

        if (entry == null) {
            return null;
        }
        return entry.sub1;
    }

    @Override
    public void insert$raw(String key, V val) {
        int idx = hash(key);
        LnList<FnTupl2<String, FnList<V>>> bucket = table[idx];
        FnTupl2<String, FnList<V>> entry = findEntry(bucket, key);

        if (entry != null) {
            // Key exists: prepend value to its list (LIFO)
            entry.sub1 = new FnList<V>(val, entry.sub1);
        } else {
            // New key: create new entry
            FnList<V> newList = new FnList<V>(val, new FnList<V>());
            FnTupl2<String, FnList<V>> newEntry = new FnTupl2<String, FnList<V>>(key, newList);
            table[idx] = new LnList<FnTupl2<String, FnList<V>>>(newEntry, bucket);
            keyCount++;
        }
    }

    @Override
    public void insert$exn(String key, V val) {
        insert$raw(key, val);
    }

    @Override
    public boolean insert$opt(String key, V val) {
        insert$raw(key, val);
        return true;
    }

    @Override
    public FnList<V> remove$raw(String key) {
        int idx = hash(key);
        LnList<FnTupl2<String, FnList<V>>> bucket = table[idx];

        // Handle empty bucket
        if (bucket.nilq1()) {
            return null;
        }

        FnTupl2<String, FnList<V>> first = bucket.hd1();
        if (first.sub0.equals(key)) {
            FnList<V> vals = first.sub1;
            table[idx] = bucket.tl1();
            keyCount--;
            return vals;
        }

        LnList<FnTupl2<String, FnList<V>>> prev = bucket;
        LnList<FnTupl2<String, FnList<V>>> current = bucket.tl1();

        while (!current.nilq1()) {
            FnTupl2<String, FnList<V>> entry = current.hd1();
            if (entry.sub0.equals(key)) {
                FnList<V> vals = entry.sub1;
                prev.unlink1();
                prev.link1(current.tl1());
                keyCount--;
                return vals;
            }
            prev = current;
            current = current.tl1();
        }

        return null;
    }

    @Override
    public FnList<V> remove$exn(String key) {
        FnList<V> result = remove$raw(key);
        if (result == null) {
            throw new MyMap00NoKeyExn();
        }
        return result;
    }

    @Override
    public FnList<V> remove$opt(String key) {
        return remove$raw(key);
    }

    @Override
    public void foritm(java.util.function.BiConsumer<? super String, ? super V> work) {
        for (int i = 0; i < capacity; i++) {
            LnList<FnTupl2<String, FnList<V>>> bucket = table[i];
            LnList<FnTupl2<String, FnList<V>>> current = bucket;

            while (!current.nilq1()) {
                FnTupl2<String, FnList<V>> entry = current.hd1();
                String key = entry.sub0;
                FnList<V> vals = entry.sub1;

                FnList<V> valList = vals;
                while (!valList.nilq()) {
                    work.accept(key, valList.hd());
                    valList = valList.tl();
                }

                current = current.tl1();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Testing Assign08_01 (Separate Chaining Hash Table) ===\n");

        // Test 1: Basic insertion and search
        System.out.println("Test 1: Basic Insertion and Search");
        MyMapSeparateChain<Integer> map = new MyMapSeparateChain<>(8);

        map.insert$raw("apple", 1);
        map.insert$raw("banana", 2);
        map.insert$raw("cherry", 3);

        System.out.println("Size: " + map.size());
        System.out.println("Is empty: " + map.isEmpty());
        System.out.println("Is full: " + map.isFull());

        FnList<Integer> appleVals = map.search$opt("apple");
        System.out.println("Search 'apple': " + (appleVals != null ? appleVals.hd() : "null"));
        System.out.println();

        // Test 2: Multiple values for same key (LIFO)
        System.out.println("Test 2: Multiple Values per Key (LIFO)");
        map.insert$raw("apple", 10);
        map.insert$raw("apple", 20);
        map.insert$raw("apple", 30);

        FnList<Integer> appleList = map.search$opt("apple");
        System.out.print("Values for 'apple' (should be 30, 20, 10, 1): ");
        while (appleList != null && !appleList.nilq()) {
            System.out.print(appleList.hd() + " ");
            appleList = appleList.tl();
        }
        System.out.println("\n");

        // Test 3: Collision handling
        System.out.println("Test 3: Collision Handling");
        MyMapSeparateChain<String> smallMap = new MyMapSeparateChain<>(4);

        // These keys will likely collide in a small table
        smallMap.insert$raw("key1", "value1");
        smallMap.insert$raw("key2", "value2");
        smallMap.insert$raw("key3", "value3");
        smallMap.insert$raw("key4", "value4");
        smallMap.insert$raw("key5", "value5");

        System.out.println("Size after 5 insertions: " + smallMap.size());
        System.out.println("Search 'key1': " + smallMap.search$opt("key1").hd());
        System.out.println("Search 'key3': " + smallMap.search$opt("key3").hd());
        System.out.println("Search 'key5': " + smallMap.search$opt("key5").hd());
        System.out.println();

        // Test 4: Search variants
        System.out.println("Test 4: Search Variants");
        System.out.println("search$opt('banana'): " + map.search$opt("banana").hd());
        System.out.println("search$opt('nonexistent'): " + map.search$opt("nonexistent"));

        try {
            map.search$exn("banana");
            System.out.println("search$exn('banana'): Success");
        } catch (MyMap00NoKeyExn e) {
            System.out.println("search$exn('banana'): Failed (unexpected)");
        }

        try {
            map.search$exn("nonexistent");
            System.out.println("search$exn('nonexistent'): Success (unexpected)");
        } catch (MyMap00NoKeyExn e) {
            System.out.println("search$exn('nonexistent'): Threw exception (expected)");
        }
        System.out.println();

        // Test 5: Remove operations
        System.out.println("Test 5: Remove Operations");
        System.out.println("Size before remove: " + map.size());

        FnList<Integer> removedVals = map.remove$opt("apple");
        System.out.print("Removed values for 'apple': ");
        while (removedVals != null && !removedVals.nilq()) {
            System.out.print(removedVals.hd() + " ");
            removedVals = removedVals.tl();
        }
        System.out.println();

        System.out.println("Size after remove: " + map.size());
        System.out.println("Search 'apple' after remove: " + map.search$opt("apple"));

        FnList<Integer> removed = map.remove$opt("banana");
        System.out.println("Removed 'banana': " + (removed != null ? removed.hd() : "null"));
        System.out.println("Size after second remove: " + map.size());
        System.out.println();

        // Test 6: Remove on nonexistent key
        System.out.println("Test 6: Remove Nonexistent Key");
        System.out.println("remove$opt('nothere'): " + map.remove$opt("nothere"));

        try {
            map.remove$exn("nothere");
            System.out.println("remove$exn('nothere'): Success (unexpected)");
        } catch (MyMap00NoKeyExn e) {
            System.out.println("remove$exn('nothere'): Threw exception (expected)");
        }
        System.out.println();

        // Test 7: foritm iteration
        System.out.println("Test 7: Iteration with foritm");
        MyMapSeparateChain<String> iterMap = new MyMapSeparateChain<>();
        iterMap.insert$raw("dog", "woof");
        iterMap.insert$raw("cat", "meow");
        iterMap.insert$raw("dog", "bark");
        iterMap.insert$raw("bird", "chirp");

        System.out.println("All key-value pairs:");
        iterMap.foritm((key, val) -> {
            System.out.println("  " + key + " -> " + val);
        });
        System.out.println();

        // Test 8: Large-scale test
        System.out.println("Test 8: Large-Scale Test");
        MyMapSeparateChain<Integer> largeMap = new MyMapSeparateChain<>(16);

        for (int i = 0; i < 100; i++) {
            largeMap.insert$raw("key" + i, i);
        }

        System.out.println("Inserted 100 keys");
        System.out.println("Size: " + largeMap.size());
        System.out.println("Search 'key50': " + largeMap.search$opt("key50").hd());
        System.out.println("Search 'key99': " + largeMap.search$opt("key99").hd());

        int count = 0;
        largeMap.foritm((key, val) -> {
        });
        System.out.println("Successfully iterated through all entries");
        System.out.println();

        // Test 9: Empty map operations
        System.out.println("Test 9: Empty Map Operations");
        MyMapSeparateChain<String> emptyMap = new MyMapSeparateChain<>();
        System.out.println("Empty map size: " + emptyMap.size());
        System.out.println("Empty map isEmpty: " + emptyMap.isEmpty());
        System.out.println("Search in empty map: " + emptyMap.search$opt("anything"));
        System.out.println("Remove from empty map: " + emptyMap.remove$opt("anything"));
        System.out.println();

        // Test 10: Insert variants
        System.out.println("Test 10: Insert Variants");
        MyMapSeparateChain<Double> insertMap = new MyMapSeparateChain<>();

        insertMap.insert$raw("pi", 3.14);
        System.out.println("insert$raw successful");

        insertMap.insert$exn("e", 2.718);
        System.out.println("insert$exn successful");

        boolean result = insertMap.insert$opt("phi", 1.618);
        System.out.println("insert$opt returned: " + result);

        System.out.println("Final size: " + insertMap.size());
        System.out.println();

        System.out.println("=== All Tests Completed ===");
    }
}