function DataController ($scope, $http) {

    $scope.state = "loading";

    $http.post("/api/data/get", { dataKey: "data" })
        .success(function (data) {
            $scope.data = data.data;
            $scope.state = "view";
        })
        .error(function (error) {
            $scope.error = error.errorMessage;
            $scope.data = { product: "", amount: "" };
            $scope.state = "view";
        });

    $scope.save = function () {
        $scope.state = "saving";

        $http.post("/api/data/save", { dataKey: "data", product: $scope.data.product, amount: $scope.data.amount })
            .success(function () {
                $scope.state = "view";
            });
    };

}
