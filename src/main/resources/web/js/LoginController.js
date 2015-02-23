function LoginController ($scope, $http, $location, authTokenStorageService) {

    authTokenStorageService.resetAuthToken();

    $scope.login = function () {
        var password = $("#passwordInput").val();
        $http.post("/api/login/username", { username: $scope.username, password: password })
            .success(function (data) {
                authTokenStorageService.setAuthToken(data.newAuthToken);
                $location.path("/app/protected/data");
            })
            .error(function (error) {
                $scope.error = error.errorMessage;
            });
    };

}
