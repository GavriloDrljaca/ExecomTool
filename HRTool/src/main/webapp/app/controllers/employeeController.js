/**
 * Employee controller created by: Gavrilo Drljaca
 */


app.controller('employeeController', function ($rootScope, $scope, $window, $mdDialog, selectedEmployee, $filter){
	function init(){
		
			$scope.activeForm = "none";
		}
		
	
	 $scope.activeForm = "none";
	 $scope.currEmp = selectedEmployee;
	 
	 $scope.DateBirth = $filter('jsDate')(date, "dd-mm-yyyy");
	 alert($scope.DateBirth);
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