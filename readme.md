# Java Collections Framework Practice Repository

## Overview
This repository is dedicated to practicing and understanding the **Java Collections Framework (JCF)**. It covers all major collection types, their implementations, properties, and performance characteristics.

## 1. Abstract Collection Hierarchy
| Interface        | Description |
|-----------------|-------------|
| Collection<E>   | The root interface of the collections framework |
| List<E>        | Ordered collection allowing duplicates |
| Set<E>         | Collection with unique elements |
| Queue<E>       | FIFO (First-In-First-Out) collection |
| Deque<E>       | Double-ended queue |
| Map<K,V>       | Key-value pairs with unique keys |

## 2. Java Collections Framework Hierarchy
```plaintext
                   Collection
                  /    |    \
                List  Set   Queue
                 |      |      |
     ArrayList  HashSet  LinkedList
     LinkedList TreeSet  PriorityQueue
      Vector    LinkedHashSet  ArrayDeque
       Stack    EnumSet   CopyOnWriteArrayList
       CopyOnWriteArraySet  ConcurrentSkipListSet
```
```plaintext
                      Map
                 /         \
         HashMap       SortedMap
         LinkedHashMap   TreeMap
         WeakHashMap     EnumMap
         IdentityHashMap ConcurrentHashMap
         Properties      ConcurrentSkipListMap
```

## 3. List Implementations
| Implementation | Allows Nulls? | Ordered? | Allow Duplicates? | Indexing | Search | Insert Middle | Insert End | Delete | Structure | Sorting and Search Algorithm | Unique Methods | Thread-Safe? | Resizable? | Immutability | Fail-Fast or Fail-Safe? | Memory Overhead | Backed By |
|---------------|--------------|---------|----------------|---------|--------|--------------|-----------|--------|-----------|----------------------------|---------------|--------------|------------|-------------|--------------------|----------------|-----------|
| **ArrayList** | Yes | Yes | Yes | O(1) | O(n) | O(n) | O(1) | O(n) | Dynamic array | Merge Sort (via Collections.sort), Binary Search (via Collections.binarySearch) | ensureCapacity(int), trimToSize() | No | Yes | No | Fail-Fast | Moderate | - |
| **LinkedList** | Yes | Yes | Yes | O(n) | O(n) | O(1) | O(1) | O(1) | Doubly linked list | Merge Sort (via Collections.sort), Linear search | getFirst(), getLast(), removeFirst(), removeLast() | No | Yes | No | Fail-Fast | High | - |
| **Vector** | Yes | Yes | Yes | O(1) | O(n) | O(n) | O(1) | O(n) | Synchronized dynamic array | Merge Sort (via Collections.sort), Binary Search (via Collections.binarySearch) | capacity(), ensureCapacity(), trimToSize() | Yes | Yes | No | Fail-Fast | High | - |
| **Stack** | Yes | Yes | Yes | O(n) | O(n) | O(n) | O(1) | O(1) | LIFO (extends Vector) | - | push(E), pop(), peek(), empty(), search(Object) | Yes | Yes | No | Fail-Fast | High | Vector |
| **CopyOnWriteArrayList** | Yes | Yes | Yes | O(1) | O(n) | O(n) | O(n) | O(n) | Thread-safe array | Merge Sort (via Collections.sort) | addIfAbsent(E), addAllAbsent(Collection<? extends E>) | Yes | Yes | No | Fail-Safe | High | - |
| **List.of(...) (Java 9+)** | No | Yes | Yes | O(1) | O(n) | - | - | - | Immutable List | - | of(...) | No | No | Yes | Fail-Fast | Low | - |
| **Collections.unmodifiableList(List<T>)** | Same as underlying list | Same as underlying list | Same as underlying list | Same as underlying list | Same as underlying list | - | - | - | Immutable wrapper | - | unmodifiableList(List<T>) | No | No | Yes | Fail-Fast | Low | Another List |
| **AbstractList** | Yes | Yes | Yes | O(1) | O(n) | O(n) | O(1) | O(n) | Partial implementation of List | Merge Sort (via Collections.sort) | - | No | Yes | No | Fail-Fast | Moderate | - |
| **RoleList (Apache Commons Collections)** | Yes | Yes | Yes | O(1) | O(n) | O(n) | O(1) | O(n) | Dynamic array | Merge Sort (via Collections.sort) | role-specific methods | No | Yes | No | Fail-Fast | Moderate | - |
| **AbstractSequentialList** | Yes | Yes | Yes | O(n) | O(n) | O(1) | O(1) | O(1) | Partial implementation of List (optimized for sequential access) | Merge Sort (via Collections.sort) | - | No | Yes | No | Fail-Fast | High | - |
| **RandomAccess** | - | - | - | - | - | - | - | - | Marker interface | - | - | - | - | - | - | - | - |

