/**
 * Employee controller
 * created by: Gavrilo Drljaca
 */


app.controller('employeeController', function ($rootScope, $scope, $window){
	
	
	 $scope.hide = function() {
		    $mdDialog.hide();
		  };
		  $scope.cancel = function() {
		    $mdDialog.cancel();
		  };
		  $scope.answer = function(answer) {
		    $mdDialog.hide(answer);
		  }
	
	
});