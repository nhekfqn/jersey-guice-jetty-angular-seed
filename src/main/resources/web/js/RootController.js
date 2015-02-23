function RootController ($rootScope, $scope) {

    $scope.closeServerErrorAlert = function () {
        $rootScope.serverError = null;
    };

}
