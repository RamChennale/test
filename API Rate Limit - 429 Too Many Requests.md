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
