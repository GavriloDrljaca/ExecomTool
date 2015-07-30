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

	dat = new Date(selectedEmployee.dateOfBirth);

	$scope.dateOfBirth = $filter('date')(dat, 'yyyy-MM-dd');
	$scope.dateBirth = new Date($scope.dateOfBirth);
	alert($scope.dateBirth);
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
		alert($scope.dateBirth);
	}
	/*
	 * function toJSONLocal (date) { var local = new Date(date);
	 * local.setMinutes(date.getMinutes() - date.getTimezoneOffset()); return
	 * local.toJSON().slice(0, 10); }
	 */

});