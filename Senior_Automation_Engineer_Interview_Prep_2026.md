# Senior Automation Engineer (10+ Years) — Interview Preparation Guide (2026)
### Selenium WebDriver | RestAssured API Automation | Core Java | Coding Programs

---

## PART 1: SELENIUM WEBDRIVER — SENIOR LEVEL Q&A

**1. What's new/relevant in Selenium 4 that interviewers ask in 2026?**
Selenium 4: 1. is fully W3C WebProtocol compliant (no more JSON Wire Protocol translation layer),  which makes browser communication faster and more stable. 
	2. Key additions: relative locators (`above()`, `below()`, `toLeftOf()`, `toRightOf()`, `near()`), 
	3. native Chrome DevTools Protocol (CDP) access for network interception/console logs/geolocation mocking, 
	4. a redesigned Selenium Grid 4 with Hub-Node architecture supporting Docker and Kubernetes, 
	5. native support for multiple windows/tabs via `getWindowHandles()`, and improved Actions API. 
	6. Selenium 4.6+ also introduced **Selenium Manager**, which auto-manages browser driver binaries so you no longer need WebDriverManager 
	(though many teams still use it for finer control). 
	7. As of 2025-2026, **WebDriver BiDi (Bi-Directional)** protocol support is expanding, enabling real-time event subscriptions 
	(console logs, network requests, JS exceptions) — expect questions on this.


**2. Explain the Selenium WebDriver architecture.**
Four components: 
	(1) Selenium Client Library — language bindings (Java, Python, etc.) that convert your code into commands; 
	(2) JSON Wire Protocol / W3C Protocol — standardized communication format sent over HTTP; 
	(3) Browser Drivers (ChromeDriver, GeckoDriver, EdgeDriver) — translate commands into browser-native calls; 
	(4) Browsers — actually execute the actions. Each driver runs an HTTP server that listens for and executes commands.

**3. How do you handle dynamic elements / synchronization issues?**
Never use `Thread.sleep()`. 
	Use **Explicit Waits** (`WebDriverWait` with `ExpectedConditions` like `visibilityOfElementLocated`, `elementToBeClickable`) 
	for element-specific conditions, and 
	**Fluent Wait** when you need custom polling intervals and to ignore specific exceptions (e.g., `NoSuchElementException`) during retries. 
	Set a global **Implicit Wait** conservatively (or avoid mixing it with explicit waits — mixing causes unpredictable timeouts). 
	For SPA apps with AJAX, also consider waiting on JavaScript `document.readyState` or a custom condition polling a network-idle state via CDP.

##SPA apps with AJAX
A Single-Page Application (SPA)-  loads one HTML page and updates its content dynamically using AJAX (Asynchronous JavaScript and XML). 
	Instead of reloading the whole page for new data, AJAX fetches data behind the scenes so the app can change parts of the screen instantly.


**4. How do you design a robust, scalable test automation framework?**
Key components: 
	1. Page Object Model (or Screenplay pattern for larger enterprise suites) for maintainability; 
	2. a Driver factory using ThreadLocal<WebDriver> for thread-safe parallel execution; 
	3. a config/properties management layer (YAML/properties + environment overrides); 
	4. a reporting layer (ExtentReports/Allure); 
	5. TestNG: listeners for screenshots-on-failure (TestNG `ITestListener`); a retry-analyzer for flaky tests; data-driven testing via TestNG DataProviders or external JSON/Excel; 
	6. CI/CD integration (Jenkins/GitHub Actions) with Selenium Grid or cloud grids (BrowserStack/Sauce Labs/LambdaTest) for cross-browser runs; 
	7. and clean separation of test logic from framework utilities.

**5. How do you ensure thread safety during parallel execution?**
Use `ThreadLocal<WebDriver>` to give each test thread its own driver instance instead of a static/shared driver — a shared driver is the cause of flaky parallel suites. 
	Configure parallel execution at the `<suite>` level in `testng.xml` (`parallel="methods"` or `"classes"`), and 
	ensure no shared mutable state (static variables, shared test data files) between threads.

**6. How do you handle Shadow DOM elements?**
Selenium 4 provides native support via `WebElement.getShadowRoot()`, which returns a `SearchContext` you can query with `findElement`/`findElements` 
	just like the regular DOM. For nested shadow roots, you traverse them recursively.

