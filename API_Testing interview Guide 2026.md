##What is an API?
APIs are mechanisms that enable two software components to communicate with each other using a set of definitions and protocols. 
	For example, the weather software system contains daily weather data. 
	The weather app on your phone “talks” to this weather software system via APIs and shows you daily weather updates on your phone.
	
##How do APIs work?
API architecture is of client and server. 
	***Cient*** The application sending the request is called the client, and 
	***Server***the application sending the response is called the server. 
	So in the weather example, the weather database is the server, and the mobile app is the client. 

##There are four different ways that APIs can work depending on when and why they were created.

***1. REST APIs -- Representational State Transfer - HTTP 
REST (Representational State Transfer): REST is a lightweight and flexible API architecture that uses 
	1. HTTP methods such as GET, POST, PUT, and DELETE to enable communication between client & server and exchange data using HTTP.
	2. These are the most popular and flexible APIs found on the web today. 
	3. The client sends requests to the server as data, exchanges data in JSON/XML/Text format. 
	4. The server uses this client input to start internal functions and returns output data back to the client. 
	

***2. SOAP APIs -- Simple Object Access Protocol 
These APIs use SOAP is a protocol-based API architecture (Simple Object Access Protocol). 
	1. Client and server exchange messages using XML. 
	2. This is a less flexible API compare to REST. as it .
	3. It follows strict standards and is commonly used in enterprise systems

***3. RPC APIs -- Remote Procedure Calls - TCP or UDP, and application protocols like HTTP/1.1, HTTP/2
These APIs are called Remote Procedure Calls. 
	1. The client completes a function (or procedure) on the server, and the server sends the output back to the client.

***4. Websocket APIs - TCP
Websocket API is another modern web API development that uses JSON objects to pass data. 
	1. A WebSocket API supports two-way communication between client apps and the server live streaming exchange of events/communication. 
	2. The server can send callback messages to connected clients, making it more efficient than REST API.
	(callback messages: a callback is an active message sent by the server to give new data or notify a client about an event)

#Why WebSockets Are Stateful ??
1. Persistent Connection: 	The connection stays open continuously until one side explicitly closes it.
2. Context Retention:		The server remembers exactly who the client is after the initial handshake.
3. No Re-authentication: 	The client does not need to send authentication tokens (like JWTs) or headers with every single message.
4. Bi-directional Flow: 	Either side can send data at any time without waiting for a request.




#What are the different types of APIs?
APIs are classified both according to their architecture and scope of use. 
We have already explored the main types of API architectures so let’s take a look at the scope of use.

***1. Private APIs
These are internal to an enterprise and only used for connecting systems and data within the business.

***2. Public APIs 
These are open to the public and may be used by anyone. There may or not be some authorization and cost associated with these types of APIs.
Like whether API and AQI - air quality index API
 
***3. Partner APIs 
These are only accessible by authorized external developers to aid business-to-business partnerships - 
like google map for Ola/Uber

***4. Composite APIs 
These combine two or more different APIs to address complex system requirements or behaviors. 
like WMAP - orchestration API service, which commincates mutiple downstream domain to fecth /clientDetails/ 

#What is an API endpoint and why is it important?
	API endpoints are the final touchpoints in the API communication system. 
	These include server URLs, services, and other specific digital locations from where information is sent and received between systems. 
	API endpoints are critical to enterprises for two main reasons: 
1. Security
API endpoints make the system vulnerable to attack. API monitoring is crucial for preventing misuse.

2. Performance
API endpoints, especially high traffic ones, can cause bottlenecks and affect system performance.

#API Testing Tools
1. Postman: A popular tool used to send API requests, test endpoints, and automate API test cases.
2. Swagger / OpenAPI: Used for designing, documenting, and testing REST APIs through interactive API documentation.
3. REST Assured: A Java-based library used for automating REST API testing and validating responses.
4. SoapUI: A testing tool for SOAP and REST APIs that supports functional, security, and load testing.

#API testing kind of validation need to verify duing API testing  

