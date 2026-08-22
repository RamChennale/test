# Java Collections Framework — Interview Prep
## Questions, Answers, Code Examples & Differences (for QA Automation, 7–9 Years)
Collections come up constantly in automation interviews — storing test data, handling API response parsing, managing WebElements, deduplication,
 sorting test results, etc. This guide covers concepts **with the "why would I use this in automation" angle** interviewers expect at senior level.

---

## SECTION 1: Core Framework Overview

### Q1. What is the Java Collections Framework? Draw/explain its hierarchy.
**A:** 
	It's a unified architecture for storing and manipulating groups of objects, consisting of 
	**interfaces** (Collection, List, Set, Map, Queue), 
	their **implementations** (ArrayList, HashSet, HashMap, etc.), and 
	**algorithms** (Collections.sort(), Collections.reverse()).

Top-level hierarchy:
```
Iterable
   └── Collection
          ├── List   (ordered, allows duplicates)
          ├── Set    (no duplicates)
          └── Queue  (FIFO/priority processing)

Map (separate hierarchy — not a Collection)
   ├── HashMap
   ├── TreeMap
   └── LinkedHashMap
```
**Note:** `Map` does NOT extend `Collection` — it's a separate interface since it stores key-value pairs, not single elements.

### Q2. Why does `Map` not extend `Collection`?
**A:** 

	`Collection` is designed around single-element operations (`add(E e)`, `remove(E e)`). 

	`Map` deals with key-value pairs, which doesn't fit that contract cleanly — `add(K, V)` would need two parameters, 
	breaking the `Collection` interface. 
	So `Map` is deliberately a **separate root interface**, though it provides `keySet()`, `values()`, and `entrySet()` 
	which return `Collection` views.

---

## SECTION 2: List Interface

### Q3. ArrayList vs LinkedList — differences and when to use which?

| Aspect | ArrayList | LinkedList |
|---------------------------|---|---|
| Underlying structure 		| Dynamic (resizable) array | Doubly linked list |
| Access (get by index) 	| O(1) — fast 				| O(n) — must traverse |
| Insert/Delete (middle) 	| O(n) — shifts elements 	| O(1) — once node found |
| Insert/Delete (at ends)	| O(n) amortized for add at end (O(1) usually, O(n) on resize) | O(1) |
| Memory overhead 			| Lower (just array) 		| Higher (stores prev/next pointers per node) |
| Implements 				| List, RandomAccess		| List, Deque |

**Example:**
```java
List<String> arrayList = new ArrayList<>();
arrayList.add("TestCase1");
arrayList.add("TestCase2");
arrayList.get(0);  // O(1) — fast random access

List<String> linkedList = new LinkedList<>();
linkedList.add("TestCase1");
linkedList.addFirst("PriorityTest"); // O(1) — fast insert at head
```

**When I use which in automation:** `ArrayList` almost always — for storing WebElements, test data rows, 
API response lists — since we mostly iterate/read, rarely insert in the middle. 
`LinkedList` only if I need a queue-like structure (e.g., processing a FIFO queue of test steps).

### Q4. What is `Vector`? How is it different from `ArrayList`?
**A:** 
	`Vector` is legacy (Java 1.0), **synchronized** (thread-safe) but slower due to lock overhead on every operation. 
	`ArrayList` is unsynchronized and faster for single-threaded use. 
In practice, I never use `Vector` — if thread safety is needed, I use `Collections.synchronizedList()` or `CopyOnWriteArrayList` instead, 
which are more modern and flexible.

### Q5. What is `CopyOnWriteArrayList` and when would you use it in a test framework?
**A:** A thread-safe variant of `ArrayList` where every write operation creates a fresh copy of the underlying array. 
Reads don't need locking (no `ConcurrentModificationException`). 

**Real automation use case:** In **parallel test execution** (TestNG parallel="methods"), 
if multiple threads log results into a shared list (e.g., a shared "failed test names" list for reporting), 
`CopyOnWriteArrayList` avoids `ConcurrentModificationException` that a plain `ArrayList` would throw.

