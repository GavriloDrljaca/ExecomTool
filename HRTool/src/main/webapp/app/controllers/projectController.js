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
			$scope.employees = {};
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
	
	$scope.addEmployeeToProject = function(employeeIndex){
		console.log(employeeIndex)
		$scope.newProjectInfo = {};
		$scope.newProjectInfo.jobResponsibilities = $scope.jobResponsibilities;
		$scope.newProjectInfo.projectExp = $scope.projectExp;
		if(!angular.equals(selectedProject, {})){
			//$scope.newProjectInfo.idProject = $scope.selectedProject._links.self.href;
			//$scope.newProjectInfo.idEmployee = $scope.otherEmployees[employeeIndex]._links.self.href;
			console.log($scope.selectedProject._links.self.href);
			console.log($scope.otherEmployees[employeeIndex]._links.self.href);
			projectInfoService.create($scope.newProjectInfo).success(function(data){
				console.log(data);
				var temp = data;
				projectInfoService.saveProject(temp, $scope.selectedProject._links.self.href).success(function(data){
					projectInfoService.saveEmployee(temp, $scope.otherEmployees[employeeIndex]._links.self.href);	
				})
			})	
		}
	}
	
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