1.API testing focus is on validating server responses. API testing includes:
2.Making multiple requests to API endpoints for performance testing.
3.Rate: The number of requests your API receives per second.
4.Errors: The number of failed requests. You should categorize these by HTTP status codes:200, 400 and 500 
5.Schema Validation: Monitor whether the API responses strictly match your structural OpenAPI/Swagger documentation during automated test runs.
6.Duration (Latency): How long it takes to process a request. Slow response times damage user experience and tie up server resources

#What is an API gateway?
An API Gateway is an API management tool for enterprise clients that use a broad range of back-end services. 
API gateways typically handle common tasks like 
	1. User authentication, 
	2. statistics, and 
	3. Rate management that are applicable across all API calls.

##Importance of APIs
APIs are critical for building scalable, flexible, and connected software systems. 
	They allow different applications to communicate, share data, and use existing services efficiently.

Reusability: APIs allow developers to use existing functionalities instead of building everything from scratch.
Efficiency:  APIs reduce development time by providing ready-made features and services that can be easily integrated.
Scalability: APIs support modular and distributed systems, making it easier to expand applications as business requirements grow.
Integration: APIs enable seamless communication between different platforms such as web applications, mobile apps, IoT devices, and analytics systems.
Automation:  APIs allow systems to exchange data and perform tasks automatically without manual intervention.


##PUT and PATCH 
PUT and PATCH are used to update data in a REST API, but the core difference lies in how they update it: 
	PUT performs a complete replacement of the resource, 
	while PATCH applies a partial modification
	
HTTP PUT request is used to replace and update the entire resource or document, 
while the PATCH request only updates the specific parts of that document.


##Query param and Pathparam in API testing


Path Parameters are used to identify a specific resource,
Query Parameters are used to sort, filter, or paginate a collection of resources


Path Parameter (PathParam) 
	Primary PurposeTo identify a unique resource
	URL Syntax: Embedded directly into the path (/resource/value).
	Data Scope: Typically returns a single item/object.

Query Parameter (QueryParam).
	To filter, sort, or paginate resources.
	URL Syntax:Appends after a question mark (/resource?key=value).
	Data Scope : Typically returns a list/array of items.


##Critical Testing Scenarios to Validate When designing your test cases,
##Query param 
1.:Missing Path Parameter: Test sending a request without the path variable (e.g., GET /products/). 
Assert that the application safely rejects it with a 404 Not Found or 400 Bad Request

:2. Pass a string into a numeric path parameter (e.g., /products/abc instead of /products/123). Verify error handling.

##Pathparam

3. Test empty query values (e.g., ?category=) or extreme pagination limits (e.g., ?limit=999999) to ensure the backend does not crash.
4. Array Response Structure: Assert that using query parameters returns a JSON list [], 
	where as targeting a path parameter returns a single JSON object {}



# API Testing Guide: Authentication, Status Codes & Interview Q&A

## 1. API Authentication Types

APIs need to verify *who* is calling them. Here are the common methods (matching what you see in the Postman Authorization dropdown):

### No Auth
No credentials sent. Used for public, open APIs (e.g., free weather APIs).