**7. How do you handle iframes and multiple windows/tabs?**
For iframes: `driver.switchTo().frame(index/name/WebElement)`, then `driver.switchTo().defaultContent()` to return. 
For windows: `driver.getWindowHandles()` (a `Set<String>`) before triggering the new window, then iterate and `switchTo().window(handle)` 
for the one not matching the original handle.

**8. What's the difference between `findElement` and `findElements`? How do you avoid `NoSuchElementException`?**
`findElement` throws `NoSuchElementException` if no match is found; `findElements` returns an empty list instead. 
	To safely check existence, use `findElements(...).size() > 0` or wrap in an explicit wait condition rather than try-catch for every check
	(try-catch is expensive and hides real failures if overused).

**9. How do you handle file upload/download in Selenium?**
For upload: if the `<input type="file">` is present in DOM (not OS-native dialog), 
	use `sendKeys(absoluteFilePath)` directly — no need for AutoIT/Robot class. 
	For OS-level dialogs (rare in modern web apps), use `java.awt.Robot` or a tool like Sikuli. 
	For download: configure the browser profile (Chrome options `download.default_directory`) to auto-download to a known folder without a prompt,
	then verify file existence/content programmatically.

**10. Explain the Page Object Model vs Page Factory. Which do you prefer and why?**
	**POM structures** each page as a class with locators + methods, decoupling test logic from UI structure. 
	**Page Factory** (`@FindBy` annotations + `PageFactory.initElements()`) adds lazy initialization of elements via Java proxies. 
	In 2026, many senior engineers actually **avoid Page Factory** because `@FindBy` elements are cached by default(causing `StaleElementReferenceException` 
	on SPA re-renders) unless you wrap the driver in an `AjaxElementLocatorFactory`. 
	Many teams prefer plain POM with `By` locators resolved fresh on each interaction, or move to the **Screenplay Pattern** 
	for very large suites since it favors composition over inheritance and scales better with multiple actors/roles.

**11. How do you deal with `StaleElementReferenceException`?**
	It happens when the DOM element reference becomes invalid (page reload, AJAX re-render, DOM manipulation). 
	Fix by re-locating the element right before interaction rather than caching it, 
	wrapping the action in a small retry loop that catches `StaleElementReferenceException` and re-finds the element, 
	or using explicit waits tied to `ExpectedConditions.refreshed(...)`.

**12. How do you integrate Selenium with CI/CD, and how do you run at scale?**
Trigger suites via Jenkins/GitHub Actions/GitLab CI pipelines on PR merge or nightly schedule. 
	Use a Dockerized Selenium Grid (Hub + Node containers) or cloud grid providers for scale and true cross-browser/cross-OS coverage. 
	Parallelize via TestNG suite XML or JUnit5 parallel execution, and publish Allure/ExtentReports as pipeline artifacts. 
	For flaky-test control, integrate a retry analyzer and quarantine flaky tests into a separate CI stage so they don't block the pipeline.

**13. How do you take screenshots on test failure automatically?**
Implement `ITestListener.onTestFailure()` (TestNG) or a JUnit5 `TestWatcher` extension that calls `((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)` 
	and attaches it to the report (Extent/Allure) with the test name and timestamp.

**14. How do you handle CAPTCHA, OTP, or third-party auth in automation?**
Best practice: request the dev/QA team to disable CAPTCHA in test environments, or use test-specific bypass tokens/API hooks. For OTP, use test accounts with a fixed OTP or intercept via an email/SMS testing API (e.g., Mailosaur, Twilio test credentials). Never try to "solve" CAPTCHA programmatically — that's both fragile and against most CAPTCHA providers' terms.

**15. Selenium vs Playwright vs Cypress — how do you evaluate for a new project in 2026?**
Selenium: broadest language/browser support, mature ecosystem, best for large legacy Java-based orgs and Grid-based cross-browser/cross-device labs. Playwright: built-in auto-waiting, faster execution, native multi-tab/multi-context support, strong for modern SPAs, growing fast in enterprise adoption. Cypress: great DX for JS-heavy teams, but historically limited to single-tab/single-origin (improving) and JS/TS only. Decision criteria: existing team skillset, language standardization needs (Java-heavy backend teams often stick with Selenium+RestAssured for a unified stack), required browser/device matrix, and CI parallelization needs.

