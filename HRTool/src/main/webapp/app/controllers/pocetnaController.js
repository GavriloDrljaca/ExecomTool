app.controller('pocetnaController', function( $scope, $window, $mdDialog, pocetnaFactory) {
	 
	function init(){
		pocetnaFactory.getEmployees().success(function(data){
			$scope.employees = data;
		})		
		
		pocetnaFactory.getProjects().success(function(data){
			$scope.projects = data;
		})	
	}
	
	init();
	
	
	  $scope.showEmployee = function(ev, emp) {
		$scope.selectedEmployee = emp;
	    $mdDialog.show({
	      controller: 'employeeController',
	      controllerAs: 'empCtrl',
	      templateUrl: 'app/partials/employeeDialog.html',
	      parent: angular.element(document.body),
	      targetEvent: ev,
	      locals: {
	          selectedEmployee: $scope.selectedEmployee
	        }
	    })
	    .then(function(answer) {
	      $scope.alert = 'You said the information was "' + answer + '".';
	    }, function() {
	      $scope.alert = 'You cancelled the dialog.';
	    });
	  };
	  
	  $scope.showProject = function(ev, emp) {
			$scope.selectedEmployee = emp;
		    $mdDialog.show({
		      controller: 'employeeController',
		      controllerAs: 'empCtrl',
		      templateUrl: 'app/partials/employeeDialog.html',
		      parent: angular.element(document.body),
		      targetEvent: ev,
		      locals: {
		          selectedEmployee: $scope.selectedEmployee
		        }
		    })
		    .then(function(answer) {
		      $scope.alert = 'You said the information was "' + answer + '".';
		    }, function() {
		      $scope.alert = 'You cancelled the dialog.';
		    });
		  };
	});
