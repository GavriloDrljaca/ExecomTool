function EmployeeController($rootScope, $http) {
    $http.get('/employees').
        success(function(data) {
            $rootScope.employees = data;
        });  
}