**16. How do you make your framework's locators resilient to UI changes?**
Prefer stable attributes (`data-testid`, `id`) over brittle XPath/CSS chains; collaborate with dev teams to add automation-friendly hooks; centralize locators in POM classes so a UI change requires a single-point update; consider self-healing locator tools (Healenium) for large legacy suites where element churn is high.

**17. How do you debug a flaky Selenium test in a CI pipeline?**
Reproduce locally with same browser version/headless mode; check for race conditions (missing waits); check for shared state across parallel threads; enable video recording/screenshot-on-failure in CI; check for environment-specific timing (CI machines are often slower); isolate and run the test repeatedly (e.g., 20x) to measure the actual flake rate before "fixing" it; use CDP network throttling to simulate CI network conditions locally.

**18. What design patterns do you commonly apply in a Selenium framework besides POM?**
Singleton (WebDriver instance management, though ThreadLocal-backed for parallel-safety), Factory (Browser factory to instantiate the right driver based on config), Builder (fluent test-data builders), Strategy (swap wait strategies or reporting strategies at runtime), and Listener/Observer (TestNG listeners for reporting/screenshots).

---

## PART 2: RESTASSURED / API AUTOMATION — SENIOR LEVEL Q&A

**1. What is RestAssured and why is it preferred for Java-based API automation?**
RestAssured is a Java DSL (domain-specific language) library for testing REST APIs. It's preferred because its BDD-style syntax (`given().when().then()`) is highly readable, it integrates natively with TestNG/JUnit and Java's Hamcrest matchers for assertions, and it fits naturally into an existing Selenium+Java framework, letting teams maintain one language/stack for both UI and API layers.

**2. Explain the `given-when-then` structure.**
`given()` sets up the request context — headers, params, body, auth, base URI. `when()` specifies the HTTP action (`.get()`, `.post()`, etc.) and the endpoint. `then()` validates the response — status code, headers, body content, response time — using Hamcrest matchers or JSON path assertions.

**3. How do you validate a JSON response body?**
Using `.body("path", Matchers.equalTo(value))` with JSON Path syntax, e.g., `.body("data.id", equalTo(101))` or `.body("data.items.size()", greaterThan(0))`. For complex structures, deserialize into a POJO via `.as(MyPojo.class)` and assert with standard Java assertions, or validate the entire payload against a **JSON Schema** using `matchesJsonSchemaInClasspath("schema.json")` from the `json-schema-validator` module.

**4. How do you handle authentication in RestAssured?**
Basic Auth: `.auth().preemptive().basic(user, pass)`. Bearer/OAuth2 token: `.header("Authorization", "Bearer " + token)` or `.auth().oauth2(token)`. For flows requiring a token fetched from a login endpoint first, chain requests: call the auth endpoint, extract the token via `.extract().path("token")`, then reuse it in a `RequestSpecification` for subsequent calls.

**5. How do you design a reusable API automation framework?**
Centralize `RequestSpecification` and `ResponseSpecification` builders (base URI, common headers, logging) so tests don't repeat boilerplate. Use POJOs with Jackson/Gson for serialization/deserialization of request and response bodies. Separate test data (JSON/YAML files or builders) from test logic. Add a reusable `ApiClient`/`ServiceClass` layer per microservice/resource (similar to Page Objects in UI automation) so tests call `userService.createUser(payload)` rather than raw RestAssured calls. Integrate schema validation, retry/backoff for flaky endpoints, and structured logging (`.log().ifValidationFails()`) to keep CI logs clean but informative.

**6. How do you test PUT vs PATCH vs POST correctly, and what's the difference?**
POST creates a new resource (not idempotent — calling it twice creates two resources). PUT replaces an entire resource and is idempotent (calling it multiple times with the same payload yields the same result). PATCH partially updates a resource and may or may not be idempotent depending on implementation. In tests, validate idempotency explicitly for PUT (fire twice, assert same end state) and validate partial-field updates don't null out unspecified fields for PATCH.

**7. How do you chain dependent API calls (e.g., create order → get order ID → fetch order details)?**
Extract needed values from one response (`.extract().response()` then `.jsonPath().getString("id")`) and feed them into the next request as a path/query parameter or request body field. For complex chains, wrap this in a reusable workflow/service method rather than repeating extraction logic inline in every test.

