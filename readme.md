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
| Implementation | Allows Nulls? | Ordered? | Search O(n) | Insert | Structure | Sorting Algorithm | Unique Methods |
|---------------|--------------|---------|------------|--------|-----------|----------------|----------------|
| ArrayList     | Yes          | Yes     | O(n)       | O(1)   | Dynamic Array | MergeSort, Timsort | ensureCapacity(), trimToSize() |
| LinkedList    | Yes          | Yes     | O(n)       | O(1)   | Doubly Linked List | MergeSort | descendingIterator() |
| Vector        | Yes          | Yes     | O(n)       | O(1)   | Synchronized Dynamic Array | MergeSort | capacityIncrement() |
| Stack         | Yes          | Yes     | O(n)       | O(1)   | Synchronized Stack (LIFO) | - | push(), pop() |
| CopyOnWriteArrayList | Yes   | Yes     | O(n)       | O(n)   | Thread-Safe Dynamic Array | - | addAllAbsent() |
| AbstractList  | Yes          | Yes     | -          | -      | Abstract Implementation | - | - |
| List.of(...)  | No           | Yes     | -          | -      | Immutable List (Java 9+) | - | - |
| Collections.unmodifiableList(List<T>) | No | Yes | - | - | Unmodifiable Wrapper | - | - |
| RoleList (Apache Commons) | Yes | Yes | - | - | Specialized List | - | - |

## 4. Set Implementations
| Implementation  | Allows Nulls? | Ordered? | Search O(n) | Insert | Structure | Sorting Algorithm | Unique Methods |
|----------------|--------------|---------|------------|--------|-----------|----------------|----------------|
| HashSet       | Yes (1)       | No      | O(1)       | O(1)   | Hash Table | - | - |
| LinkedHashSet | Yes (1)       | Yes     | O(1)       | O(1)   | Hash Table + Linked List | - | - |
| TreeSet       | No            | Yes     | O(log n)   | O(log n) | Red-Black Tree | Tree traversal | headSet(), tailSet() |
| EnumSet       | No            | Yes     | O(1)       | O(1)   | Bit Vector | - | of(), allOf() |
| CopyOnWriteArraySet | Yes     | Yes     | O(n)       | O(n)   | Thread-Safe Set | - | addAllAbsent() |
| ConcurrentSkipListSet | No    | Yes     | O(log n)   | O(log n) | Concurrent Navigable Set | - | - |
| JobStateReasons | No         | Yes     | -          | -      | Specialized Enum Set | - | - |
| AbstractSet   | No            | No      | -          | -      | Abstract Implementation | - | - |
| Set.of(...)   | No            | Yes     | -          | -      | Immutable Set (Java 9+) | - | - |
| Collections.unmodifiableSet(Set<T>) | No | Yes | - | - | Unmodifiable Wrapper | - | - |

## 5. Map Implementations
| Implementation   | Allows Nulls (Keys/Values)? | Ordered? | Search O(n) | Insert | Structure | Sorting Algorithm | Unique Methods |
|-----------------|----------------------|---------|------------|--------|-----------|----------------|----------------|
| HashMap        | Yes / Yes             | No      | O(1)       | O(1)   | Hash Table | - | keySet(), values() |
| LinkedHashMap  | Yes / Yes             | Yes     | O(1)       | O(1)   | Hash Table + Linked List | - | accessOrder() |
| TreeMap        | No / Yes              | Yes     | O(log n)   | O(log n) | Red-Black Tree | Tree traversal | firstKey(), lastKey() |
| WeakHashMap    | Yes / Yes             | No      | O(1)       | O(1)   | Hash Table (Weak References) | - | - |
| ConcurrentHashMap | No / Yes           | No      | O(1)       | O(1)   | Thread-Safe Hash Table | - | - |
| EnumMap        | No / Yes              | Yes     | O(1)       | O(1)   | Array-based Map | - | - |

## 6. Queue Implementations
| Implementation  | Allows Nulls? | Ordered? | Search O(n) | Insert | Structure | Sorting Algorithm | Unique Methods |
|----------------|--------------|---------|------------|--------|-----------|----------------|----------------|
| LinkedList    | Yes          | Yes     | O(n)       | O(1)   | Doubly Linked List | - | - |
| PriorityQueue | No           | By Priority | O(log n) | O(log n) | Heap | HeapSort | comparator(), poll() |
| ArrayDeque    | No           | No      | O(1)       | O(1)   | Resizable Array | - | push(), pop() |


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


