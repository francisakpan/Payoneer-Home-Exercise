Payoneer Home Exercise
=======================

A simple application that fetch and displays a list of payment methods.

Problem Statement
=======================

When an end-customer pays through Payoneer, our frontend presents a list of payment methods like Visa, 
SEPA and PayPal. Our frontend software loads this list of payment methods using a token-based list URL.

Write a small app that loads this JSON list and presents the payment methods in a scrolling list in your app.

Focus Points
==============
* Network errors should be handled properly e.g 404, 500 response codes and IOExceptions.
* Cool looking UI and UX.
* Unit tests.

Dependencies
============

* Androidx Livecycle 2.3.1
* RxJava >= 3.0.0
* Retrofit 2.9.0
* Glide 4.12.0
* Mockito 3.3.0
* Truth 1.1.3

Solution
==========
For this Solution, I have employed a MVVM (Model-View ViewModel) architecture to structure the application.  
The UI as view communicate with its ViewModel to request for data. The View model subscribe to an 
observable type using RxJava3 library and perform an asynchronous network call on the background thread to retrieve the payment methods.  
Depending on the state and response from the network call, it emits a Single observable type that is 
converted to a LiveData of either **LOADING, SUCCESS OR ERROR**. The changes in the LiveData value is 
observed by the UI using a LiveData observer that has been registered by the view.

Image
======
<img src="https://res.cloudinary.com/dclwbiwmf/image/upload/v1642781880/Screenshot_2022-01-21-17-15-13-135_com.francis.payoneerexercise_zal9ht.jpg" width="300">

Author
=======

* Francis Akpan