**8. How do you validate response time / performance SLAs in functional API tests?**
`.then().time(lessThan(2000L), TimeUnit.MILLISECONDS)` — RestAssured supports basic response-time assertions inline. For deeper performance testing, this is complemented (not replaced) by dedicated load-testing tools like JMeter, Gatling, or k6 — functional API automation should only sanity-check SLA at the individual-request level.

**9. How would you test a GraphQL API with RestAssured?**
GraphQL typically uses a single POST endpoint with the query/mutation in the request body. You send `.body("{ \"query\": \"{ user(id: 1) { name email } }\" }")` (or better, load from a `.graphql`/JSON file) to the `/graphql` endpoint, then assert on the `data` node of the JSON response, and separately assert the `errors` node is absent/empty for success cases.

**10. How do you mock external dependencies in API testing?**
Use WireMock (or similar) to stub downstream service responses so tests aren't dependent on flaky third-party services, and to simulate edge cases (500 errors, timeouts, malformed responses) that are hard to trigger against a real service. WireMock can run as a JUnit5 extension or a standalone server in CI.

**11. How do you handle multipart file upload/download testing?**
Multipart upload: `.multiPart("file", new File(path), "application/octet-stream")` combined with other form fields via `.multiPart("field", value)`, using `.contentType("multipart/form-data")`. Download: fetch the response as a byte stream (`.asByteArray()` or `.asInputStream()`) and validate content length/checksum or save and compare against an expected file.

**12. What HTTP status codes and scenarios do you always cover in a thorough API test suite?**
2xx (200 OK, 201 Created, 204 No Content) for happy paths; 4xx (400 Bad Request for invalid payload, 401 Unauthorized, 403 Forbidden, 404 Not Found, 409 Conflict for duplicate resources, 422 Unprocessable Entity for validation errors); 5xx simulated via mocking for resilience testing. Also test boundary/negative cases: missing required fields, wrong data types, oversized payloads, SQL injection/XSS payloads for security sanity, and rate-limiting (429) behavior.

**13. How do you integrate API tests into a CI/CD pipeline alongside UI tests?**
Keep API and UI suites as separate TestNG/JUnit test groups/tags so API tests (fast, no browser) run early in the pipeline as a fast-feedback gate, and full UI regression runs afterward or in parallel on a Grid. Fail-fast on API contract breaks before spending time on browser-based tests that would be pointless if backend contracts are broken.

**14. How do you do contract testing, and how does it differ from regular API testing?**
Regular API testing validates actual runtime behavior end-to-end. Contract testing (e.g., Pact) validates that a producer and consumer agree on a request/response schema/contract independently, without both being deployed together — useful in microservice architectures to catch breaking changes early, in CI, before integration environments are even involved.

**15. How do you manage test data and environment configuration across dev/QA/staging?**
Externalize base URIs, credentials, and environment-specific data into config files (`.properties`/YAML) selected via a system property or environment variable (`-Denv=qa`), never hardcoded. Use a config loader singleton. For test data, prefer creating/tearing down data via API calls in setup/teardown (self-contained tests) over relying on pre-seeded environment data, which causes cross-test pollution and flakiness.

---

## PART 3: CORE JAVA FOR AUTOMATION FRAMEWORK DESIGN

**1. Why are Collections so important in framework design — when do you use List vs Set vs Map?**
`List` (ArrayList/LinkedList) — ordered, allows duplicates; use for test data sequences, step lists. `Set` (HashSet/LinkedHashSet/TreeSet) — no duplicates; use for unique element collections like deduplicating locator sets or window handles. `Map` (HashMap/LinkedHashMap/TreeMap/ConcurrentHashMap) — key-value pairs; use for config lookups, test data keyed by scenario ID, or caching driver instances per thread. `ConcurrentHashMap` specifically matters for thread-safe parallel test execution.

**2. Explain `ThreadLocal` and why it's critical in parallel Selenium/API test execution.**
`ThreadLocal<T>` gives each thread its own isolated copy of a variable, so in parallel execution, `ThreadLocal<WebDriver>` ensures Thread A's driver instance never collides with Thread B's. Without it, a shared static driver causes one thread's browser actions to interfere with another's, producing unpredictable failures.

