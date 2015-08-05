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
	
	//Da spreci automatsko sortiranje ng-repeata
	$scope.objectKeys = function(obj){
		  return Object.keys(obj);
	}
	
	function getEmployees() {
		$scope.employees = [];
		projectInfoService.getForProject(selectedProject).success(function (data) {
			if(data._embedded != undefined) {
				$scope.projInfos = data._embedded.projectInfoes;
				//console.log( data);
			} else {
				$scope.projInfos = {};
			}
			for(i = 0; i<$scope.projInfos.length; i++) {
				var info = $scope.projInfos[i];
				projectInfoService.getOne(info).success((function(info, i) {
						return function(data){
						$scope.employees[i] = data;
						$scope.employees[i].projectInfo = info;
						}
				})(info, i));
			}
			getOtherEmployees($scope.employees);
		});
	};

	
	function getOtherEmployees(employees) {
		employeeService.list().success(function(data) {
			$scope.otherEmployees = data._embedded.employees;
			if (!$scope.newProject){
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
		for (i=0; i<$scope.employees.length; i++) {
			projInfos[i] = $scope.employees[i].projectInfo;
			/*projInfos[i].jobResponsibilities = $scope.projInfos[i].jobResponsibilities;
			projInfos[i].projectExp = $scope.projInfos[i].projectExp;*/
			projectInfoService.update(projInfos[i]);
		};
		selectedProject.nameProject = $scope.selectedProject.nameProject;
		selectedProject.durationOfProject = $scope.selectedProject.durationOfProject;
		$scope.jobResponsibilities = "";
		$scope.projectExp = "";
		projectService.update(selectedProject);
	};
	
	$scope.addEmployeeToProject = function(employeeIndex){
		$scope.newProjectInfo = {};
		$scope.newProjectInfo.jobResponsibilities = $scope.jobResponsibilities;
		$scope.newProjectInfo.projectExp = $scope.projectExp;
		if(!angular.equals(selectedProject, {})){
			projectInfoService.create($scope.newProjectInfo).success(function(data){
				var temp = data;
				projectInfoService.saveProject(temp, $scope.selectedProject._links.self.href).success(function(data){
					projectInfoService.saveEmployee(temp, $scope.otherEmployees[employeeIndex]._links.self.href).success(function(){
						$scope.otherEmployees.splice(employeeIndex,1);
						getEmployees();
					});	
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