app.controller('projectController', ['$http', '$scope', '$mdDialog', 'selectedProject', 'projectService', 'projectInfoService', 'employeeService', function($http, $scope, $mdDialog, selectedProject, projectService, projectInfoService, employeeService){
		
	$scope.selectedProject = selectedProject;
	
	$scope.init = function() {
		if (!angular.equals(selectedProject, {}	)){
			$scope.newProject = false;
			getEmployees();
		}else{
			$scope.projInfos = {};
			$scope.updateable = true;
			$scope.newProject = true;
			getOtherEmployees();
		}
		
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
			getOtherEmployees($scope.employees);
		});
	};
	
	function getOtherEmployees(employees) {
		//$http.get('/employees').success(function(data) {
		employeeService.list().success(function(data) {
			console.log(data);
			$scope.otherEmployees = data._embedded.employees;
			if (!$scope.newProject){
				console.log(employees.length);
				var temp = $scope.otherEmployees;
				for(i=0; i<employees.length; i++) {
					for (k=0; k<temp.length; k++){
						if (temp[k]._links.self.href == employees[i]._links.self.href){
							$scope.otherEmployees.splice(k,1);
						}
					}
					/*removable = 0;
					removable = $scope.otherEmployees.indexOf(employees[i]);
					if(removable>0) {
						$scope.otherEmployees.splice(removable,1);
					};*/
				};
			}
		});		
	};
	
	$scope.setSelectedEmployee = function(employeeIndex){
		$scope.selectedEmployeeIndex = employeeIndex;
	}
	
	$scope.updateProject = function() {
		if ($scope.selectedProject.nameProject == ""){
			$scope.selectedProject.nameProject = "No name given";
		}
		projInfos = [];
		for (i=0; i<$scope.projInfos.length; i++) {
			projInfos[i] = $scope.projInfos[i];
			projInfos[i].jobResponsibilities = $scope.projInfos[i].jobResponsibilities;
			projInfos[i].projectExp = $scope.projInfos[i].projectExp;
			projectInfoService.update(projInfos[i]);
		};
		selectedProject.nameProject = $scope.selectedProject.nameProject;
		selectedProject.durationOfProject = $scope.selectedProject.durationOfProject;
		$scope.jobResponsibilities = "";
		$scope.projectExp = "";
		projectService.update(selectedProject);
	};
	
	$scope.createProject = function(){
		if ($scope.selectedProject.nameProject == "" || $scope.selectedProject.nameProject == undefined){
			$scope.selectedProject.nameProject = "No name given";
		}
		projInfos = [];
		for (i=0; i<$scope.projInfos.length; i++) {
			projInfos[i] = $scope.projInfos[i];
			projInfos[i].jobResponsibilities = $scope.projInfos[i].jobResponsibilities;
			projInfos[i].projectExp = $scope.projInfos[i].projectExp;
			projectInfoService.update(projInfos[i]);
		};
		selectedProject.nameProject = $scope.selectedProject.nameProject;
		selectedProject.durationOfProject = $scope.selectedProject.durationOfProject;
		projectService.save(selectedProject).success(function(){
			$mdDialog.cancel();
		});
	}
	
	$scope.deleteProject = function(){
		projectService.delete(selectedProject).success(function(){
			$mdDialog.cancel();
		})
	}
	
	$scope.answer = function(answer) {
		$mdDialog.hide(answer);
	};
}]);