**3. Explain Java 8+ Streams and give an automation-relevant example.**
Streams allow functional-style operations (filter/map/reduce) on collections. Example: filtering only failed test results from a list of `TestResult` objects — `results.stream().filter(r -> r.getStatus() == FAILED).collect(Collectors.toList())` — or extracting all `href` attributes from a list of WebElements via `.stream().map(WebElement::getAttribute).collect(Collectors.toList())`.

**4. What's the difference between checked and unchecked exceptions, and how do you handle them in a framework?**
Checked exceptions (e.g., `IOException`) must be declared or caught at compile time; unchecked (`RuntimeException` subclasses, e.g., `NullPointerException`) aren't enforced by the compiler. In frameworks, wrap common checked exceptions (like file I/O in config loading) into custom unchecked exceptions so calling test code doesn't need boilerplate try-catch everywhere, while still logging the root cause.

**5. Explain `equals()` vs `==`, and why it matters for assertions.**
`==` compares object references (memory location) for objects, or actual values for primitives. `.equals()` compares logical/content equality (when properly overridden, as in `String`, or custom POJOs). In assertions, always use `.equals()` (or assertion library methods that use it internally) for object comparison — using `==` on two separately-constructed `String` or POJO objects with identical content can silently give a false negative.

**6. What are functional interfaces and lambda expressions, and where would you use them in a framework?**
A functional interface has exactly one abstract method (e.g., `Runnable`, `Predicate<T>`, `Supplier<T>`) and can be implemented via a lambda. Useful for custom wait conditions (`Supplier<Boolean>` polling logic), retry logic (`Supplier<T>` wrapped in a generic retry utility), or simplifying `ExpectedConditions`-style custom conditions.

**7. Explain the Singleton pattern and why "double-checked locking" matters for a WebDriver factory.**
Singleton ensures a class has only one instance. In multi-threaded execution, naive singletons (`if (instance == null) instance = new Driver()`) can create multiple instances due to race conditions. Double-checked locking with a `volatile` field avoids that overhead while remaining thread-safe — though in practice, most frameworks now prefer `ThreadLocal` over a true cross-thread Singleton for driver management, since each thread genuinely needs its own driver.

**8. Interface vs Abstract class — when do you choose which in framework design?**
Interfaces define a contract with no state, and a class can implement multiple interfaces (multiple inheritance of type) — good for defining a `Page` contract or `ApiService` contract. Abstract classes can hold shared state/implementation and are extended by only one parent — good for a `BasePage` or `BaseTest` class holding common setup/teardown logic. Since Java 8, interfaces can also have default methods, blurring the line somewhat, but state and constructors still require an abstract/concrete class.

**9. Explain `final`, `finally`, and `finalize()`.**
`final` — keyword marking a variable as constant, a method as non-overridable, or a class as non-extendable. `finally` — a block that always executes after try/catch, used for guaranteed cleanup (e.g., closing a driver or file handle). `finalize()` — a deprecated `Object` method historically called by the GC before object collection; not reliable and removed/discouraged in modern Java — use `try-with-resources` or explicit cleanup instead.

**10. What is the String pool, and why does it matter for test data comparisons?**
Java caches string literals in a special memory area (the String pool) to save memory — two literals with the same value point to the same object. Strings created via `new String("x")` bypass the pool and create a new object. This is exactly why `==` is unreliable for string comparison and `.equals()` must be used.

**11. Explain immutability and why `String` is immutable in Java.**
An immutable object's state cannot change after construction. `String` is immutable for security (safe use in class loading, network connections), thread-safety (safely shared across threads without synchronization), and to support the String pool optimization. In framework design, favor immutable objects for config/test-data classes when possible to avoid accidental shared-state bugs in parallel execution.

**12. What's the difference between `HashMap`, `LinkedHashMap`, and `TreeMap`?**
`HashMap` — no ordering guarantee, O(1) average access. `LinkedHashMap` — maintains insertion order, useful when you need deterministic iteration (e.g., ordered test step execution log). `TreeMap` — sorted by natural/comparator order, O(log n) access, useful when you need keys in sorted order (e.g., sorted test IDs).

