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

				### JWT (JSON Web Token) Bearer-- NOT USED
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
*** When will 100 or 300 response code***
### 1xx – Informational

### 3xx – Redirection


An API returns a 100 (Continue) status code when the server receives the first part of a request and tells the client to send the rest. 
It returns a 300 (Multiple Choices) status code when a requested resource has more than one option and the client needs to pick one


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
	
1.API testing focus is on validating server responses. API testing includes:
2.Making multiple requests to API endpoints for performance testing.
3.Rate: The number of requests your API receives per second.
4.Errors: The number of failed requests. You should categorize these by HTTP status codes:200, 400 and 500 
5.Schema Validation: Monitor whether the API responses strictly match your structural OpenAPI/Swagger documentation during automated test runs.
6.Duration (Latency): How long it takes to process a request. Slow response times damage user experience and tie up server resources
	
	

**Q7: What is schema validation and why does it matter?**
A: Verifying the response JSON/XML matches an expected structure (types, required fields) — catches breaking contract changes early.

**Q8: What's the difference between authentication and authorization? ENTITLEMENT TESTING** 
A: Authentication verifies *who you are* (login/token); authorization verifies *what you're allowed to do* (permissions/roles).

**Q8: Authorization Roles based access ? ENTITLEMENT TESTING - will get response code - 403 | Forbidden — authenticated but not allowed** 
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

**Q12: What's the difference between a JWT and a session-based token?**
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
	API Chaining means linking multiple Application Programming Interface (API) calls together in a sequential sequence, where the output data from one API response is automatically passed as the input for the next API request.
	How It Works 
	(The 3-Step Flow)The 
	First Call: Your system makes an initial request to API A.
	The Extraction: A software tool or workflow script extracts a specific value (like a tracking ID, token, or customer number) from API A's response body.
	The Next Call: This extracted value is automatically injected into the URL, headers, or body parameters of API B
