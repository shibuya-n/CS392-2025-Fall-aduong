import Library.FnList.*;
import Library.LnList.*;
import Library.LnStrm.*;
import Library.FnTuple.*;
import Library.MyMap00.*;

import java.util.function.BiConsumer;

public class Assign08_02<V>
        implements MyMap00<String, V> {
    // HX-2025-11-12:
    // Please give an implementation of hash table
    // based on open addressing. The probing strategy
    // chosen for handling collisions is quadratic probing.
    private FnTupl2<String, FnList<V>>[] table;
    private boolean[] occupied; // Track which slots contain valid entries
    private boolean[] deleted; // Track deleted slots (for proper probing)
    private int capacity;
    private int keyCount;
    private static final double LOAD_FACTOR_THRESHOLD = 0.7;

    @SuppressWarnings("unchecked")
    public Assign08_02() {
        this(16);
    }

    @SuppressWarnings("unchecked")
    public Assign08_02(int cap) {
        capacity = cap;
        keyCount = 0;
        table = (FnTupl2<String, FnList<V>>[]) new FnTupl2[capacity];
        occupied = new boolean[capacity];
        deleted = new boolean[capacity];

        for (int i = 0; i < capacity; i++) {
            table[i] = null;
            occupied[i] = false;
            deleted[i] = false;
        }
    }

    // Hash function - maps string keys to table indices
    private int hash(String key) {
        int h = key.hashCode();
        return (h & 0x7FFFFFFF) % capacity;
    }

    // Quadratic probing: h(k, i) = (h(k) + i²) mod capacity
    private int probe(String key, int attempt) {
        int h = hash(key);
        return (h + attempt * attempt) % capacity;
    }

    // Find the index of a key, return -1 if not found
    private int findIndex(String key) {
        int attempt = 0;

        while (attempt < capacity) {
            int idx = probe(key, attempt);

            // Empty slot that was never used - key not in table
            if (!occupied[idx] && !deleted[idx]) {
                return -1;
            }

            // Found the key at this position
            if (occupied[idx] && table[idx] != null && table[idx].sub0.equals(key)) {
                return idx;
            }

            // Continue probing (either deleted or different key)
            attempt++;
        }

        return -1; // Not found after checking all positions
    }

    // Find an available slot for insertion
    private int findInsertIndex(String key) {
        int attempt = 0;
        int firstDeleted = -1;

        while (attempt < capacity) {
            int idx = probe(key, attempt);

            // Key already exists at this position
            if (occupied[idx] && table[idx] != null && table[idx].sub0.equals(key)) {
                return idx;
            }

            // Empty slot (never used) - use it or a previously deleted slot
            if (!occupied[idx] && !deleted[idx]) {
                return (firstDeleted != -1) ? firstDeleted : idx;
            }

            // Deleted slot - remember the first one we encounter
            if (deleted[idx] && firstDeleted == -1) {
                firstDeleted = idx;
            }

            attempt++;
        }

        // If we've checked all positions, use first deleted slot if available
        return firstDeleted;
    }

    @Override
    public int size() {
        return keyCount;
    }

    @Override
    public boolean isFull() {
        return keyCount >= (int) (capacity * LOAD_FACTOR_THRESHOLD);
    }

    @Override
    public boolean isEmpty() {
        return keyCount == 0;
    }

    @Override
    public LnStrm<FnTupl2<String, FnList<V>>> strmize() {
        // Collect all valid entries in reverse order
        LnList<FnTupl2<String, FnList<V>>> allEntries = new LnList<FnTupl2<String, FnList<V>>>();

        for (int i = capacity - 1; i >= 0; i--) {
            if (occupied[i] && !deleted[i] && table[i] != null) {
                allEntries = new LnList<FnTupl2<String, FnList<V>>>(table[i], allEntries);
            }
        }

        return convertListToStream(allEntries);
    }

    // Helper method to convert LnList to LnStrm
    private LnStrm<FnTupl2<String, FnList<V>>> convertListToStream(
            final LnList<FnTupl2<String, FnList<V>>> list) {
        return new LnStrm<FnTupl2<String, FnList<V>>>(
                () -> {
                    if (list.nilq1()) {
                        return new Library.LnStrm.LnStcn<FnTupl2<String, FnList<V>>>();
                    } else {
                        return new Library.LnStrm.LnStcn<FnTupl2<String, FnList<V>>>(
                                list.hd1(),
                                convertListToStream(list.tl1()));
                    }
                });
    }

    @Override
    public FnList<V> search$raw(String key) {
        int idx = findIndex(key);
        return table[idx].sub1;
    }

    @Override
    public FnList<V> search$exn(String key) {
        int idx = findIndex(key);

        if (idx == -1) {
            throw new MyMap00NoKeyExn();
        }

        return table[idx].sub1;
    }

    @Override
    public FnList<V> search$opt(String key) {
        int idx = findIndex(key);

        if (idx == -1) {
            return null;
        }

        return table[idx].sub1;
    }

    @Override
    public void insert$raw(String key, V val) {
        int idx = findInsertIndex(key);

        if (idx == -1) {
            // Table is completely full - should not happen with load factor check
            return;
        }

        if (occupied[idx] && !deleted[idx] && table[idx] != null && table[idx].sub0.equals(key)) {
            // Key exists: prepend value to its list (LIFO)
            table[idx].sub1 = new FnList<V>(val, table[idx].sub1);
        } else {
            // New key: create new entry
            FnList<V> newList = new FnList<V>(val, new FnList<V>());
            table[idx] = new FnTupl2<String, FnList<V>>(key, newList);
            occupied[idx] = true;
            deleted[idx] = false;
            keyCount++;
        }
    }

    @Override
    public void insert$exn(String key, V val) {
        if (isFull()) {
            throw new MyMap00FullExn();
        }
        insert$raw(key, val);
    }

    @Override
    public boolean insert$opt(String key, V val) {
        if (isFull()) {
            return false;
        }
        insert$raw(key, val);
        return true;
    }

    @Override
    public FnList<V> remove$raw(String key) {
        int idx = findIndex(key);

        if (idx == -1) {
            return null;
        }

        FnList<V> vals = table[idx].sub1;
        table[idx] = null;
        deleted[idx] = true; // Mark as deleted (not just empty)
        // Keep occupied[idx] as true to maintain probing chain
        keyCount--;

        return vals;
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
    public void foritm(BiConsumer<? super String, ? super V> work) {
        for (int i = 0; i < capacity; i++) {
            if (occupied[i] && !deleted[i] && table[i] != null) {
                String key = table[i].sub0;
                FnList<V> vals = table[i].sub1;

                // Process each value for this key (LIFO order)
                FnList<V> valList = vals;
                while (!valList.nilq()) {
                    work.accept(key, valList.hd());
                    valList = valList.tl();
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Testing Assign08_02 (Quadratic Probing) ===\n");

        // Test 1: Basic insertion and search
        testBasicOperations();

        // Test 2: Multiple values per key (LIFO)
        testMultipleValues();

        // Test 3: Collision handling
        testCollisionHandling();

        // Test 4: Removal operations
        testRemoval();

        // Test 5: Iterator (foritm)
        testIterator();

        // Test 6: Stream operations
        testStream();

        // Test 7: Edge cases
        testEdgeCases();

        System.out.println("\n=== All tests completed! ===");
    }

    // Test 1: Basic insertion and search
    private static void testBasicOperations() {
        System.out.println("Test 1: Basic Operations");
        Assign08_02<Integer> map = new Assign08_02<>(8);

        // Insert some values
        map.insert$raw("apple", 1);
        map.insert$raw("banana", 2);
        map.insert$raw("cherry", 3);

        System.out.println("Size after 3 insertions: " + map.size());
        System.out.println("Is empty? " + map.isEmpty());
        System.out.println("Is full? " + map.isFull());

        // Search for values
        FnList<Integer> appleVals = map.search$opt("apple");
        System.out.println("Value for 'apple': " + (appleVals != null ? appleVals.hd() : "null"));

        FnList<Integer> bananaVals = map.search$opt("banana");
        System.out.println("Value for 'banana': " + (bananaVals != null ? bananaVals.hd() : "null"));

        FnList<Integer> notFound = map.search$opt("notfound");
        System.out.println("Value for 'notfound': " + (notFound != null ? "found" : "null"));

        System.out.println();
    }

    // Test 2: Multiple values per key (LIFO)
    private static void testMultipleValues() {
        System.out.println("Test 2: Multiple Values Per Key (LIFO)");
        Assign08_02<String> map = new Assign08_02<>(8);

        // Insert multiple values for the same key
        map.insert$raw("colors", "red");
        map.insert$raw("colors", "green");
        map.insert$raw("colors", "blue");

        FnList<String> colors = map.search$opt("colors");
        System.out.print("Colors (LIFO order): ");
        while (colors != null && !colors.nilq()) {
            System.out.print(colors.hd() + " ");
            colors = colors.tl();
        }
        System.out.println();
        System.out.println();
    }

    // Test 3: Collision handling with quadratic probing
    private static void testCollisionHandling() {
        System.out.println("Test 3: Collision Handling");
        Assign08_02<Integer> map = new Assign08_02<>(8);

        // Insert keys that may collide
        map.insert$raw("key1", 10);
        map.insert$raw("key2", 20);
        map.insert$raw("key3", 30);
        map.insert$raw("key4", 40);
        map.insert$raw("key5", 50);

        System.out.println("Inserted 5 keys");
        System.out.println("Size: " + map.size());

        // Verify all can be retrieved
        System.out.println("key1: " + map.search$opt("key1").hd());
        System.out.println("key2: " + map.search$opt("key2").hd());
        System.out.println("key3: " + map.search$opt("key3").hd());
        System.out.println("key4: " + map.search$opt("key4").hd());
        System.out.println("key5: " + map.search$opt("key5").hd());
        System.out.println();
    }

    // Test 4: Removal operations
    private static void testRemoval() {
        System.out.println("Test 4: Removal Operations");
        Assign08_02<Integer> map = new Assign08_02<>(8);

        // Insert and then remove
        map.insert$raw("temp1", 100);
        map.insert$raw("temp2", 200);
        map.insert$raw("temp3", 300);

        System.out.println("Size before removal: " + map.size());

        FnList<Integer> removed = map.remove$opt("temp2");
        System.out.println("Removed value: " + (removed != null ? removed.hd() : "null"));
        System.out.println("Size after removal: " + map.size());

        // Try to search for removed key
        FnList<Integer> notFound = map.search$opt("temp2");
        System.out.println("Search for removed key: " + (notFound != null ? "found" : "null"));

        // Verify other keys still accessible
        System.out.println("temp1 still exists: " + (map.search$opt("temp1") != null));
        System.out.println("temp3 still exists: " + (map.search$opt("temp3") != null));
        System.out.println();
    }

    // Test 5: Iterator (foritm)
    private static void testIterator() {
        System.out.println("Test 5: Iterator (foritm)");
        Assign08_02<String> map = new Assign08_02<>(8);

        map.insert$raw("a", "alpha");
        map.insert$raw("b", "beta");
        map.insert$raw("c", "gamma");
        map.insert$raw("a", "alpha2"); // Multiple values for 'a'

        System.out.println("Iterating over all key-value pairs:");
        map.foritm((key, val) -> {
            System.out.println("  " + key + " -> " + val);
        });
        System.out.println();
    }

    // Test 6: Stream operations
    private static void testStream() {
        System.out.println("Test 6: Stream Operations");
        Assign08_02<Integer> map = new Assign08_02<>(8);

        map.insert$raw("x", 10);
        map.insert$raw("y", 20);
        map.insert$raw("z", 30);

        LnStrm<FnTupl2<String, FnList<Integer>>> stream = map.strmize();

        System.out.println("Streaming all entries:");
        stream.foritm0(entry -> {
            System.out.println("  Key: " + entry.sub0 + ", Value: " + entry.sub1.hd());
        });
        System.out.println();
    }

    // Test 7: Edge cases
    private static void testEdgeCases() {
        System.out.println("Test 7: Edge Cases");
        Assign08_02<Integer> map = new Assign08_02<>(4); // Small capacity

        // Test exception handling
        try {
            map.search$exn("nonexistent");
            System.out.println("ERROR: Should have thrown exception");
        } catch (MyMap00NoKeyExn e) {
            System.out.println("Correctly threw MyMap00NoKeyExn for missing key");
        }

        // Fill near capacity
        map.insert$raw("k1", 1);
        map.insert$raw("k2", 2);

        System.out.println("Size: " + map.size());
        System.out.println("Is full? " + map.isFull());

        // Test insert$opt when approaching full
        boolean success = map.insert$opt("k3", 3);
        System.out.println("Insert k3 success: " + success);

        // Try to overfill
        map.insert$opt("k4", 4);
        map.insert$opt("k5", 5);
        System.out.println("Final size: " + map.size());
        System.out.println("Is full? " + map.isFull());

        // Test insert$exn when full
        try {
            map.insert$exn("overflow", 999);
            System.out.println("Insert succeeded or not at capacity yet");
        } catch (MyMap00FullExn e) {
            System.out.println("Correctly threw MyMap00FullExn when full");
        }
        System.out.println();
    }
}