**13. Explain multithreading basics relevant to running tests in parallel.**
A `Thread` is a unit of execution; the JVM can run multiple threads concurrently. TestNG/JUnit5 leverage a thread pool to execute test methods/classes in parallel. Key concerns: shared mutable state (avoid static non-thread-local fields), synchronization overhead (avoid oversynchronizing, which serializes execution and defeats the purpose of parallelism), and proper resource cleanup per thread (drivers, API clients) to avoid leaks/zombie browser processes in CI.

**14. What are generics, and why are they used in framework utility classes?**
Generics allow type-safe, reusable code, e.g., a generic `RetryUtil<T>` that retries any `Supplier<T>` operation, or a generic `ApiResponse<T>` wrapper class used across multiple endpoint response types without duplicating boilerplate per type.

**15. JVM vs JRE vs JDK — quick distinction.**
JVM (Java Virtual Machine) — executes bytecode, platform-specific. JRE (Java Runtime Environment) — JVM + core libraries needed to *run* Java apps. JDK (Java Development Kit) — JRE + compiler (`javac`) and dev tools needed to *build* Java apps.

---

## PART 4: JAVA CODING PROGRAMS — ARRAYS

```java
// 1. Reverse an array in-place
public static void reverseArray(int[] arr) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
        left++;
        right--;
    }
}

// 2. Find the second largest element in an array
public static int secondLargest(int[] arr) {
    int largest = Integer.MIN_VALUE, second = Integer.MIN_VALUE;
    for (int num : arr) {
        if (num > largest) {
            second = largest;
            largest = num;
        } else if (num > second && num != largest) {
            second = num;
        }
    }
    return second;
}

// 3. Find all duplicate elements in an array
public static List<Integer> findDuplicates(int[] arr) {
    Set<Integer> seen = new HashSet<>();
    Set<Integer> duplicates = new LinkedHashSet<>();
    for (int num : arr) {
        if (!seen.add(num)) {
            duplicates.add(num);
        }
    }
    return new ArrayList<>(duplicates);
}

// 4. Find the missing number in an array of 1..n
public static int findMissingNumber(int[] arr, int n) {
    int expectedSum = n * (n + 1) / 2;
    int actualSum = 0;
    for (int num : arr) actualSum += num;
    return expectedSum - actualSum;
}

// 5. Rotate an array left by k positions
public static void rotateLeft(int[] arr, int k) {
    int n = arr.length;
    k = k % n;
    reverse(arr, 0, k - 1);
    reverse(arr, k, n - 1);
    reverse(arr, 0, n - 1);
}
private static void reverse(int[] arr, int start, int end) {
    while (start < end) {
        int temp = arr[start];
        arr[start] = arr[end];
        arr[end] = temp;
        start++;
        end--;
    }
}

// 6. Two Sum — find indices of two numbers that add up to target
public static int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) {
            return new int[] { map.get(complement), i };
        }
        map.put(nums[i], i);
    }
    throw new IllegalArgumentException("No two sum solution");
}

// 7. Maximum subarray sum — Kadane's Algorithm
public static int maxSubArraySum(int[] arr) {
    int maxSoFar = arr[0], maxEndingHere = arr[0];
    for (int i = 1; i < arr.length; i++) {
        maxEndingHere = Math.max(arr[i], maxEndingHere + arr[i]);
        maxSoFar = Math.max(maxSoFar, maxEndingHere);
    }
    return maxSoFar;
}

// 8. Intersection of two arrays (unique common elements)
public static int[] intersection(int[] arr1, int[] arr2) {
    Set<Integer> set1 = new HashSet<>();
    for (int n : arr1) set1.add(n);
    Set<Integer> result = new LinkedHashSet<>();
    for (int n : arr2) {
        if (set1.contains(n)) result.add(n);
    }
    return result.stream().mapToInt(Integer::intValue).toArray();
}

// 9. Move all zeros to the end while preserving order of non-zeros
public static void moveZerosToEnd(int[] arr) {
    int insertPos = 0;
    for (int num : arr) {
        if (num != 0) arr[insertPos++] = num;
    }
    while (insertPos < arr.length) {
        arr[insertPos++] = 0;
    }
}

// 10. Find a pair with a given sum (print all pairs)
public static void findPairsWithSum(int[] arr, int target) {
    Set<Integer> seen = new HashSet<>();
    for (int num : arr) {
        int complement = target - num;
        if (seen.contains(complement)) {
            System.out.println("Pair: (" + complement + ", " + num + ")");
        }
        seen.add(num);
    }
}
```

