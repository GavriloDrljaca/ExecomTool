app.controller('projectController', function($http, $scope, $window, $mdDialog, selectedProject, projectInfoService){
		
	$scope.selectedProject = selectedProject;	
	 
	$scope.init = function() {
		$scope.employees = [];
		getEmployees();	
	}
	
	function getEmployees() {
		
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
	
	$scope.answer = function(answer) {
		$mdDialog.hide(answer);
	}
});