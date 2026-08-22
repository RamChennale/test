***Difference between POM and PageFactory***

1. Page Object Model (POM) : The object of Page is an OOPs class that acts as an interface for the web page of your application under test and
 it is actually a type of design pattern that is typically used in Selenium for Automation of Test Cases. 
 Page class in this POM incorporates web factors and techniques to engage with web elements. 
 
 
2. PageFactory : Selenium WebDriver provides a class called PageFactory which helps to assist Page Object of Design patterns. 
In this, we use an annotation '@FindBy'. There is a method called an 'initElements' method, 
which is used for initializing the elements of the web.

##Difference between POM and PageFactory:
S.No.	POM												PageFactory
1.	It is an approach for Design patterns.				It is a class that is provided by Selenium Webdriver.
2.	It is not an optimal method to process tasks.		It is an optimal method to process tasks.
3.	It helps in separating page objects and scripts.	It is a technique to implement POM.
4.	'By' annotation is used to define page objects.		It uses annotation 'FindBy' to describe page objects.
5.	It does not handle exceptions efficiently.			It efficiently handles the exceptions.
6.	It requires the initialization of every object.		It does not require the initialization of every object.
7.	There is cache storage for performing tasks.		There is no need for cache storage.