---

## PART 5: JAVA CODING PROGRAMS — STRINGS

```java
// 1. Reverse a string without using built-in reverse()
public static String reverseString(String str) {
    char[] chars = str.toCharArray();
    int left = 0, right = chars.length - 1;
    while (left < right) {
        char temp = chars[left];
        chars[left] = chars[right];
        chars[right] = temp;
        left++;
        right--;
    }
    return new String(chars);
}

// 2. Check if a string is a palindrome
public static boolean isPalindrome(String str) {
    String clean = str.toLowerCase().replaceAll("[^a-z0-9]", "");
    int left = 0, right = clean.length() - 1;
    while (left < right) {
        if (clean.charAt(left) != clean.charAt(right)) return false;
        left++;
        right--;
    }
    return true;
}

// 3. Count vowels and consonants in a string
public static void countVowelsConsonants(String str) {
    int vowels = 0, consonants = 0;
    String vowelSet = "aeiouAEIOU";
    for (char c : str.toCharArray()) {
        if (Character.isLetter(c)) {
            if (vowelSet.indexOf(c) != -1) vowels++;
            else consonants++;
        }
    }
    System.out.println("Vowels: " + vowels + ", Consonants: " + consonants);
}

// 4. Find the first non-repeated character in a string
public static Character firstNonRepeatedChar(String str) {
    Map<Character, Integer> freq = new LinkedHashMap<>();
    for (char c : str.toCharArray()) {
        freq.put(c, freq.getOrDefault(c, 0) + 1);
    }
    for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
        if (entry.getValue() == 1) return entry.getKey();
    }
    return null;
}

// 5. Check if two strings are anagrams
public static boolean areAnagrams(String s1, String s2) {
    if (s1.length() != s2.length()) return false;
    char[] arr1 = s1.toLowerCase().toCharArray();
    char[] arr2 = s2.toLowerCase().toCharArray();
    Arrays.sort(arr1);
    Arrays.sort(arr2);
    return Arrays.equals(arr1, arr2);
}

// 6. Remove duplicate characters from a string (preserve first-seen order)
public static String removeDuplicates(String str) {
    StringBuilder sb = new StringBuilder();
    Set<Character> seen = new LinkedHashSet<>();
    for (char c : str.toCharArray()) {
        if (seen.add(c)) sb.append(c);
    }
    return sb.toString();
}

// 7. Convert a string to an integer without Integer.parseInt() (atoi)
public static int stringToInt(String str) {
    str = str.trim();
    int sign = 1, index = 0, result = 0;
    if (str.charAt(0) == '-' || str.charAt(0) == '+') {
        sign = str.charAt(0) == '-' ? -1 : 1;
        index++;
    }
    while (index < str.length() && Character.isDigit(str.charAt(index))) {
        result = result * 10 + (str.charAt(index) - '0');
        index++;
    }
    return result * sign;
}

// 8. Character frequency count in a string
public static Map<Character, Integer> charFrequency(String str) {
    Map<Character, Integer> freq = new LinkedHashMap<>();
    for (char c : str.toCharArray()) {
        freq.put(c, freq.getOrDefault(c, 0) + 1);
    }
    return freq;
}

// 9. Longest substring without repeating characters (sliding window)
public static int longestUniqueSubstring(String str) {
    Set<Character> window = new HashSet<>();
    int left = 0, maxLen = 0;
    for (int right = 0; right < str.length(); right++) {
        char c = str.charAt(right);
        while (window.contains(c)) {
            window.remove(str.charAt(left));
            left++;
        }
        window.add(c);
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}

// 10. Print all permutations of a string
public static void permute(String str, String prefix) {
    if (str.isEmpty()) {
        System.out.println(prefix);
        return;
    }
    for (int i = 0; i < str.length(); i++) {
        String remaining = str.substring(0, i) + str.substring(i + 1);
        permute(remaining, prefix + str.charAt(i));
    }
}
```

---

## PART 6: CORE JAVA — QUICK-FIRE BASICS (COMMONLY ASKED IN 2026)

