import Library.FnList.*;
import Library.LnList.*;
import Library.LnStrm.LnStrm;
import Library.FnTuple.*;
import Library.MyMap00.*;

public class Assign08_02<V>
        implements MyMap00<String, V> {

    private FnTupl2<String, FnList<V>>[] table;
    private boolean[] deleted; // Track deleted slots for proper probing
    private int capacity;
    private int keyCount;
    private static final double LOAD_FACTOR_THRESHOLD = 0.7;

    // Sentinel value to mark deleted entries
    private static final String DELETED_KEY = "\0DELETED\0";

    // Constructor with default capacity
    @SuppressWarnings("unchecked")
    public Assign08_02() {
        this(16);
    }

    // Constructor with specified capacity
    @SuppressWarnings("unchecked")
    public Assign08_02(int cap) {
        capacity = cap;
        keyCount = 0;
        table = (FnTupl2<String, FnList<V>>[]) new FnTupl2[capacity];
        deleted = new boolean[capacity];

        // Initialize all slots as empty
        for (int i = 0; i < capacity; i++) {
            table[i] = null;
            deleted[i] = false;
        }
    }

    // Hash function
    private int hash(String key) {
        int h = key.hashCode();
        return (h & 0x7FFFFFFF) % capacity;
    }

    // Quadratic probing: try positions h(k), h(k)+1^2, h(k)+2^2, h(k)+3^2, ...
    private int probe(String key, int attempt) {
        int h = hash(key);
        return (h + attempt * attempt) % capacity;
    }

    // Find the index of a key, or return -1 if not found
    private int findIndex(String key) {
        int attempt = 0;

        while (attempt < capacity) {
            int idx = probe(key, attempt);

            // Empty slot (never used) - key not in table
            if (table[idx] == null && !deleted[idx]) {
                return -1;
            }

            // Found the key
            if (table[idx] != null && table[idx].sub0.equals(key)) {
                return idx;
            }

            // Continue probing (either deleted or different key)
            attempt++;
        }

        return -1; // Not found after checking all positions
    }

    // Find an available slot for insertion (handles both empty and deleted)
    private int findInsertIndex(String key) {
        int attempt = 0;
        int firstDeleted = -1;

        while (attempt < capacity) {
            int idx = probe(key, attempt);

            // Key already exists
            if (table[idx] != null && table[idx].sub0.equals(key)) {
                return idx;
            }

            // Empty slot (never used)
            if (table[idx] == null && !deleted[idx]) {
                // Use first deleted slot if we found one, otherwise use this empty slot
                return (firstDeleted != -1) ? firstDeleted : idx;
            }

            // Deleted slot - remember first one we encounter
            if (deleted[idx] && firstDeleted == -1) {
                firstDeleted = idx;
            }

            attempt++;
        }

        // Table is full (shouldn't happen if load factor is maintained)
        return (firstDeleted != -1) ? firstDeleted : -1;
    }

    @Override
    public int size() {
        return keyCount;
    }

    @Override
    public boolean isFull() {
        return keyCount >= capacity * LOAD_FACTOR_THRESHOLD;
    }

    @Override
    public boolean isEmpty() {
        return keyCount == 0;
    }

    @Override
    public LnStrm strmize() {
        // Collect all non-null, non-deleted entries
        LnList<FnTupl2<String, FnList<V>>> allEntries = new LnList<FnTupl2<String, FnList<V>>>();

        for (int i = capacity - 1; i >= 0; i--) {
            if (table[i] != null && !deleted[i]) {
                allEntries = new LnList<FnTupl2<String, FnList<V>>>(table[i], allEntries);
            }
        }

        return convertListToStream(allEntries);
    }

    private Library.LnStrm.LnStrm<FnTupl2<String, FnList<V>>> convertListToStream(
            final LnList<FnTupl2<String, FnList<V>>> list) {
        return new Library.LnStrm.LnStrm<FnTupl2<String, FnList<V>>>(
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

        if (table[idx] != null && table[idx].sub0.equals(key)) {
            // Key exists: prepend value to its list (LIFO)
            table[idx].sub1 = new FnList<V>(val, table[idx].sub1);
        } else {
            // New key: create new entry
            FnList<V> newList = new FnList<V>(val, new FnList<V>());
            table[idx] = new FnTupl2<String, FnList<V>>(key, newList);
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
        deleted[idx] = true; // Mark as deleted, not just empty
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
    public void foritm(java.util.function.BiConsumer<? super String, ? super V> work) {
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null && !deleted[i]) {
                String key = table[i].sub0;
                FnList<V> vals = table[i].sub1;

                // Process each value for this key
                FnList<V> valList = vals;
                while (!valList.nilq()) {
                    work.accept(key, valList.hd());
                    valList = valList.tl();
                }
            }
        }
    }
}