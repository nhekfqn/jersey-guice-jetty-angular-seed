# jersey-guice-jetty-angular-seed
Seed project for web application with rest back-end built on Jersey integrated with Guice running on embedded Jetty server and AngularJS front-end.


## Features

* Jersey resources working in Guice context, running on embedded Jetty.
* Client-server login and data protocols.
* In-memory AuthTokensService implementation, dummy LoginService implementation.
* Angular web application with login page, sample protected page and authentication and error handling and sample data page.
* Sample Data resource.


## Login protocol

Unauthorized client uses one of the login methods of LoginResource (except tokenLogin - it is discussed later)
to get auth token.
Auth token shold be saved on client side to avoid log-in on every session.
Every log-in replaces token so only the last one is valid and therefor loging in from one device will
force user to log in again to use another device. If you need multi-device login then store, expire and
replace tokens by userId + deviceId (need to be implemented).
Auth token should expire after some time (not implemented atm), to avoid log-in after that client should
use tokenLogin once at start of every session - it will both check client's token is valid and issue new one.


## Protocol

Every client request results in either ErrorResponse, NotAuthorizedResponse (client checks login before requesting
server so it occurs only in exceptional cases) or request-specific response (or SuccessResponse, if there is no specific
response for the request).
