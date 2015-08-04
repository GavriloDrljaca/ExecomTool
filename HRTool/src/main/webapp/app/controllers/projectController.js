app.controller('projectController', ['$http', '$scope', '$mdDialog', 'selectedProject', 'projectService', 'projectInfoService', 'employeeService', function($http, $scope, $mdDialog, selectedProject, projectService, projectInfoService, employeeService){
		
	$scope.selectedProject = selectedProject;
	
	$scope.init = function() {
		if (!angular.equals(selectedProject, undefined)){
			$scope.newProject = false;
			getEmployees();
		}else{
			$scope.updateable = true;
			$scope.newProject = true;
		}
		getOtherEmployees($scope.employees);
	};
	
	function getEmployees() {

		$scope.employees = [];
		$scope.projInfos = {};
		projectInfoService.getForProject(selectedProject).success(function (data) {
			if(data._embedded != undefined) {
				$scope.projInfos = data._embedded.projectInfoes;
			} else {
				$scope.projInfos = {};
			}
			for(i = 0; i<$scope.projInfos.length; i++) {
				projectInfoService.getOne($scope.projInfos[i]).success(function (data) {
					$scope.employees.push(data);
				});
			}
		});
	};
	
	function getOtherEmployees(employees) {
		employeeService.list().success(function(data) {
			console.log(data);
			$scope.otherEmployees = data._embedded.employees;
			if (!$scope.newProject){
				console.log(employees.length);
				for(i=0; i<employees.length; i++) {
					removable = $scope.otherEmployees.indexOf(employees[i]);
					if(removable>0) {
						$scope.otherEmployees.splice(removable,1);
					};
				};
			}
		});		
	};
	
	$scope.updateProject = function() {
		projInfos = [];
		console.log($scope.projInfos.length);
		for (i=0; i<$scope.projInfos.length; i++) {
			console.log($scope.projInfos[i]);
			projInfos[i] = $scope.projInfos[i];
			console.log(projInfos[i]);
			projInfos[i].jobResponsibilities = $scope.projInfos[i].jobResponsibilities;
			projInfos[i].projectExp = $scope.projInfos[i].projectExp;
			projectInfoService.update(projInfos[i]);
		};
		selectedProject.nameProject = $scope.selectedProject.nameProject;
		selectedProject.durationOfProject = $scope.selectedProject.durationOfProject;
		projectService.update(selectedProject);
	};
	
	$scope.answer = function(answer) {
		$mdDialog.hide(answer);
	};
}]);