```java
List<String> failedTests = new CopyOnWriteArrayList<>();
// Safe to add from multiple test threads concurrently
failedTests.add(testName);
```

---

## SECTION 3: Set Interface

### Q6. HashSet vs LinkedHashSet vs TreeSet — differences?

| Aspect 		| HashSet 				| LinkedHashSet				 | TreeSet |
|---			|-------------------	|----------------------------|---|
| Order 		| No guaranteed order	| Insertion order preserved | Sorted (natural/comparator) |
| Underlying structure | HashMap internally | LinkedHashMap internally | Red-Black Tree (TreeMap internally) |
| Performance (add/remove/contains) | O(1) average 	| O(1) average 	| O(log n) |
| Null elements | One null allowed	 	| One null allowed 			| No nulls (throws NPE in comparison) |
| Use case 		| Fast dedup, order doesn't matter | Dedup + need insertion order | Dedup + need sorted order |

**Example:**
```java
Set<String> hashSet = new HashSet<>();
hashSet.add("Chrome"); hashSet.add("Firefox"); hashSet.add("Chrome"); // duplicate ignored
System.out.println(hashSet); // order not guaranteed

Set<String> linkedHashSet = new LinkedHashSet<>();
linkedHashSet.add("Chrome"); linkedHashSet.add("Firefox");
System.out.println(linkedHashSet); // [Chrome, Firefox] — insertion order

Set<Integer> treeSet = new TreeSet<>();
treeSet.add(30); treeSet.add(10); treeSet.add(20);
System.out.println(treeSet); // [10, 20, 30] — sorted
```

**Automation use case:** I use `HashSet` to **deduplicate browser names or test data** pulled from a data provider. 
`LinkedHashSet` when I need dedup but must **preserve the execution/insertion order** (e.g., unique failed test names in the order they failed). 
`TreeSet` when I need a **sorted set** of, say, test priorities or timestamps.


## SECTION 4: Map Interface

### Q9. HashMap vs LinkedHashMap vs TreeMap vs Hashtable vs ConcurrentHashMap

| Aspect 				| HashMap 		| LinkedHashMap 	| TreeMap 		| Hashtable 						| ConcurrentHashMap 			 	|
|---					|---	  		|---				|---			|---								|---								|
| Order					| No guarantee 	| Insertion order 	| Sorted by key | No guarantee 						| No guarantee					    |
| Thread-safe 			| No 			| No 				| No 			| Yes (fully synchronized, slow) 	| Yes (segment/bucket-level locking, fast) |
| Null key/values 		| 1null:M null val| Same as HashMap | No null key 	| No nulls allowed at all 			| No nulls allowed 					|
| Performance 			| O(1) average | O(1) average 		| O(log n)		| O(1) but slow (lock on every op)  | O(1) average, better concurrency 	|

**Example:**
```java
Map<String, String> hashMap = new HashMap<>();
hashMap.put("browser", "Chrome");
hashMap.put("env", "QA");

Map<String, String> linkedHashMap = new LinkedHashMap<>();
linkedHashMap.put("step1", "Login");
linkedHashMap.put("step2", "Search"); // maintains insertion order — useful for ordered test steps

Map<String, Integer> treeMap = new TreeMap<>();
treeMap.put("Zebra", 1); treeMap.put("Apple", 2);
System.out.println(treeMap); // {Apple=2, Zebra=1} — sorted by key
```

**Automation use case:** 
	`HashMap` for general key-value test config. 
	`LinkedHashMap` when I need an **ordered test-step map** (step name → status) for reporting in execution order. 
	`TreeMap` for **sorted results** (e.g., alphabetically sorted module-wise pass/fail counts). 
	`ConcurrentHashMap` in **parallel execution frameworks** to safely store thread-wise WebDriver instances or results without synchronization	overhead.



## SECTION 7: Utility & Advanced

