##What are challenges faced during selenium automation?

1. Report: No reporting API/capabilities available need to use 3rd party tools like TestNG and Extent reporting
2. Dealing with pop-up never know what kind of pop-up will be (
	java script, 
	window pop-up, 
	model pop-up or 
	Browser pop-up)
3. *** Time out resulting from synchronization problem: 
	When to use implicit wait, explicit wait sometime explicit wait also does not works need to use JavaScriptExecutor also need to check element 
	is present, is enabled and is selected we will get StaleElementException, ElementNotFoudException, ElementNotVisibleException where
We must know how to use implicit, explicit, fluent wait then JavaScriptExecutor is the solution where we should never use Thread.sleep().
4. Handling Ajax Components: Asynchronous call 
	Example: on home page 2 dropdown
	Country: USA
	State: California
	On changing country INDIA then a valid wait need to reset the state dropdown and Indian state sometime the USA state only displayed.


##What are disadvantages of selenium/ what are all we cannot automate?

1. Images or text overlapping issue cannot verified.
2. No facility to capture capatch and Bar code.
3. Does not support non web application.
4
5. Report: No direct reporting API/capabilities available need to use 3rd party tools like TestNG and Extent reporting
6. API testing - No: Selenium does not natively support API testing. It is strictly a browser automation tool built to replicate user actions on a web UI
7. Android Mobile: does not support testing native or hybrid Android mobile apps. 
	To automate native Android apps, you must use a Selenium-derived framework like Appium or Selendroid, which use the same WebDriver protocol


































challenges faced during selenium automation

What are challenges faced during selenium automation/ disadvantages of selenium/ what are all we cannot automate?

1. Images or text overlapping issue cannot verify.
2. No facility to capture capatch and Bar code.
3. Does not support non web and mobile application.
4. Open source - so there is no direct support.
5. Bitmap comparison (BMP) does not support.
6. Report: No reporting API/capabilities available need to use 3rd party tools like TestNG and Extent reporting
7. Need to learn native programming language Java, python, .net, PHP etc
8. Difficult to identify dynamic object (dynamic Xpath).
9. Working with frame (ex: in ecommerce more than 10 frames jumping one frame to other or coming back it's difficult).
//10. JavaScript sandbox, flash and HTML-5 are all preset problems.
11. Dealing with pop-up never know what kind of pop-up will be (java script, wind pop-up, model pop-up or browser pop-up)
12. *** Time out resulting from synchronization problem: 
When to use implicit wait, explicit wait sometime explicit wait also does not works need to use JavaScriptExecutor also need to check element is present, is enabled and is selected we will get StaleElementException, ElementNotFoudException, ElementNotVisibleException where
We must know how to use implicit, explicit, fluent wait then JavaScriptExecutor is the solution where we should never use Thread.sleep().
13. Cross browser testing issue, 500 TC execting on chrome then need to exected all on other browser.
14. Handling Ajax Components: Asynchronous call 
Example: on home page 2 dropdown
Country: USA
State: California
On changing country INDIA then a valid wait need to reset the state dropdown and Indian state sometime the USA state only displayed.

What are disadvantages of selenium/ what are all we cannot automate?

1. Images or text overlapping issue cannot verified.
2. No facility to capture capatch and Bar code.
3. Does not support non web application.
4. Selenium will support mobile application.
5. Report: No direct reporting API/capabilities available need to use 3rd party tools like TestNG and Extent reporting