## 4. Set Implementations
| Implementation | Allows Nulls? | Ordered? | Allow Duplicates? | Indexing | Search | Insert Middle | Insert End | Delete | Structure | Sorting and Search Algorithm | Unique Methods | Thread-Safe? | Resizable? | Immutability | Fail-Fast or Fail-Safe? | Memory Overhead | Backed By |
|---------------|--------------|---------|----------------|---------|--------|--------------|-----------|--------|-----------|----------------------------|---------------|--------------|------------|-------------|--------------------|----------------|-----------|
| **HashSet** | No | No | No | - | O(1) | - | O(1) | O(1) | Hash table | Hash-based | - | No | Yes | No | Fail-Fast | Moderate | HashMap |
| **LinkedHashSet** | No | Yes | No | - | O(1) | - | O(1) | O(1) | Hash table + linked list | Hash-based | - | No | Yes | No | Fail-Fast | Moderate | HashMap + LinkedList |
| **TreeSet** | No | Yes (Sorted) | No | - | O(log n) | - | O(log n) | O(log n) | Red-black tree | Natural ordering / Comparator | first(), last(), headSet(), tailSet(), subSet() | No | Yes | No | Fail-Fast | High | TreeMap |
| **EnumSet** | No | Yes (Enum order) | No | - | O(1) | - | O(1) | O(1) | Bit vector | - | allOf(), noneOf(), range(), complementOf() | No | No | Yes | Fail-Fast | Low | Bitset |
| **CopyOnWriteArraySet** | No | Yes | No | - | O(n) | - | O(n) | O(n) | Copy-on-write array | Iteration snapshot | addIfAbsent(E) | Yes | Yes | No | Fail-Safe | High | CopyOnWriteArrayList |
| **ConcurrentSkipListSet** | No | Yes (Sorted) | No | - | O(log n) | - | O(log n) | O(log n) | Skip list | Natural ordering / Comparator | - | Yes | Yes | No | Fail-Safe | High | ConcurrentSkipListMap |
| **JobStateReasons** | No | Yes | No | - | O(1) | - | O(1) | O(1) | EnumSet | - | - | No | No | Yes | Fail-Fast | Low | EnumSet |
| **AbstractSet** | No | No | No | - | O(n) | - | O(n) | O(n) | Partial Set implementation | - | - | No | Yes | No | Fail-Fast | Moderate | - |
| **Set.of(...)** | No | Yes | No | - | O(n) | - | - | - | Immutable Set | - | of(...) | No | No | Yes | Fail-Fast | Low | - |
| **Collections.unmodifiableSet(Set<T>)** | Same as underlying set | Same as underlying set | Same as underlying set | - | Same as underlying set | - | - | - | Immutable wrapper | - | unmodifiableSet(Set<T>) | No | No | Yes | Fail-Fast | Low | Another Set |
| **ImmutableSet (Google Guava)** | No | Yes | No | - | O(1) | - | - | - | Immutable Hash-based Set | - | copyOf(), of() | No | No | Yes | Fail-Fast | Low | - |
| **HashMultiset (Google Guava)** | No | No | Yes | - | O(1) | - | O(1) | O(1) | Hash table | Hash-based | count(), elementSet(), entrySet() | No | Yes | No | Fail-Fast | Moderate | HashMap |
| **ListOrderedSet (Apache Commons Collections)** | No | Yes | No | - | O(n) | - | O(n) | O(n) | HashSet + List | - | - | No | Yes | No | Fail-Fast | Moderate | HashSet + ArrayList |
| **CompositeSet (Apache Commons Collections)** | No | Yes (Merged order) | No | - | O(n) | - | O(n) | O(n) | Composite of multiple sets | - | addComposited(Set<?>...), toCollection() | No | Yes | No | Fail-Fast | Moderate | Multiple Sets |
| **UnifiedSet (Eclipse Collections)** | No | No | No | - | O(1) | - | O(1) | O(1) | Hash table | Hash-based | getIfAbsentPut() | No | Yes | No | Fail-Fast | Moderate | HashMap |
| **MutableSet (Eclipse Collections)** | No | No | No | - | O(1) | - | O(1) | O(1) | Hash table | Hash-based | - | No | Yes | No | Fail-Fast | Moderate | HashMap |
| **ImmutableSet (Eclipse Collections)** | No | No | No | - | O(1) | - | - | - | Immutable Hash-based Set | - | ofAll(), newSetWith() | No | No | Yes | Fail-Fast | Low | - |


