/**
 * Employee controller
 * created by: Gavrilo Drljaca
 */


app.controller('employeeController', function ($rootScope, $scope, $window, selectedEmployee){
		$scope.activeForm = "nijedna";
		$scope.selectedEmployee = selectedEmployee 
	 $scope.hide = function() {
		    $mdDialog.hide();
		  };
		  $scope.cancel = function() {
		    $mdDialog.cancel();
		  };
		  $scope.answer = function(answer) {
		    $mdDialog.hide(answer);
		  }
	
     $scope.showForm = function(forma){
    	 $scope.activeForm = forma;
     }
		  
	});