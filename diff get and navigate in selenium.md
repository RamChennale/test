##difference between get and navigate in selenium
Primary difference is that 
- driver.get() is a direct method used strictly to fetch a webpage and wait for it to load,while 
- driver.navigate() returns a dedicated Navigation interface providing advanced history manipulation like moving back, forward, and refreshing

Feature			 | driver.get("url")									|driver.navigate().to("url")
Primary Interface| Belongs directly to the WebDriver interface.			|Returns a specialized Navigation interface.
History Tracking |Does not provide native history movement tracking. 	| Explicitly tracks browser history for session movement.
Page Load Waiting|Automatically waits until the page is fully loaded.	| May return immediately without waiting for full compilation.
Parameter Types  |Only accepts a String literal URL.					|Overloaded to accept both String and a URL object.
Extra CommandsNone:. Only used to load the target webpage.				|Supports .back(), .forward(), and .refresh()