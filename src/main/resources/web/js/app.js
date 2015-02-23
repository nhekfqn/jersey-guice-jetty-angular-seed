'use strict';

angular.module('app', ['ui.router', 'LocalStorageModule'])
    .config(function($stateProvider, $urlRouterProvider, $locationProvider, localStorageServiceProvider, $httpProvider) {
        localStorageServiceProvider.setPrefix("app");

        var tokenLogin = function ($http, $q, authTokenStorageService, redirectService) {
            if (authTokenStorageService.authTokenNotSet()) {
                return redirectService.redirectToLogin();
            }

            if (authTokenStorageService.isLoggedId()) {
                return true;
            }

            return $http.post("/api/login/token", { oldAuthToken: authTokenStorageService.getAuthToken() })
                .success(function (data) {
                    authTokenStorageService.setAuthToken(data.newAuthToken);

                    return true;
                })
                .error (function (error) {
                    return redirectService.redirectToLogin();
                });
        };

        $stateProvider
            .state('root', {
                url: "/app",
                templateUrl: "views/root.html",
                controller: RootController
            })
            .state('root.login', {
                url: "/login",
                templateUrl: "views/login.html",
                controller: LoginController
            })
            .state('root.protected', {
                url: "/protected",
                templateUrl: "views/protected.html",
                controller: ProtectedController,
                resolve: {
                    authorization: tokenLogin
                }
            })
            .state('root.protected.data', {
                url: "/data",
                templateUrl: "views/data.html",
                controller: DataController
            });

        $urlRouterProvider.otherwise("/app/login");

        $httpProvider.interceptors.push(function (authTokenStorageService, $location, $q, $rootScope, redirectService) {
            function isApi (url) {
                return contains(url, "/api");
            }

            function isProtected (url) {
                return isApi(url) && containsNo(url, "/login");
            }

            function contains (string, substring) {
                return string.indexOf(substring) != -1;
            }

            function containsNo (string, substring) {
                return !contains(string, substring);
            }

            return {
                request: function (config) {
                    var url = config.url;
                    if (isApi(url)) {
                        console.log("Api request.");
                        console.log(config);

                        if (isProtected(url)) {
                            if (!authTokenStorageService.isLoggedId()) {
                                // should never happen

                                alert("Trying to request protected resource when not logged in: " + url);

                                return redirectService.redirectToLogin();
                            }

                            config.data.authToken = authTokenStorageService.getAuthToken();
                        }
                    }

                    return config;
                },

                response: function (response) {
                    var url = response.config.url;
                    if (isApi(url)) {
                        console.log("Api response.");
                        console.log(response);

                        if (isProtected(url) && response.data.notAuthorized) {
                            // for now happens when server is rebooted because it does not persist tokens, should never happen in normal application work

                            alert("Not authorized: " + url);

                            return redirectService.redirectToLogin();
                        }

                        if (response.data.errorMessage) {
                            return $q.reject(response);
                        }
                    }

                    return response;
                },

                responseError: function (rejection) {
                    console.log("Rejection!");
                    console.log(rejection);

                    $rootScope.serverError =
                        "Server responded with error!\n" +
                            "Status code: " + rejection.status + "\n" +
                            "Message: " + rejection.data;

                    return $q.reject();
                }
            }
        });
    })
    .service('authTokenStorageService', function (localStorageService) {
        var authTokenStorageKey = "app-auth-token";

        var self = this;

        var loggedIn = false;

        self.authTokenNotSet = function () {
            return !self.getAuthToken();
        };

        self.getAuthToken = function () {
            return localStorageService.get(authTokenStorageKey);
        };

        self.resetAuthToken = function () {
            localStorageService.remove(authTokenStorageKey);
        };

        self.setAuthToken = function (authToken) {
            localStorageService.add(authTokenStorageKey, authToken);

            loggedIn = true;
        };

        self.isLoggedId = function () {
            return !self.authTokenNotSet() && loggedIn;
        };
    })
    .service('redirectService', function ($timeout, $location, $q) {
        var self = this;

        self.redirectToLogin = function () {
            $timeout(function () {
                $location.path("/app/login");
            }, 0);

            return $q.reject();
        }
    });