## 5. Map Implementations
| Implementation | Allows Nulls? | Ordered? | Allow Duplicates? | Indexing | Search | Insert Middle | Insert End | Delete | Structure | Sorting and Search Algorithm | Unique Methods | Thread-Safe? | Resizable? | Immutability | Fail-Fast or Fail-Safe? | Memory Overhead | Backed By |
|---------------|--------------|---------|----------------|---------|--------|--------------|-----------|--------|-----------|----------------------------|---------------|--------------|------------|-------------|--------------------|----------------|-----------|
| **HashMap** | Yes (keys & values) | No | Keys: No, Values: Yes | - | O(1) | - | O(1) | O(1) | Hash table | Hash-based | keySet(), values(), entrySet() | No | Yes | No | Fail-Fast | Moderate | Hash Table |
| **LinkedHashMap** | Yes (keys & values) | Yes (Insertion Order) | Keys: No, Values: Yes | - | O(1) | - | O(1) | O(1) | Hash table + linked list | Hash-based | accessOrder flag | No | Yes | No | Fail-Fast | Moderate | Hash Table + Linked List |
| **TreeMap** | No (keys), Yes (values) | Yes (Sorted by key) | Keys: No, Values: Yes | - | O(log n) | - | O(log n) | O(log n) | Red-black tree | Natural ordering / Comparator | firstKey(), lastKey(), headMap(), tailMap() | No | Yes | No | Fail-Fast | High | Red-Black Tree |
| **Hashtable** | No (keys & values) | No | Keys: No, Values: Yes | - | O(1) | - | O(1) | O(1) | Synchronized Hash Table | Hash-based | keys() (legacy) | Yes | Yes | No | Fail-Fast | High | Hash Table |
| **ConcurrentHashMap** | No (keys), Yes (values) | No | Keys: No, Values: Yes | - | O(1) | - | O(1) | O(1) | Segmented Hash Table | Hash-based | compute(), merge(), reduce() | Yes | Yes | No | Fail-Safe | High | Segmented Hash Table |
| **IdentityHashMap** | Yes (keys & values) | No | Keys: No, Values: Yes | - | O(1) | - | O(1) | O(1) | Hash table (Reference equality) | Identity-based | - | No | Yes | No | Fail-Fast | Moderate | Hash Table |
| **WeakHashMap** | Yes (keys & values) | No | Keys: No, Values: Yes | - | O(1) | - | O(1) | O(1) | Hash table (Weak keys) | Hash-based | Automatic garbage collection of unused keys | No | Yes | No | Fail-Fast | Moderate | Hash Table |
| **EnumMap** | No (keys), Yes (values) | Yes (Enum order) | Keys: No, Values: Yes | - | O(1) | - | O(1) | O(1) | Array-backed Map | Enum-based | - | No | No | No | Fail-Fast | Low | Array |
| **ConcurrentSkipListMap** | No (keys), Yes (values) | Yes (Sorted by key) | Keys: No, Values: Yes | - | O(log n) | - | O(log n) | O(log n) | Skip list | Natural ordering / Comparator | - | Yes | Yes | No | Fail-Safe | High | Skip List |
| **Properties** | Yes (keys & values) | No | Keys: No, Values: Yes | - | O(1) | - | O(1) | O(1) | Hashtable (String keys & values) | Hash-based | getProperty(), setProperty(), load(), store() | No | Yes | No | Fail-Fast | Moderate | Hashtable |
| **Collections.unmodifiableMap(Map<K, V>)** | Same as underlying map | Same as underlying map | Same as underlying map | - | Same as underlying map | - | - | - | Immutable wrapper | - | unmodifiableMap(Map<K,V>) | No | No | Yes | Fail-Fast | Low | Another Map |
| **AbstractMap** | Yes (keys & values) | No | Keys: No, Values: Yes | - | O(n) | - | O(n) | O(n) | Partial Map implementation | - | - | No | Yes | No | Fail-Fast | Moderate | - |
| **Map.of(...) (Java 9+)** | No (keys), Yes (values) | Yes (Insertion order) | Keys: No, Values: Yes | - | O(1) | - | - | - | Immutable Map | - | of(...) | No | No | Yes | Fail-Fast | Low | - |

