/**
 * Employee controller created by: Gavrilo Drljaca
 */


app.controller('employeeController', function ($rootScope, $scope, $window, $mdDialog, selectedEmployee, $filter){
	function init(){
		
			$scope.activeForm = "none";
		}
		
	
	 $scope.activeForm = "none";
	 $scope.currEmp = selectedEmployee;
	 
	 //Date d = new Date("dd-MM-YYYY");
	 d = Date(parseInt(selectedEmployee.dateOfBirth));
	 d.format = "dd-MM-YYYY";
	 d.toLocalFormat()
	// d = Date(day, month, year)
	 alert(d);
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
     /*
      * function toJSONLocal (date) {
	    var local = new Date(date);
	    local.setMinutes(date.getMinutes() - date.getTimezoneOffset());
	    return local.toJSON().slice(0, 10);
	}
      */
     
		  
	});