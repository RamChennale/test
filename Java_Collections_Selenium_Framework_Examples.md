# Java Collections in a Selenium Automation Framework
## Practical Examples: Test Data, API Parsing, WebElements, Deduplication, Sorting

Real, usable code patterns for each scenario — the kind you'd actually have in a framework, not toy examples.

---

## 1. Storing Test Data

### 1a. Storing test data as a List of Maps (like rows from an Excel/CSV file)
```java
import java.util.*;

public class TestDataProvider {

    public static List<Map<String, String>> getLoginTestData() {
        List<Map<String, String>> testData = new ArrayList<>();

        Map<String, String> row1 = new LinkedHashMap<>(); // LinkedHashMap preserves column order
        row1.put("username", "admin");
        row1.put("password", "Admin@123");
        row1.put("expectedResult", "success");
        testData.add(row1);

        Map<String, String> row2 = new LinkedHashMap<>();
        row2.put("username", "invalidUser");
        row2.put("password", "wrongPass");
        row2.put("expectedResult", "failure");
        testData.add(row2);

        return testData;
    }
}
```
**Usage in a TestNG @DataProvider:**
```java
@DataProvider(name = "loginData")
public Object[][] loginData() {
    List<Map<String, String>> data = TestDataProvider.getLoginTestData();
    Object[][] result = new Object[data.size()][1];
    for (int i = 0; i < data.size(); i++) {
        result[i][0] = data.get(i);
    }
    return result;
}

@Test(dataProvider = "loginData")
public void testLogin(Map<String, String> row) {
    driver.findElement(By.id("username")).sendKeys(row.get("username"));
    driver.findElement(By.id("password")).sendKeys(row.get("password"));
    // assertion using row.get("expectedResult")
}
```

### 1b. Storing test configuration (environment-specific values) using a Map
```java
Map<String, String> envConfig = new HashMap<>();
envConfig.put("baseUrl", "https://qa.myapp.com");
envConfig.put("browser", "chrome");
envConfig.put("timeout", "30");

String url = envConfig.getOrDefault("baseUrl", "https://default.myapp.com");
```

### 1c. Storing reusable test data as an Enum + Map combo (common in mature frameworks)
```java
public enum UserRole {
    ADMIN("admin@test.com", "Admin@123"),
    GUEST("guest@test.com", "Guest@123");

    final String email;
    final String password;

    UserRole(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

// Usage
UserRole role = UserRole.ADMIN;
driver.findElement(By.id("email")).sendKeys(role.email);
```

---

## 2. Handling API Response Parsing

### 2a. Parsing a JSON array response into a List of POJOs (using RestAssured + Jackson/Gson)
```java
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.List;

class User {
    int id;
    String name;
    String email;
    // getters/setters
}

Response response = RestAssured.get("https://api.myapp.com/users");
List<User> users = response.jsonPath().getList("", User.class); // parses JSON array into List<User>

for (User u : users) {
    System.out.println(u.name + " - " + u.email);
}
```

### 2b. Extracting specific fields from a nested JSON response into a List<String>
```java
// Response JSON: { "orders": [ {"id":1,"status":"shipped"}, {"id":2,"status":"pending"} ] }
Response response = RestAssured.get("https://api.myapp.com/orders");

List<String> statuses = response.jsonPath().getList("orders.status", String.class);
System.out.println(statuses); // [shipped, pending]

// Validate all orders have a valid status using a Set of allowed values
Set<String> validStatuses = Set.of("shipped", "pending", "delivered", "cancelled");
statuses.forEach(status -> Assert.assertTrue(validStatuses.contains(status),
        "Unexpected status: " + status));
```

### 2c. Converting a JSON response into a Map<String, Object> for dynamic/flexible validation
```java
Response response = RestAssured.get("https://api.myapp.com/user/101");
Map<String, Object> userMap = response.jsonPath().getMap("");

System.out.println(userMap.get("name"));
System.out.println(userMap.get("email"));

// Useful when comparing actual API response against expected key-value pairs
Map<String, Object> expected = Map.of("name", "John Doe", "status", "active");
expected.forEach((key, value) ->
        Assert.assertEquals(userMap.get(key), value, "Mismatch for key: " + key));
```

### 2d. Comparing two API responses (regression: old API vs new API) using Sets
```java
List<String> oldApiIds = getOldApiUserIds();  // returns List<String>
List<String> newApiIds = getNewApiUserIds();

Set<String> oldSet = new HashSet<>(oldApiIds);
Set<String> newSet = new HashSet<>(newApiIds);

Set<String> missingInNewApi = new HashSet<>(oldSet);
missingInNewApi.removeAll(newSet); // set difference

Set<String> newlyAddedInNewApi = new HashSet<>(newSet);
newlyAddedInNewApi.removeAll(oldSet);

System.out.println("Missing: " + missingInNewApi);
System.out.println("New: " + newlyAddedInNewApi);
```

---

## 3. Managing WebElements

