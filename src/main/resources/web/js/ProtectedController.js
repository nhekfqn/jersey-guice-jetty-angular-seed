function ProtectedController ($rootScope, $scope, $location) {

    $scope.logout = function () {
        $location.path("/login");
    };

}