## 6. Queue Implementations
| Implementation | Allows Nulls? | Ordered? | Allow Duplicates? | Indexing | Search | Insert Middle | Insert End | Delete | Structure | Sorting and Search Algorithm | Unique Methods | Thread-Safe? | Resizable? | Immutability | Fail-Fast or Fail-Safe? | Memory Overhead | Backed By |
|---------------|--------------|---------|----------------|---------|--------|--------------|-----------|--------|-----------|----------------------------|---------------|--------------|------------|-------------|--------------------|----------------|-----------|
| **LinkedList (also implements Queue)** | Yes | Yes (Insertion order) | Yes | O(n) | O(n) | O(n) | O(1) | O(1) | Doubly Linked List | Sequential search | offer(), poll(), peek(), removeFirst(), addLast() | No | Yes | No | Fail-Fast | Moderate | Linked List |
| **PriorityQueue** | No | No (Sorted Order) | Yes | O(n) | O(log n) | - | O(log n) | O(log n) | Binary Heap | Heap-based | comparator(), poll(), peek() | No | Yes | No | Fail-Fast | Moderate | Binary Heap |
| **ArrayDeque** | No | Yes (Insertion order) | Yes | O(1) | O(n) | O(n) | O(1) | O(1) | Resizable Array | Linear search | addFirst(), addLast(), removeFirst(), removeLast() | No | Yes | No | Fail-Fast | Moderate | Array |
| **ConcurrentLinkedQueue** | No | Yes (Insertion order) | Yes | O(n) | O(n) | - | O(1) | O(1) | Lock-Free Linked List | CAS-based | offer(), poll(), peek() | Yes | Yes | No | Fail-Safe | Low | Linked List |
| **BlockingQueue (Interface)** | No | Depends on Implementation | Yes | - | - | - | - | - | Varies | Varies | put(), take(), drainTo() | Yes | Yes | No | Fail-Safe | Varies | Varies |
| **LinkedBlockingQueue** | No | Yes (FIFO Order) | Yes | O(n) | O(n) | - | O(1) | O(1) | Linked List | Sequential search | put(), take(), drainTo() | Yes | Yes | No | Fail-Safe | Moderate | Linked List |
| **ArrayBlockingQueue** | No | Yes (FIFO Order) | Yes | O(n) | O(n) | - | O(1) | O(1) | Bounded Array | Linear search | put(), take(), remainingCapacity() | Yes | No | No | Fail-Safe | Moderate | Array |
| **Deque (Interface)** | No | Yes (Insertion order) | Yes | - | - | - | - | - | Varies | Varies | addFirst(), addLast(), removeFirst(), removeLast() | No | Yes | No | Varies | Varies | Varies |
| **SynchronousQueue** | No | No (Only one element at a time) | No | - | - | - | - | - | Direct Handoff | Blocking Exchange | put(), take() | Yes | No | No | Fail-Safe | Low | Direct Exchange |
| **DelayQueue** | No | Yes (Time-ordered) | Yes | O(n) | O(log n) | - | O(log n) | O(log n) | Priority Queue with Time-based Ordering | Time-based | offer(), poll(), peek(), take() | Yes | Yes | No | Fail-Safe | Moderate | PriorityQueue |
| **LinkedTransferQueue** | No | Yes (FIFO Order) | Yes | O(n) | O(n) | - | O(1) | O(1) | Linked List | CAS-based | tryTransfer(), transfer() | Yes | Yes | No | Fail-Safe | Low | Linked List |
| **PriorityBlockingQueue** | No | No (Sorted Order) | Yes | O(n) | O(log n) | - | O(log n) | O(log n) | Priority Queue | Heap-based | comparator(), poll(), peek() | Yes | Yes | No | Fail-Safe | Moderate | Binary Heap |
| **ConcurrentLinkedDeque** | No | Yes (Insertion order) | Yes | O(n) | O(n) | - | O(1) | O(1) | Lock-Free Linked List | CAS-based | addFirst(), addLast(), pollFirst(), pollLast() | Yes | Yes | No | Fail-Safe | Low | Linked List |
| **Queue.of(...) (Java 9+)** | No | Yes (Insertion order) | Yes | - | O(1) | - | - | - | Immutable List | - | of(...) | No | No | Yes | Fail-Fast | Low | Immutable Collection |
| **AbstractQueue** | No | No | Yes | O(n) | O(n) | - | O(1) | O(1) | Abstract class | Depends on implementation | - | No | Yes | No | Fail-Fast | Moderate | - |
| **ArrayQueue** | No | Yes (FIFO Order) | Yes | O(n) | O(n) | - | O(1) | O(1) | Bounded Array | Linear search | offer(), poll(), peek() | No | No | No | Fail-Fast | Moderate | Array |

## Additional Notes:
- **Sorting Algorithms**: Collections framework often uses **Timsort (Hybrid MergeSort & InsertionSort)** for sorting operations.
- **Performance Considerations**: Hash-based structures (like `HashSet` and `HashMap`) provide **O(1) lookup** but lack ordering, while Tree-based structures (like `TreeSet` and `TreeMap`) maintain order but have **O(log n) performance**.
- **Thread-Safety**: `Vector`, `Hashtable`, and `ConcurrentHashMap` provide thread-safety at the cost of performance.

### Contribution
Feel free to fork this repository, add implementations, and improve the documentation!

### References
- Official Java Documentation
- "Java: The Complete Reference" by Herbert Schildt
- Oracle Java Tutorials