| # | Question | Concise Answer |
|---|----------|-----------------|
| 1 | Why is Java "platform independent"? | Java source compiles to bytecode (.class), which runs on any OS via a platform-specific JVM — "write once, run anywhere." |
| 2 | What is method overloading vs overriding? | Overloading — same method name, different parameter list, resolved at compile time (compile-time/static polymorphism). Overriding — subclass redefines a parent method with the same signature, resolved at runtime (runtime/dynamic polymorphism). |
| 3 | What is the difference between `ArrayList` and `LinkedList`? | `ArrayList` — backed by a dynamic array, O(1) random access, O(n) insert/delete in the middle. `LinkedList` — doubly linked list, O(1) insert/delete at ends, O(n) random access. |
| 4 | What is the difference between abstract class and interface (Java 8+)? | Abstract class can have constructors, instance state, and both abstract/concrete methods; a class extends only one. Interfaces can have default/static methods (Java 8+) and constants only; a class can implement many. |
| 5 | What are the four pillars of OOP? | Encapsulation, Abstraction, Inheritance, Polymorphism. |
| 6 | What is the difference between `throw` and `throws`? | `throw` actually throws an exception instance at runtime; `throws` is a method-signature declaration listing exceptions the method may propagate. |
| 7 | What is a `try-with-resources` statement? | A try block that auto-closes any resource implementing `AutoCloseable` (e.g., file streams, DB connections) at the end of the block, without needing an explicit `finally`. |
| 8 | Explain the Java Memory Model briefly (Stack vs Heap). | Stack stores method call frames and local primitive/reference variables (thread-specific, LIFO). Heap stores actual objects (shared across threads, garbage collected). |
| 9 | What is garbage collection, and can you force it? | Automatic reclamation of memory occupied by unreachable objects. `System.gc()` only *suggests* to the JVM to run GC — it's not guaranteed or typically relied on in production/test code. |
| 10 | What's the difference between `Comparable` and `Comparator`? | `Comparable` — defines a class's single natural ordering via `compareTo()`, implemented by the class itself. `Comparator` — defines external, custom ordering(s) via `compare()`, useful when you need multiple sort strategies without modifying the class. |
| 11 | What is the diamond problem, and how does Java avoid it? | Ambiguity when a class inherits conflicting implementations from two parents via multiple inheritance. Java avoids it by disallowing multiple class inheritance; for interface default methods with conflicts, the implementing class must explicitly override and resolve it. |
| 12 | What is a `volatile` keyword used for? | Ensures a variable's value is always read from/written to main memory (not a thread's local CPU cache), guaranteeing visibility of updates across threads — commonly used in double-checked-locking singleton patterns. |
| 13 | Difference between `synchronized` method and block? | A synchronized method locks the entire method on the object's monitor; a synchronized block locks only a specific critical section, offering finer-grained control and better performance. |
| 14 | What's new/relevant across recent Java versions (8 → 21+) that interviewers probe on? | Java 8: lambdas, streams, Optional, default methods. Java 9: modules. Java 10/11: `var` local type inference, new `String`/`Collection` methods. Java 14/16: records, pattern matching for `instanceof`. Java 17 (LTS): sealed classes. Java 21 (LTS): virtual threads (Project Loom — highly relevant for scaling parallel test execution cheaply), pattern matching for switch. Expect at least one question in 2026 interviews on **virtual threads** and how they could simplify massively parallel test/API execution. |
| 15 | What is `Optional`, and why use it? | A container object that may or may not hold a non-null value, used to avoid `NullPointerException` and make "value might be absent" explicit in method signatures/return types, rather than relying on null checks scattered through code. |

---

## Quick Interview Tips for a 10-Year Senior Automation Engineer Role (2026)
- Be ready to **whiteboard/verbally walk through framework architecture** end-to-end (driver management, reporting, CI, retry logic) — this matters more at senior level than any single coding trick.
- Expect **leadership/process questions**: how you mentor juniors, how you decide build-vs-buy for tooling, how you drove flaky-test reduction, how you sold automation ROI to stakeholders.
- Be ready to discuss **AI-assisted testing** trends (self-healing locators, AI-generated test cases, Copilot-assisted script maintenance) since these are now standard 2026 interview topics for senior roles.
- Practice explaining trade-offs, not just definitions — interviewers at this level probe "why" and "when," not just "what."