#1. Basic Auth
Username and password are combined (`username:password`), Base64-encoded, and sent in the `Authorization` header as `Basic <encoded-string>`.
- **Pros:** Simple to implement.
- **Cons:** Not encrypted by Base64 (it's encoding, not encryption) — must be used over HTTPS. Credentials sent on every request.

#2.Bearer Token
A token (often from a login/auth step) is sent as `Authorization: Bearer <token>`.
- The server trusts whoever "bears" the token — no username/password needed per request.
- Common with OAuth2 access tokens, API session tokens, etc.

#3. API Key
A unique string issued to a client/app, sent via header, query param, or body (e.g., `x-api-key: abc123`).
- Identifies the *application*, not necessarily a specific user.
- Easier to rate-limit and revoke per key.

#4 JWT (JSON Web Token) Bearer
				A specific, structured type of bearer token with three parts: `Header.Payload.Signature`.
				- **Header:** algorithm used (e.g., HS256, RS256).
				- **Payload:** claims — user id, roles, expiry (`exp`), issued-at (`iat`), etc.
				- **Signature:** verifies the token wasn't tampered with, using a secret (HS256) or public/private key pair (RS256).
				- Self-contained — server can verify it without a database lookup, until it expires.

				### Digest Auth
				Like Basic Auth but hashes credentials (with a server-provided nonce) instead of sending them in plain Base64 — 
				more secure than Basic Auth over non-HTTPS, though largely superseded by token-based methods.

				### OAuth 1.0
				Uses cryptographic signing of each request with consumer key/secret and token key/secret. 
				Complex but doesn't require HTTPS to be secure (signature-based).

				### OAuth 2.0
				Industry-standard authorization framework (not authentication itself). Issues **access tokens** (often JWTs) after flows like:
				- Authorization Code (web apps)
				- Client Credentials (server-to-server)
				- Password Grant (legacy, discouraged)
				- Implicit (legacy, discouraged)


---

## 2. HTTP Status Codes: Important Codes to Know

You mentioned classes 1xx/2xx/3xx/4xx/5xx — here's what's *inside* each class that actually matters day-to-day in API testing:

### 1xx – Informational
| Code | Meaning |
|---|---|
| 100 | Continue — client should continue the request |
| 101 | Switching Protocols (e.g., HTTP to WebSocket) |

### 2xx – Success
| Code | Meaning |
|---|---|
| 200 | OK — standard success |
| 201 | Created — resource successfully created (POST) |
| 202 | Accepted — request accepted for async processing |
| 204 | No Content — success, but nothing to return (common on DELETE) |

### 3xx – Redirection
| Code | Meaning |
|---|---|
| 301 | Moved Permanently |
| 302 | Found (temporary redirect) |
| 304 | Not Modified — use cached version |
| 307 | Temporary Redirect (method preserved) |

### 4xx – Client Errors
| Code | Meaning |
|---|---|
| 400 | Bad Request — 	malformed syntax/invalid data |
| 401 | Unauthorized — 	missing/invalid authentication |
| 403 | Forbidden — 	authenticated but not allowed |
| 404 | Not Found — 	resource doesn't exist |
| 405 | Method Not Allowed |Get Method provided instead of Post
| 406 | Not Acceptable — content negotiation failure |
| 408 | Request Timeout |
| 409 | Conflict — e.g., duplicate resource, version conflict |
| 410 | Gone — resource permanently removed |
| 415 | Unsupported Media Type |
| 429 | Too Many Requests — rate limit exceeded |

### 5xx – Server Errors
| Code | Meaning |
|---|---|
| 500 | Internal Server Error — generic failure |
| 501 | Not Implemented |
| 502 | Bad Gateway — invalid response from upstream server |
| 503 | Service Unavailable — server overloaded/down for maintenance |
| 504 | Gateway Timeout — upstream server too slow |

**Note on your DELETE /posts/1 example:** 
A successful DELETE typically returns **200** (with body) or 
**204 No Content**. If auth (JWT) is invalid/missing → **401**. If the JWT is valid but the user lacks permission to delete → **403**.

---
##403 Forbidden :Authenticated but not authorized
this is achived by the differnt access level based user Roles asigned: 
JSON Web Token (JWT)
JSON Web Token (JWT) is a compact and secure method for transmitting information between parties as a JSON object. 
It is commonly used for authentication and authorization in web applications, allowing users to access protected resources 
without repeatedly providing credentials. Key features include:

Stateless Authentication: 	Stores user information in a token, reducing the need for server-side session storage.
Secure Data Exchange: 		Uses digital signatures to verify that the token has not been altered.
Compact Format: 			Consists of three parts—Header, Payload, and Signature—encoded into a single string.
Widely Supported: 			Works across different programming languages and platforms.



## 3. API Testing Interview Questions & Answers

**Q1: What is API testing?**
A: Testing performed directly at the API layer (bypassing UI) to validate functionality, reliability, performance, 
	and security of business logic and data exchange.

**Q2: What's the difference between API testing and UI testing?**
A: 
	API testing is faster, more stable (no UI flakiness), tests business logic directly, and is done earlier in the dev cycle. 
	UI testing validates the user-facing experience.

**Q3: What are common HTTP methods and their purpose?**
A: GET (read), POST (create), PUT (full update/replace), PATCH (partial update), DELETE (remove).

**Q4: What's the difference between PUT and PATCH?**
A: 	PUT replaces the entire resource; 
	PATCH updates only specified fields.

**Q5: What is idempotency, and which methods are idempotent?**
A: An idempotent operation produces the same result no matter how many times it's called. GET, PUT, DELETE are idempotent; POST is not.

##Idempotent Methods -  GET, PUT, DELETE are idempotent; 

##Non-Idempotent Methods - POST is not

**Q6: How do you validate an API response?**
A: 
	Check status code, 
	Response headers, 
	Response body/schema, 
	Data types, 
	Response time, and 
	Business-rule correctness like in chain API 
	
1.API testing focus is on validating server responses.
2.Making multiple requests to API endpoints for performance testing.
3.Rate: The number of requests your API receives per second.
4.Errors: The number of failed requests. You should categorize these by HTTP status codes:200, 400 and 500 
5.Schema Validation: Monitor whether the API responses strictly match your structural OpenAPI/Swagger documentation during automated test runs.
6.Duration (Latency): How long it takes to process a request. Slow response times damage user experience and tie up server resources
	
	

**Q7: What is schema validation and why does it matter?**
A: Verifying the response JSON/XML matches an expected structure (types, required fields) — catches breaking contract changes early.

**Q8: What's the difference between authentication and authorization? ENTITLEMENT TESTING** 
A: Authentication verifies *who you are* (login/token); authorization verifies *what you're allowed to do* (permissions/roles).

**Q8: Authorization Roles based access ? ENTITLEMENT TESTING JWT Bearer Token based testing - will get response code - 403 | Forbidden — authenticated but not allowed** 
Testing with role based access to the to resouce.

**Q9: How do you test for negative/error scenarios?**
A: 
	1. Send invalid/missing auth tokens, 
	2. Malformed payloads, 
	3. Wrong data types, 
	4. Boundary values, 
	5. Unsupported methods, 
	6. Unsupported file type and 
	6. verify the API returns correct error codes/messages instead of crashing or leaking data.

**Q10: What is rate limiting, and how do you test it?**
A: Restricting number of requests per time window. 
	Test by sending requests beyond the limit and confirming a 429 is returned with appropriate retry-after headers.

**Q11: What is a mock server, and why use one?**
A: A simulated API endpoint that returns predefined responses — used to test client code before the real API is ready, or 
	to isolate tests from external dependencies.

**Q12: What's the difference between a JWT and a session-based(Bearer token) token?**
A: 	JWTs are self-contained/stateless (server verifies signature, no DB lookup needed); 
	session tokens are typically opaque references requiring a server-side lookup/store.

**Q13: How do you test API security?**
A: 
	1. Test for broken auth, 
	2. SQL/NoSQL injection, 
	3. Excessive data exposure, 
	4. Missing rate limiting, 
	5. Improper error handling (leaking stack traces), and 
	6. Verify HTTPS/TLS is enforced.

**Q14: What is contract testing?**
A: Verifying that the API's request/response contract matches what consumers expect — often done with tools like Pact, 
	to catch breaking changes between services.
	

 **schema validation**  is a structural check on a single system, whereas 
 **contract testing** is a collaborative validation of interactions between two separate systems (a consumer and a provider)
 

**Q15: What tools have you used for API testing?**
A: Postman, REST Assured, SoapUI, curl - Newman (CLI for Postman collections) - CLI tool need to check 
#API Testing Tools
1. Postman: A popular tool used to send API requests, test endpoints, and automate API test cases.
2. Swagger / OpenAPI: Used for designing, documenting, and testing REST APIs through interactive API documentation.
3. REST Assured: A Java-based library used for automating REST API testing and validating responses.
4. SoapUI: A testing tool for SOAP and REST APIs that supports functional, security, and load testing.


**Q16: How do you handle dynamic/dependent data in API tests (e.g., a token or ID from a previous response)?**
A: Chain requests — extract values from one response (e.g., via Postman scripts/tests tab or environment variables) and 
	inject them into subsequent requests.

**Q17: What is the difference between 401 and 403?**
A: 401 = not authenticated (no/invalid credentials); 403 = authenticated but not authorized to access that resource.

**Q18: What's the difference between REST and SOAP?**
A: 
	REST is architectural style over HTTP using JSON/XML, stateless, resource-based (URLs); 

	SOAP is a stricter XML-based protocol with formal contracts (WSDL), often used in enterprise/legacy systems.

**Q19: How do you test API performance?**
A: Measure response time under normal and peak load, test concurrency/throughput, and identify bottlenecks using tools like JMeter, k6, or 
	Postman's performance testing.

**Q20: What should you check in response headers during testing?**
A: 
	`Content-Type`, 
	`Cache-Control`, 
	CORS headers, rate-limit headers (`X-RateLimit-*`), 
	security headers (`Strict-Transport-Security`, `Content-Security-Policy`).

**Q21: What  API Chaining
	API Chaining means linking multiple Application Programming Interface (API) calls together in a sequential sequence, where the output data from one API 
	response is automatically passed as the input for the next API request.
	How It Works 
	(The 3-Step Flow)The 
	First Call: Your system makes an initial request to API A.
	The Extraction: A software tool or workflow script extracts a specific value (like a tracking ID, token, or customer number) from API A's response body.
	The Next Call: This extracted value is automatically injected into the URL, headers, or body parameters of API B


## 13. How to automate a POST request using REST Assured

```java
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;

public class PostRequestExample {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://reqres.in/api";

        String requestBody = "{ \"name\": \"John\", \"job\": \"QA Engineer\" }";

        Response response = 
			given()
                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .body(requestBody)
            .when()
                .post("/users")
            .then()
                .statusCode(201)
                .extract().response();

        System.out.println("Response Body: " + response.asString());
        System.out.println("Status Code: " + response.getStatusCode());
    }
}
```

**Using a POJO instead of raw JSON string (better practice):**
```java
User user = new User("John", "QA Engineer");

given()
    .contentType("application/json")
    .body(user)
.when()
    .post("/users")
.then()
    .statusCode(201)
    .body("name", equalTo("John"));
```

**Key REST Assured syntax to remember**: 
`given()` (request setup) → `when()` (action: get/post/put/delete) → `then()` (validation/assertions).

---

## 14. Authentication vs Authorization

| Aspect | Authentication | Authorization |
|---|---|---|
| **Meaning** | Verifies *who you are* (identity) | Verifies *what you're allowed to do* (permissions) |
| **Happens** | First (before authorization) | After authentication |
| **Example** | Logging in with username/password, API key, token | Accessing admin panel only if role = "Admin" |
| **HTTP Status if failed** | 401 Unauthorized | 403 Forbidden |
| **In REST Assured** | `given().auth().basic("user","pass")` or bearer token in header | 
						Checked at the server based on the authenticated user's role |

**Common auth types tested via REST Assured**: Basic Auth, Bearer Token/OAuth2, API Key in header.
```java
given().auth().oauth2("your_token_here")
       .when().get("/secure-endpoint")
       .then().statusCode(200);
```

---

## 15. PUT vs PATCH

| Aspect 		| PUT 								| PATCH |
|---			|---|---|
| **Purpose** | Replaces the **entire resource** 	| Updates **only specified fields** (partial update) |
| **Idempotent?** | Yes 							| Technically yes, but often treated as non-idempotent in practice |
| **Payload** | Must send the complete resource object | Send only the fields you want to change |
| **Example** | `PUT /users/1` with full user JSON — missing fields may be nulled/removed | `PATCH /users/1` with `{ "email": "new@mail.com" }` — only updates email, rest untouched |

**Interview line**: "PUT is like overwriting the whole file, PATCH is like editing just one line in that file."

---
#Understanding Cookies: 
			Cookies are small pieces of data sent by a server to maintain session information, 
			allowing the server to recognize subsequent requests from the same user 
			
			
## Differences between REST and SOAP

#REST- 
	uses standard HTTP methods + URLs to identify resources, and typically exchanges data as JSON (lightweight, flexible format). 
#SOAP-
	uses only HTTP as a transport wrapper, but every request must be a strictly-structured XML envelope following a fixed contract (WSDL) — 
	regardless of what HTTP method you use.
	
1. SOAP uses only HTTP as a transport wrapper:
	HTTP is just the delivery truck, not something SOAP actually cares about or uses meaningfully

2. Every request must be a strictly-structured XML envelope:
	The entire request body, no matter what action you're performing, must be wrapped inside a fixed XML structure called an Envelope.
	<soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope">
	  <soap:Header>
		 <!-- optional: auth tokens, session info -->
	  </soap:Header>
	  <soap:Body>
		 <!-- the actual action/data goes here -->
		 <GetOrderDetails>
			<OrderID>123</OrderID>
		 </GetOrderDetails>
	  </soap:Body>
	</soap:Envelope>


## Core differences
| Aspect 					| REST 										| SOAP |
|---|---|---|
| **1.Type** 	| Architectural style 										| Strict protocol |
| **2.Data format** | JSON (mostly), also XML, plain text 					| XML only |
| **3.Transport** | HTTP/HTTPS only 										| HTTP, SMTP, TCP, JMS, etc. (transport-agnostic) |
| **4.Contract** | No formal contract; OpenAPI/Swagger optional 			| Formal contract required — WSDL (Web Services Description Language) |
| **5.Statefulness** | Stateless by design 								| Can be stateful or stateless |
| **6.Standards/Security** | Relies on HTTPS, OAuth, JWT — flexible 		| Built-in standard: WS-Security (message-level encryption, digital signatures) |
| **7.Error handling** | HTTP status codes (404, 500, etc.) 				| Standardized `<Fault>` element in the SOAP envelope |
| **8.Performance** | Lighter payload (JSON), faster 						| Heavier payload (XML envelope + headers), slower |
| **9.Ease of use** | Simple, human-readable, easy to test (Postman/curl) | Verbose, needs SOAP client/library to consume |
| **10Flexibility** | Loosely coupled — client/server can evolve independently | Tightly coupled to WSDL contract — changes break clients more easily |

## Where each is actually used

**REST is used for:**
- Public web/mobile APIs (Twitter, GitHub, Stripe, most SaaS APIs)
- Microservices communicating internally
- Any scenario prioritizing speed, simplicity, and broad client compatibility (browsers, mobile apps, IoT)

**SOAP is used for:**
- **Banking and financial services** (payment gateways, core banking systems) — needs guaranteed delivery, ACID transactions
- **Healthcare** (HL7, insurance claim processing) — needs strict data integrity, formal contracts, auditability
- **Telecom** — often built on top of non-HTTP transports (JMS, MSMQ)
- **Legacy enterprise systems** — many were built pre-2010 when SOAP was the enterprise standard (e.g., older ERP/CRM integrations — SAP, Salesforce still expose SOAP endpoints for legacy support)
- **Government/regulated industries** — where a formal, versioned, machine-readable contract (WSDL) is a compliance requirement
			

### API Rate Limit -429 | Too Many Requests

API Rate Limiting: Quick Reference
Question								Answer
What happens when limits are exceeded?	The API returns a 429 Too Many Requests status, often with a Retry-After header.
Can limits differ per user?				Yes. Limits can vary by tier, subscription, authentication method, or role.
Which algorithm is best?				Sliding window for accuracy, token bucket for burst flexibility.
How do I test rate limits?				Use an API client or automated runner to send rapid requests.
Should I limit internal APIs?			Yes. Rate limiting prevents cascading failures and resource exhaustion.


###What is API rate limiting?
API rate limiting restricts the number of requests a client can make to an API within a specific time window. 
When a client exceeds the allowed threshold, the API rejects additional requests and typically returns a ***429 Too Many Requests*** response.

Think of it like a highway toll booth. Traffic flows smoothly at a steady rate, but if too many cars try to enter at once, 
the system slows or stops new arrivals to prevent congestion. Rate limiting applies the same principle to API traffic.

***Rate limiting serves several critical functions:

1. Prevents abuse by stopping malicious actors from overwhelming your API.
2. Ensures reasonable access for all users.
3. Manages infrastructure expenses by limiting unnecessary traffic.
4. Keeps response times fast during high-traffic periods.
5. Defends against DDoS, credential stuffing, and brute-force attacks.

##How API rate limiting works
At a high level, rate limiting follows a simple flow:

1. A client sends a request to the API.

2. The rate limiter checks the number of requests that client has made recently.

3. The request count is compared to the configured limit.

4. The request is accepted or rejected.

5. Response headers communicate the current rate limit status.

**Rate limit response headers
APIs often include headers that help clients understand their current usage:

#Response
	HTTP/1.1 200 OK
	X-RateLimit-Limit: 1000
	X-RateLimit-Remaining: 847
	X-RateLimit-Reset: 1677721600
	These headers tell clients:

X-RateLimit-Limit: total requests allowed in the time window
X-RateLimit-Remaining: requests left in the current window
X-RateLimit-Reset: when the limit resets

**Rate limit exceeded response
When a client exceeds the limit, the API typically responds with a 429 status:
#Response
	HTTP/1.1 429 Too Many Requests
	Retry-After: 60
	X-RateLimit-Remaining: 0

	{
	  "error": "Rate limit exceeded",
	  "message": "Try again in 60 seconds"
	}
	
The Retry-After header tells the client how long to wait before sending another request.

#Rate limit for information:
Public APIs (Open to External Developers) Public APIs focus on preventing server abuse, 
protecting against DDoS attacks, and driving monetization tiers.

Standard Limits: 			Typically 60 to 100 requests per minute (1 request/second) for unauthenticated or free tiers.
Paid/Authenticated Tiers: 	Usually scale up to 1,000 to 10,000 requests per hour (or roughly 3 to 150 requests/second).

=========================================================================================================================================================================
**1. What is RestAssured and why is it preferred for Java-based API automation?**
RestAssured is a Java DSL (domain-specific language) library for testing REST APIs. 

	It's preferred because its BDD-style syntax (`given().when().then()`) is highly readable, it integrates natively with TestNG/JUnit and 
	Java's Hamcrest matchers for assertions, and it fits naturally into an existing Selenium+Java framework, 
	letting teams maintain one language/stack for both UI and API layers.


**2. Explain the `given-when-then` structure.**
`given()` sets up the request context — headers, params, body, auth, base URI. 
`when()` specifies the HTTP action (`.get()`, `.post()`, etc.) and the endpoint. 
`then()` validates the response — status code, headers, body content, response time — using Hamcrest matchers or JSON path assertions.


**3. How do you validate a JSON response body?**
Using `.body("path", Matchers.equalTo(value))` with JSON Path syntax, 
e.g., `.body("data.id", equalTo(101))` or `.body("data.items.size()", greaterThan(0))`. For complex structures, deserialize into a POJO via `.as(MyPojo.class)` and assert with standard Java assertions, or validate the entire payload against a **JSON Schema** using `matchesJsonSchemaInClasspath("schema.json")` from the `json-schema-validator` module.


**4. How do you handle authentication in RestAssured?**
Basic Auth: `.auth().preemptive().basic(user, pass)`. 

Bearer/OAuth2 token: `.header("Authorization", "Bearer " + token)` or `.auth().oauth2(token)`. 
For flows requiring a token fetched from a login endpoint first, chain requests: call the auth endpoint, extract the token via 
`.extract().path("token")`, then reuse it in a `RequestSpecification` for subsequent calls.

**5. How do you design a reusable API automation framework?**
Centralize `RequestSpecification` and `ResponseSpecification` builders (base URI, common headers, logging) so tests don't repeat boilerplate. 
Use POJOs with Jackson/Gson for serialization/deserialization of request and response bodies. 
Separate test data (JSON/YAML files or builders) from test logic. 
Add a reusable `ApiClient`/`ServiceClass` layer per microservice/resource (similar to Page Objects in UI automation) so tests call 
`userService.createUser(payload)` rather than raw RestAssured calls. 
Integrate schema validation, retry/backoff for flaky endpoints, and structured logging (`.log().ifValidationFails()`) to keep CI logs clean but informative.

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
	Regular API testing validates actual runtime behavior end-to-end. 
	Contract testing (e.g., Pact) validates that a producer and consumer agree on a request/response schema/contract independently, 
	without both being deployed together — useful in microservice architectures to catch breaking changes early, 
	in CI, before integration environments are even involved.

**15. How do you manage test data and environment configuration across dev/QA/staging?**
Externalize base URIs, credentials, and environment-specific data into config files (`.properties`/YAML) 
selected via a system property or environment variable (`-Denv=qa`), 
never hardcoded. Use a config loader singleton. For test data, 
prefer creating/tearing down data via API calls in setup/teardown (self-contained tests) over relying on pre-seeded environment data, which causes cross-test pollution and flakiness.
