app.controller('projectController', function($http, $scope, $mdDialog, selectedProject, employeeService){
		
	$scope.selectedProject = selectedProject;	
	 
	$scope.init = function() {
		getEmployees();
		getAllEmployees();
	}
	
	function getEmployees() {

		$scope.employees = [];
		$scope.projInfos = {};
		$http.get(selectedProject._links.projectInfo.href).success(function (data) {
			if(data._embedded != undefined) {
				$scope.projInfos = data._embedded.projectInfoes;
			} else {
				$scope.projInfos = {};
			}
			for(i = 0; i<$scope.projInfos.length; i++) {
				$http.get($scope.projInfos[i]._links.employee.href).success(function (data) {
					$scope.employees.push(data);
				});
			}
		});
	}
	
	function getAllEmployees() {
		employeeService.list().then(function(data) {
			$scope.otherEmployees = data.data._embedded.employees;
			for(i=0; i<$scope.employees.lenght; i++) {
				removable = $scope.otherEmployees.indexOf($scope.employees[i]);
				if(removable>0) {
					$scope.otherEmployees.splice(removable,1);
				};
			};
			console.log($scope.otherEmployees);
		});		
	}
	
	$scope.answer = function(answer) {
		$mdDialog.hide(answer);
	}
});