### Q17. What is the `Collections` class (not to be confused with Collection interface)?
**A:** A utility class with static methods to operate on collections: 
`Collections.sort()`, `Collections.reverse()`, `Collections.unmodifiableList()`, `Collections.synchronizedList()`, 
`Collections.max()/min()`, `Collections.emptyList()`.

```java
List<String> tests = new ArrayList<>(List.of("TC3","TC1","TC2"));
Collections.sort(tests);                          // natural order sort
List<String> readOnly = Collections.unmodifiableList(tests); // prevents accidental mutation of shared test data
```
### Q19. Difference between `Iterator` and `ListIterator`?
**A:** 
- `Iterator`: forward-only traversal, works on any `Collection` — `hasNext()`, `next()`, `remove()`.
- `ListIterator`: extends `Iterator`, works only on `List`, supports **bidirectional traversal** (`hasPrevious()`, `previous()`), and allows `set()` (replace element) and `add()` during iteration — not just `remove()`.

```java
List<String> tests = new ArrayList<>(List.of("TC1","TC2","TC3"));
ListIterator<String> it = tests.listIterator();
while (it.hasNext()) {
    String t = it.next();
    if (t.equals("TC2")) it.set("TC2-Updated"); // safe in-place update
}
```


## SECTION 8: Quick Comparison Cheat Sheet

| Best Choice 				| Need 								|
|---						|---								|
| `ArrayList` 				|Fast random access, mostly reads 	|	 
| `LinkedList`/`ArrayDeque` |Frequent insert/delete at ends 	| 
| `HashSet` 				|No duplicates, order doesn't matter| 
| `LinkedHashSet` 			|No duplicates, preserve insertion order | 
| `TreeSet` 				|No duplicates, need sorted order |
| `HashMap` 				|Key-value lookup, fastest | 
| `LinkedHashMap` 			|Key-value, preserve insertion order | 
| `TreeMap` 				|Key-value, sorted by key | 
| `ConcurrentHashMap` 		|Thread-safe map, high concurrency | 
| `CopyOnWriteArrayList` 	|Thread-safe list, few writes/many reads | 
|  `LinkedList` (as Queue)/`ArrayDeque` |FIFO processing |
| `PriorityQueue` 			|Priority-based processing | 
| `Comparator` 				|Multiple custom sort orders | 
| `Comparable` 				|Single natural sort order on the class itself | 

---


## SECTION 9: Common Scenario-Based Questions

### Q21. You have a list of 10,000 API response objects and need to check if a specific ID exists frequently. Which collection do you use and why?
**A:** Convert to a `HashSet<String>` (of IDs) for O(1) `contains()` lookups instead of an O(n) linear scan through a `List`. 
	If I need to check existence hundreds of times, the one-time O(n) conversion cost pays off massively over repeated O(1) lookups vs repeated O(n) 
	scans.

### Q22. How would you remove duplicate WebElements/test data from a list while preserving order?
**A:**
```java
List<String> testData = List.of("TC1", "TC2", "TC1", "TC3", "TC2");
List<String> deduped = new ArrayList<>(new LinkedHashSet<>(testData));
System.out.println(deduped); // [TC1, TC2, TC3] — duplicates removed, order preserved
```

### Q23. You need to count occurrences of each test status (Pass/Fail/Skip) from a list of results. How?
**A:** Using `HashMap` with a `merge()` or the `Collectors.groupingBy()` + `counting()` from Streams:
```java
List<String> statuses = List.of("Pass","Fail","Pass","Skip","Pass","Fail");

Map<String, Long> countMap = statuses.stream()
    .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

System.out.println(countMap); // {Pass=3, Fail=2, Skip=1}
```



*Interview tip: Whenever you explain a collection, immediately follow with "and here's where I've actually used it in my automation framework" — 
this is what separates a 7-9 year candidate's answer from a fresher's textbook answer. 
Have 3-4 concrete framework examples ready (parallel execution data sharing, deduplication of test data, sorting reports, retry queues).*
