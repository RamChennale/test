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
HTTP methods such as GET, POST, PUT, and DELETE to enable communication between client & server and exchange data using HTTP.
	These are the most popular and flexible APIs found on the web today. 
	The client sends requests to the server as data. 
	The server uses this client input to start internal functions and returns output data back to the client. 
	

***2. SOAP APIs -- Simple Object Access Protocol 
	These APIs use SOAP is a protocol-based API architecture (Simple Object Access Protocol). 
	Client and server exchange messages using XML. 
	This is a less flexible API compare to REST. as it .
	It follows strict standards and is commonly used in enterprise systems

***3. RPC APIs -- Remote Procedure Calls - TCP or UDP, and application protocols like HTTP/1.1, HTTP/2
	These APIs are called Remote Procedure Calls. 
	The client completes a function (or procedure) on the server, and the server sends the output back to the client.

***4. Websocket APIs - TCP
	Websocket API is another modern web API development that uses JSON objects to pass data. 
	A WebSocket API supports two-way communication between client apps and the server. 
	The server can send callback messages to connected clients, making it more efficient than REST API.
	(callback messages: a callback is an active message sent by the server to give new data or notify a client about an event)

#Why WebSockets Are Stateful ??
1. Persistent Connection: 	The connection stays open continuously until one side explicitly closes it.
2. Context Retention:		The server remembers exactly who the client is after the initial handshake.
3. No Re-authentication: 	The client does not need to send authentication tokens (like JWTs) or headers with every single message.
4. Bi-directional Flow: 	Either side can send data at any time without waiting for a request.



###Diff in SOAP and WebSocket : not much imp but good to know
No, WebSocket and SOAP do not work the same way. 
WebSockets WebSockets are inherently - stateful, 
SOAP is traditionally a 			 - stateless protocol that relies on standard HTTP request-response cycles.

#Persistent Connection
WebSocket: 	Yes It opens one dedicated, long-lived TCP connection that stays open for continuous data flow.
SOAP: 		No. It typically operates over standard HTTP/1.1 or HTTP/2. It opens a connection, sends a request, receives a response, 
			and then closes or returns the connection to a pool.
#Context Retention WebSocket: 
WebSocket: 	Yes  The server keeps an active memory of the specific client session directly tied to that open connection socket.
SOAP: 		No. By default, SOAP is stateless. The server treats every single incoming XML request as completely brand new and isolated.
#No Re-authentication
WebSocket: 	Yes. You authenticate exactly once during the initial HTTP handshake. After that, you send raw data frames without any auth headers.
SOAP: 		No. Because it is stateless, every single SOAP request must include authentication credentials 
			(like a WS-Security token in the XML header)  to prove who you are.
#Bi-directional Flow
WebSocket: 	Yes. It is full-duplex. The server can push data to the client at any millisecond without the client asking for it.
SOAP: 		No. It is strictly half-duplex (request-and-response). The server cannot talk to the client unless the client asks a question first


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
1.User authentication, 
2.statistics, and 
3.Rate management that are applicable across all API calls.

##Importance of APIs
	APIs are critical for building scalable, flexible, and connected software systems. 
	They allow different applications to communicate, share data, and use existing services efficiently.

Reusability:	APIs allow developers to use existing functionalities instead of building everything from scratch.
Efficiency: 	APIs reduce development time by providing ready-made features and services that can be easily integrated.
Scalability:	APIs support modular and distributed systems, making it easier to expand applications as business requirements grow.
Integration: 	APIs enable seamless communication between different platforms such as web applications, mobile apps, IoT devices, and analytics systems.
Automation: 	APIs allow systems to exchange data and perform tasks automatically without manual intervention.

