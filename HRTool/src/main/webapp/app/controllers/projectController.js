app.controller('projectController', ['$http', '$scope', '$window', '$mdDialog', 'selectedProject', 'projectService', 
                                     'projectInfoService', 'employeeService', 'tagCloudService',
                                     function($http, $scope, $window, $mdDialog, selectedProject, projectService, projectInfoService, employeeService, tagCloudService){
		
	$scope.selectedProject = selectedProject;
	var allIndustries = [];
	var allPlatforms = [];
	var allOss = [];
	
	$scope.init = function() {
		if (!angular.equals(selectedProject, {}	)){
			$scope.newProject = false;
			getEmployees();
			getAllTagCloudsForProject();
			getTagCloudsForProject();
		}else{
			$scope.projInfos = {};
			$scope.updateable = true;
			$scope.newProject = true;
			$scope.employees = {};
			getOtherEmployees();
			getAllTagCloudsForProject();
		}
	};
	
	//Da spreci automatsko sortiranje ng-repeata
	$scope.objectKeys = function(obj){
		  return Object.keys(obj);
	}
	
	//Dobavlja sve zaposlene aktivne na projektu
	function getEmployees() {
		$scope.employees = [];
		projectInfoService.getForProject(selectedProject).success(function (data) {
			if(data._embedded != undefined) {
				$scope.projInfos = data._embedded.projectInfoes;
			} else {
				$scope.projInfos = {};
			}
			var counter = 0;
			for(i = 0; i<$scope.projInfos.length; i++) {
				var info = $scope.projInfos[i];
				projectInfoService.getOne(info).success((function(info) {
						return function(data){
							if (info.active == true){	
								$scope.employees.push(data);
								$scope.employees[counter].projectInfo = info;
								counter++;
							}
						}
				})(info));
			}
			getOtherEmployees($scope.employees);
		});
	};

	//Odredjuje koji zaposleni nisu na projektu i stavlja ih u listu izbora za dodavanje
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
	
	//Cuva izmene nad projektom na serveru
	$scope.updateProject = function() {
		if ($scope.selectedProject.nameProject == ""){
			$scope.selectedProject.nameProject = "No name given";
		}
		projInfos = [];
		for (i=0; i<$scope.employees.length; i++) {
			projInfos[i] = $scope.employees[i].projectInfo;
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
			$scope.newProjectInfo.active = true;
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
	
	$scope.removeEmployeeFromProject = function(employee){
		console.log(employee);
		console.log(employee.projectInfo);
		employee.projectInfo.active = false;
		projectInfoService.update(employee.projectInfo).success(function(){
			getEmployees();			
		})
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
		projectService.save(selectedProject).success(function(data){
			selectedProject = data;
			$scope.selectedProject = data;
			$scope.newProject = false;
			$scope.updateable = false;
			$scope.init();
		});
	};
	
	$scope.deleteProject = function(){
		if ($window.confirm("Do you really want to delete the project " + selectedProject.nameProject)) {
			projectService.delete(selectedProject).success(function(){
				$mdDialog.cancel();
			});
		};
	};
	
	var getTagCloudsForProject = function() {
		projectService.tagCloudsForProject(selectedProject).success(function (data) {
			var tagClouds = data._embedded.tagClouds;
			$scope.industries = [];
			$scope.platforms = [];
			$scope.oss = [];
			for (i=0; i<tagClouds.length; i++) {
				if (tagClouds[i].tipTagCloud === "Industry") {
					$scope.industries.push(tagClouds[i]);
				} else if (tagClouds[i].tipTagCloud === "Platform" ) {
					$scope.platforms.push(tagClouds[i]);
				} else if (tagClouds[i].tipTagCloud === "OS") {
					$scope.oss.push(tagClouds[i]);
				};
			};
			$scope.updateAllIndustries();
		});
	};

	$scope.updateAllIndustries = function() {
		$scope.allIndustries = [];
		for (i=0; i<allIndustries.length; i++) {
			found = false;
			for (j=0; j<$scope.industries.length; j++) {
				if (allIndustries[i]._links.self.href === $scope.industries[j]._links.self.href) {
					found = true;
					break;
				};
			};
			if (!found) {
				$scope.allIndustries.push(allIndustries[i]);
			}
		};
	};
	
	$scope.updateAllPlatforms = function() {
		$scope.allPlatforms = [];
		for (i=0; i<allPlatforms.length; i++) {
			found = false;
			for (j=0; j<$scope.platforms.length; j++) {
				if (allPlatforms[i]._links.self.href === $scope.platforms[j]._links.self.href) {
					found = true;
					break;
				};
			};
			if (!found) {
				$scope.allPlatforms.push(allPlatforms[i]);
			}
		};
	};
	
	$scope.updateAllOss = function() {
		$scope.allOss = [];
		for (i=0; i<allOss.length; i++) {
			found = false;
			for (j=0; j<$scope.platforms.length; j++) {
				if (allOss[i]._links.self.href === $scope.oss[j]._links.self.href) {
					found = true;
					break;
				};
			};
			if (!found) {
				$scope.allOss.push(allOss[i]);
			}
		};
	};
	
	var getAllTagCloudsForProject = function() {
		tagCloudService.findByTip("Industry").success(function (data) {
			allIndustries = data._embedded.tagClouds;
		});
		tagCloudService.findByTip("Platform").success(function (data) {
			allPlatforms = data._embedded.tagClouds;
		});
		tagCloudService.findByTip("OS").success(function (data) {
			allOss = data._embedded.tagClouds;
		});
	};
	
	$scope.answer = function(answer) {
		$mdDialog.hide(answer);
	};
}]);