### 3a. Storing a List<WebElement> from findElements() and iterating
```java
List<WebElement> productCards = driver.findElements(By.className("product-card"));
System.out.println("Total products displayed: " + productCards.size());

for (WebElement product : productCards) {
    System.out.println(product.findElement(By.className("product-name")).getText());
}
```

### 3b. Mapping element text to WebElement using a Map (for quick lookup by name)
```java
List<WebElement> menuItems = driver.findElements(By.cssSelector(".nav-menu li a"));

Map<String, WebElement> menuMap = new LinkedHashMap<>();
for (WebElement item : menuItems) {
    menuMap.put(item.getText().trim(), item);
}

// Now click any menu item by name directly, without re-locating
menuMap.get("Settings").click();
```

### 3c. Storing multiple locator strategies in a Map for a Page Object (fallback locators)
```java
public class LoginPage {
    private Map<String, By> locators = new HashMap<>();

    public LoginPage() {
        locators.put("username", By.id("username"));
        locators.put("password", By.id("password"));
        locators.put("loginBtn", By.cssSelector("button[type='submit']"));
    }

    public void login(String user, String pass) {
        driver.findElement(locators.get("username")).sendKeys(user);
        driver.findElement(locators.get("password")).sendKeys(pass);
        driver.findElement(locators.get("loginBtn")).click();
    }
}
```

### 3d. Collecting visible text of all WebElements into a List<String> for validation
```java
List<WebElement> dropdownOptions = driver.findElements(By.cssSelector("select#country option"));

List<String> optionTexts = new ArrayList<>();
for (WebElement option : dropdownOptions) {
    optionTexts.add(option.getText());
}

// Or using streams (cleaner, Java 8+)
List<String> optionTextsStream = dropdownOptions.stream()
        .map(WebElement::getText)
        .collect(Collectors.toList());

Assert.assertTrue(optionTextsStream.contains("India"));
```

### 3e. Managing multiple WebDriver instances in parallel execution using ConcurrentHashMap
```java
public class DriverManager {
    private static Map<Long, WebDriver> driverMap = new ConcurrentHashMap<>();

    public static void setDriver(WebDriver driver) {
        driverMap.put(Thread.currentThread().getId(), driver);
    }

    public static WebDriver getDriver() {
        return driverMap.get(Thread.currentThread().getId());
    }

    public static void removeDriver() {
        driverMap.remove(Thread.currentThread().getId());
    }
}
```
This is the standard pattern for **thread-safe parallel Selenium execution** — each thread gets its own driver instance, keyed by thread ID.

---

## 4. Deduplication

### 4a. Removing duplicate values from a List while preserving order
```java
List<String> browserLogs = List.of("Chrome started", "Error 404", "Chrome started", "Timeout", "Error 404");

List<String> uniqueLogs = new ArrayList<>(new LinkedHashSet<>(browserLogs));
System.out.println(uniqueLogs); // [Chrome started, Error 404, Timeout]
```

### 4b. Deduplicating a list of custom objects (e.g., test result objects) by a specific field
```java
class TestResult {
    String testName;
    String status;
    TestResult(String testName, String status) { this.testName = testName; this.status = status; }
}

List<TestResult> results = List.of(
    new TestResult("TC1", "Pass"),
    new TestResult("TC2", "Fail"),
    new TestResult("TC1", "Pass") // duplicate by testName
);

// Using a Map keyed by testName to dedupe (last one wins)
Map<String, TestResult> deduped = new LinkedHashMap<>();
for (TestResult r : results) {
    deduped.put(r.testName, r);
}
Collection<TestResult> uniqueResults = deduped.values();
System.out.println(uniqueResults.size()); // 2
```

### 4c. Deduplicating using Java Streams with `distinct()` after overriding equals/hashCode
```java
class Product {
    String sku;
    Product(String sku) { this.sku = sku; }

    @Override
    public boolean equals(Object o) {
        return o instanceof Product && this.sku.equals(((Product) o).sku);
    }
    @Override
    public int hashCode() { return sku.hashCode(); }
}

List<Product> products = List.of(new Product("SKU1"), new Product("SKU2"), new Product("SKU1"));
List<Product> uniqueProducts = products.stream().distinct().collect(Collectors.toList());
System.out.println(uniqueProducts.size()); // 2
```

### 4d. Finding duplicate elements (not just removing them) — useful for data validation
```java
List<String> orderIds = List.of("ORD1", "ORD2", "ORD3", "ORD1", "ORD4", "ORD2");

Set<String> seen = new HashSet<>();
Set<String> duplicates = orderIds.stream()
        .filter(id -> !seen.add(id)) // add() returns false if already present
        .collect(Collectors.toSet());

System.out.println(duplicates); // [ORD1, ORD2]
// Real use case: validating an API doesn't return duplicate order IDs
Assert.assertTrue(duplicates.isEmpty(), "Duplicate order IDs found: " + duplicates);
```

---

## 5. Sorting Test Results

