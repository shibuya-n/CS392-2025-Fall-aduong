import Library.FnList.*;
import Library.LnList.*;
import Library.FnTuple.*;
import Library.MyMap00.*;

public class Assign08_01<V>
        implements MyMap00<String, V> {

    private LnList<FnTupl2<String, FnList<V>>>[] table;
    private int capacity;
    private int keyCount;

    @SuppressWarnings("unchecked")
    public Assign08_01() {
        this(16);
    }

    @SuppressWarnings("unchecked")
    public Assign08_01(int cap) {
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
    public Library.LnStrm.LnStrm<FnTupl2<String, FnList<V>>> strmize() {
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
        int idx = hash(key);
        LnList<FnTupl2<String, FnList<V>>> bucket = table[idx];
        FnTupl2<String, FnList<V>> entry = findEntry(bucket, key);
        return entry.sub1;
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
            entry.sub1 = new FnList<V>(val, entry.sub1);
        } else {
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
                prev.unlink();
                prev.link(current.tl1());
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
}