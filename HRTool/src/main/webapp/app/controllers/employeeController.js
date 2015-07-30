/**
 * Employee controller created by: Gavrilo Drljaca
 */

app.controller('employeeController', function($rootScope, $scope, $window,
		$mdDialog, selectedEmployee, $filter) {
	function init() {

		$scope.activeForm = "none";
	}

	$scope.activeForm = "none";
	$scope.currEmp = selectedEmployee;
	
	//Date of Birth
	$scope.dateBirth = new Date(parseInt(selectedEmployee.dateOfBirth));
	$scope.dateOfBirth = $filter('date')($scope.dateBirth , 'yyyy-MM-dd');
	
	$scope.saveEmployee(){
		
	}
	//CLOSING DIALOG
	$scope.hide = function() {
		$mdDialog.hide();
	};
	$scope.cancel = function() {
		$mdDialog.cancel();
	};
	$scope.answer = function(answer) {
		$mdDialog.hide(answer);
	}

	$scope.showForm = function(forma) {
		$scope.activeForm = forma;
	}
	$scope.showDate = function(bla){
		alert($scope.dateOfBirth);
		alert(new Date($scope.DateOfBirth))
	}
	/*
	 * function toJSONLocal (date) { var local = new Date(date);
	 * local.setMinutes(date.getMinutes() - date.getTimezoneOffset()); return
	 * local.toJSON().slice(0, 10); }
	 */

});