### 5a. Sorting a List of test result objects by execution time (Comparator)
```java
class TestResult {
    String testName;
    long executionTimeMs;
    String status;
    TestResult(String testName, long time, String status) {
        this.testName = testName; this.executionTimeMs = time; this.status = status;
    }
    public String toString() { return testName + "(" + executionTimeMs + "ms," + status + ")"; }
}

List<TestResult> results = new ArrayList<>(List.of(
    new TestResult("TC1", 1200, "Pass"),
    new TestResult("TC2", 300, "Fail"),
    new TestResult("TC3", 800, "Pass")
));

// Sort by execution time ascending
results.sort(Comparator.comparingLong(r -> r.executionTimeMs));
System.out.println(results); // [TC2(300ms,Fail), TC3(800ms,Pass), TC1(1200ms,Pass)]

// Sort by execution time descending — find slowest tests first (performance bottleneck check)
results.sort(Comparator.comparingLong((TestResult r) -> r.executionTimeMs).reversed());
```

### 5b. Multi-level sort — status first (Fail before Pass), then by execution time
```java
results.sort(
    Comparator.comparing((TestResult r) -> r.status)   // Fail, Pass alphabetically -> Fail comes first
              .thenComparingLong(r -> r.executionTimeMs)
);
```

### 5c. Sorting using a custom priority order (not alphabetical) — common for status severity
```java
Map<String, Integer> statusPriority = Map.of("Fail", 1, "Skip", 2, "Pass", 3);

results.sort(Comparator.comparingInt(r -> statusPriority.get(r.status)));
// Ensures failed tests always appear first in the report regardless of alphabetical order
```

### 5d. Sorting test names alphabetically/naturally using Comparable
```java
class TestCase implements Comparable<TestCase> {
    String name;
    TestCase(String name) { this.name = name; }

    @Override
    public int compareTo(TestCase other) {
        return this.name.compareTo(other.name); // natural (alphabetical) order
    }
    public String toString() { return name; }
}

List<TestCase> cases = new ArrayList<>(List.of(
    new TestCase("TC_Login"), new TestCase("TC_Checkout"), new TestCase("TC_AddToCart")
));
Collections.sort(cases); // uses compareTo() since Comparable is implemented
System.out.println(cases); // [TC_AddToCart, TC_Checkout, TC_Login]
```

### 5e. Grouping and counting test results by status (for a summary report) using Streams
```java
List<TestResult> results = List.of(
    new TestResult("TC1", 300, "Pass"),
    new TestResult("TC2", 500, "Fail"),
    new TestResult("TC3", 200, "Pass"),
    new TestResult("TC4", 150, "Skip")
);

Map<String, Long> summary = results.stream()
        .collect(Collectors.groupingBy(r -> r.status, Collectors.counting()));

System.out.println(summary); // {Pass=2, Fail=1, Skip=1}

// Grouping full objects, not just counts (e.g., to list all failed test names in the report)
Map<String, List<TestResult>> groupedResults = results.stream()
        .collect(Collectors.groupingBy(r -> r.status));

List<String> failedTestNames = groupedResults.getOrDefault("Fail", List.of())
        .stream().map(r -> r.testName).collect(Collectors.toList());
System.out.println(failedTestNames); // [TC2]
```

---

## Quick Reference — Which Collection for Which Automation Task

| Task | Collection Used | Why |
|---|---|---|
| Data-driven test rows | `List<Map<String,String>>` | Flexible column structure per row |
| Environment config | `HashMap<String,String>` | Fast key lookup |
| API JSON array → objects | `List<POJO>` | Direct deserialization |
| API response diffing | `HashSet` + `removeAll()` | O(1) set operations |
| WebElements from findElements() | `List<WebElement>` | Preserves DOM order |
| Element lookup by name | `LinkedHashMap<String, WebElement>` | O(1) lookup + order preserved |
| Parallel WebDriver instances | `ConcurrentHashMap<Long, WebDriver>` | Thread-safe, keyed by thread ID |
| Removing dupes, order matters | `LinkedHashSet` | Dedup + insertion order |
| Removing dupes, custom objects | `HashSet` + equals/hashCode, or Stream `.distinct()` | Field-based uniqueness |
| Sorting by one/many fields | `Comparator` (`thenComparing`) | Flexible, external, multiple sort orders |
| Natural single default sort | `Comparable` (`compareTo`) | Built into the class |
| Result summary counts | `Collectors.groupingBy + counting()` | Clean aggregation |

---

*Tip for interviews: When asked "how do you manage WebElements in parallel execution," the strongest answer combines two things — `ConcurrentHashMap` for the driver instance per thread, AND `ThreadLocal<WebDriver>` (an alternative/complementary pattern) which is often considered the more idiomatic Selenium approach. Be ready to mention both and explain the trade-off (ThreadLocal is simpler for driver-per-thread; ConcurrentHashMap is better if you need to look up/manage drivers by an external key like thread ID or test name).*
