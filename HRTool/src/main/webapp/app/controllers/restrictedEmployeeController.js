app.controller('restrictedEmployeeController', function($http, $scope, $window, $mdDialog, $mdToast, selectedEmployee, role, employeeService) {
	
	$scope.init = function(){
		$scope.currEmp = selectedEmployee;
		$scope.dateBirth = new Date(selectedEmployee.dateOfBirth);
		$scope.role = role;
	}
	
	$scope.exit = function(){
		$mdDialog.cancel();
	}
	
	$scope.update = function(){
		$scope.currEmp.dateOfBirth = $scope.dateBirth;
		$http.post('/restrictedEmployees/update', $scope.currEmp);
	}
});