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
