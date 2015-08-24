app.controller('restrictedEmployeeController', function($http, $scope, $window, $mdDialog, $mdToast, selectedEmployee, role, employeeService) {
	
	$scope.init = function(){
		$scope.currEmp = selectedEmployee;
		$scope.currEmp.dateOfBirth = new Date($scope.currEmp.dateOfBirth);
		$scope.role = role;
	}
	
	$scope.exit = function(){
		$mdDialog.cancel();
	}
	
	$scope.update = function(){
		if ($scope.currEmp.nameEmployee == ""){
			$scope.currEmp.nameEmployee = "No name";
		}
		$http.post('/restrictedEmployees/update', $scope.currEmp).success(function(data){
			$scope.showSimpleToast();
		});
		
	}
	
	$scope.toastPosition = {
		    bottom: false,
		    top: true,
		    left: false,
		    right: true
		};
		
	$scope.getToastPosition = function() {
	    return Object.keys($scope.toastPosition)
	      .filter(function(pos) { return $scope.toastPosition[pos]; })
	      .join(' ');
	};
	
	$scope.showSimpleToast = function() {
	    $mdToast.show(
	      $mdToast.simple()
	        .content("Employee " + $scope.currEmp.nameEmployee + " updated!")
	        .position($scope.getToastPosition())
	        .hideDelay(3000)
	    );
	};
});