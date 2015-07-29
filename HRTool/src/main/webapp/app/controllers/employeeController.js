/**
 * Employee controller
 * created by: Gavrilo Drljaca
 */


app.controller('employeeController', function ($rootScope, $scope, $window, employeeFactory){
	
	
	$scope.getEmployee() = function(id){
		$scope.currentEmp = employeeFactory.getEmployee(id)
		
		
	}
	
	
});