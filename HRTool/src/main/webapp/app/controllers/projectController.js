app.controller('projectController', ['$http', '$scope', '$window', '$mdDialog', 'selectedProject', 'projectService', 
                                     'projectInfoService', 'employeeService', 'tagCloudService',
                                     function($http, $scope, $window, $mdDialog, selectedProject, projectService, projectInfoService, employeeService, tagCloudService){
		
	$scope.selectedProject = selectedProject;
	var allIndustries = [];
	var allPlatforms = [];
	var allOss = [];
	var allTechnologies = [];
	var allIDEs = [];
	var allDatabases = [];
	var allJobRoles = [];
	
	$scope.init = function() {
		if (!angular.equals(selectedProject, {}	)){
			$scope.newProject = false;
			$scope.selectedProject.startDate = new Date(selectedProject.startDate);
			getEmployees();
			getAllTagCloudsForProject();
			getAllTagCloudsForProjectInfo();
			getTagCloudsForProject();
		}else{
			$scope.projInfos = {};
			$scope.updateable = true;
			$scope.newProject = true;
			$scope.employees = {};
			getOtherEmployees();
			getAllTagCloudsForProject();
			getAllTagCloudsForProjectInfo();
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

	$scope.employeeClicked = function (employee) {
		console.log("tu sam");
		if ($scope.firstTimeClicked === undefined) {
			$scope.firstTimeClicked = true;
		}
		$scope.clickedEmployee = employee;
		fillChipsForClickedEmployee(employee);
	}

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
		/*for (i=0; i<$scope.employees.length; i++) {
			projInfos[i] = $scope.employees[i].projectInfo;
			projectInfoService.update(projInfos[i]);
		};*/
		projectInfoService.update($scope.clickedEmployee.projectInfo);
		selectedProject.nameProject = $scope.selectedProject.nameProject;
		selectedProject.startDate = $scope.selectedProject.startDate;
		selectedProject.durationOfProject = $scope.selectedProject.durationOfProject;
		$scope.jobResponsibilities = "";
		$scope.projectExp = "";
		saveProjectTags();
		saveProjectInfoTags();
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
			if (data._embedded != undefined) {
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
			};
		});
	};
	
	var fillChipsForClickedEmployee = function (clickedEmployee) {
		$scope.clickedEmployee.jobRoles = [];
		$scope.clickedEmployee.technologies = [];
		$scope.clickedEmployee.databases = [];
		$scope.clickedEmployee.ides = [];
		tagCloudService.getForProjectInfo(clickedEmployee.projectInfo).success(function (data) {
			var tagClouds = data._embedded.tagClouds;
			for (i=0; i<tagClouds.length; i++) {
				switch (tagClouds[i].tipTagCloud) {
				case "JobRole":
					$scope.clickedEmployee.jobRoles.push(tagClouds[i]);
					break;
				case "Technologie":
					$scope.clickedEmployee.technologies.push(tagClouds[i]);
					break;
				case "Database":
					$scope.clickedEmployee.databases.push(tagClouds[i]);
					break;
				case "IDE":
					$scope.clickedEmployee.databases.push(tagClouds[i]);
					break;
				}
			}
		});
	};

	$scope.updateAllIndustries = function() {
		$scope.allIndustries = [];
		if ($scope.industries === undefined) {
			$scope.industries = [];
		}
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
		if ($scope.platforms === undefined) {
			$scope.platforms = [];
		}
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
		if ($scope.oss === undefined) {
			$scope.oss = [];
		}
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
	
	$scope.updateAllJobRoles = function() {
		$scope.allJobRoles = [];
		if ($scope.clickedEmployee.jobRoles === undefined) {
			$scope.clickedEmployee.jobRoles = [];
		}
		for (i=0; i<allJobRoles.length; i++) {
			found = false;
			for (j=0; j<$scope.clickedEmployee.jobRoles.length; j++) {
				if (allJobRoles[i]._links.self.href === $scope.clickedEmployee.jobRoles[j]._links.self.href) {
					found = true;
					break;
				};
			};
			if (!found) {
				$scope.allJobRoles.push(allJobRoles[i]);
			}
		};
	};
	
	$scope.updateAllDatabases = function() {
		$scope.allDatabases = [];
		if ($scope.clickedEmployee.databases === undefined) {
			$scope.clickedEmployee.databases = [];
		}
		for (i=0; i<allDatabases.length; i++) {
			found = false;
			for (j=0; j<$scope.clickedEmployee.databases.length; j++) {
				if (allDatabases[i]._links.self.href === $scope.clickedEmployee.databases[j]._links.self.href) {
					found = true;
					break;
				};
			};
			if (!found) {
				$scope.allDatabases.push(allDatabases[i]);
			}
		};
	};
	
	$scope.updateAllIDEs = function() {
		$scope.allIDEs = [];
		if ($scope.clickedEmployee.ides === undefined) {
			$scope.clickedEmployee.ides = [];
		}
		for (i=0; i<allIDEs.length; i++) {
			found = false;
			for (j=0; j<$scope.clickedEmployee.ides.length; j++) {
				if (allIDEs[i]._links.self.href === $scope.clickedEmployee.ides[j]._links.self.href) {
					found = true;
					break;
				};
			};
			if (!found) {
				$scope.allIDEs.push(allIDEs[i]);
			}
		};
	};
	
	$scope.updateAllTechnologies = function() {
		$scope.allTechnologies = [];
		if ($scope.clickedEmployee.technologies === undefined) {
			$scope.clickedEmployee.technologies = [];
		}
		for (i=0; i<allTechnologies.length; i++) {
			found = false;
			for (j=0; j<$scope.clickedEmployee.technologies.length; j++) {
				if (allTechnologies[i]._links.self.href === $scope.clickedEmployee.technologies[j]._links.self.href) {
					found = true;
					break;
				};
			};
			if (!found) {
				$scope.allTechnologies.push(allTechnologies[i]);
			}
		};
		console.log($scope.allTechnologies);
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
	
	var getAllTagCloudsForProjectInfo = function () {
		tagCloudService.findByTip("JobRole").success(function (data) {
			allJobRoles = data._embedded.tagClouds;
		});
		tagCloudService.findByTip("Technologie").success(function (data) {
			allTechnologies = data._embedded.tagClouds;
		});
		tagCloudService.findByTip("IDE").success(function (data) {
			allIDEs = data._embedded.tagClouds;
		});
		tagCloudService.findByTip("Database").success(function (data) {
			allDatabases = data._embedded.tagClouds;
		});
	}
	
	$scope.addNewTagCloud = function(newName, type){
		var newTag = {};
		newTag.nameTagCloud = newName;
		newTag.tipTagCloud = type;
		tagCloudService.create(newTag).success(function(data){
			if (data.tipTagCloud === "Industry") {
				allIndustries.push(data);
				$scope.industries.push(data);
			} else if (data.tipTagCloud === "Platform") {
				allPlatforms.push(data);
				$scope.platforms.push(data);
			} else if (data.tipTagCloud === "OS") {
				allOss.push(data);
				$scope.oss.push(data);
			} else if (data.tipTagCloud === "Technologie") {
				allTechnologies.push(data);
				$scope.clickedEmployee.techonologies.push(data);
			} else if (data.tipTagCloud === "Database") {
				allDatabases.push(data);
				$scope.clickedEmployee.databases.push(data);
			} else if (data.tipTagCloud === "IDE") {
				allIDEs.push(data);
				$scope.clickedEmployee.ides.push(data);
			} else if (data.tipTagCloud === "JobRole") {
				allJobRoles.push(data);
				$scope.clickedEmployee.jobRoles.push(data);
			};
		});
	};
	
	var saveProjectTags = function(){
		var req = "";
		var newTags = [];
		newTags = newTags.concat($scope.industries, $scope.platforms, $scope.oss);
		if (newTags !== undefined) {
			for(i =0; i<newTags.length; i++){
				req += newTags[i]._links.self.href +"\n";
			}
		};
		tagCloudService.saveTag(selectedProject._links.tagClouds.href, req)
	}
	
	var saveProjectInfoTags = function() {
		var req = "";
		var newTags = [];
		newTags = newTags.concat($scope.clickedEmployee.technologies, $scope.clickedEmployee.ides, $scope.clickedEmployee.databases, $scope.clickedEmployee.jobRoles);
		if (newTags !== undefined) {
			for(i =0; i<newTags.length; i++){
				req += newTags[i]._links.self.href +"\n";
			}
		};
		tagCloudService.saveTag($scope.clickedEmployee.projectInfo._links.tagClouds.href, req)
	}
	
	//autocomplete search
    $scope.querySearch = function (query, tip) {
    	var tagClouds = [];
    	switch(tip) {
    	case "Industry":
			tagClouds = $scope.allIndustries;
			break;
		case "Platform":
			tagClouds = $scope.allPlatforms;
			break;
		case "OS":
			tagClouds = $scope.allOss;
			break;
		case "JobRole":
			tagClouds = $scope.allJobRoles;
			break;
		case "Technologie":
			tagClouds = $scope.allTechnologies;
			break;
		case "Database":
			tagClouds = $scope.allDatabases;
			break;
		case "IDE":
			tagClouds = $scope.allIDEs;
			break;
		};
	    var results = query ? tagClouds.filter( createFilterFor(query, tip) ) : [];
		return results;
    };
    
	function createFilterFor(query, tip) {
		var lowercaseQuery = angular.lowercase(query);
		var lowercaseTip = angular.lowercase(tip);
		return function filterFn(tag) {
			return ((tag.nameTagCloud.toLowerCase().indexOf(lowercaseQuery) === 0) ||
			        (tag.tipTagCloud.toLowerCase().indexOf(lowercaseQuery) === 0) ) &&
		            (tag.tipTagCloud.toLowerCase().indexOf(lowercaseTip) === 0);
		}
	}
	
	$scope.answer = function(answer) {
		$mdDialog.hide(answer);
	};
}]);