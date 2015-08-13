app.controller('projectController', ['$http', '$scope', '$mdToast', '$animate','$window', '$mdDialog', 'selectedProject', 'projectService', 
                                     'projectInfoService', 'employeeService', 'tagCloudService',
                                     function($http, $scope, $mdToast, $animate, $window, $mdDialog, selectedProject, projectService, projectInfoService, employeeService, tagCloudService){
		
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
			isExecom();
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
	
	var isExecom = function() {
		if (selectedProject.execom) {
			$scope.execom = "true";
		} else {
			$scope.execom = "false";
		}
	}
	
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
			var allEmployees = data._embedded.employees;
			$scope.otherEmployees = [];
			if (!$scope.newProject){
				for(i=0; i<allEmployees.length; i++) {
					var found = false;
					for (k=0; k<employees.length; k++){
						if (employees[k]._links.self.href === allEmployees[i]._links.self.href){
							found = true;
						};
					};
					if (!found) {
						$scope.otherEmployees.push(allEmployees[i]);
					};
				};
			}
		});		
	};
	
	$scope.setSelectedEmployee = function(employeeIndex){
		$scope.selectedEmployeeIndex = employeeIndex;
	}

	var previousClickedEmployee = undefined;
	$scope.employeeClicked = function (index) {
		if ($scope.firstTimeClicked !== true) {
			$scope.firstTimeClicked = true;
		};
		if (previousClickedEmployee === undefined) {
			$scope.clickedEmployee = $scope.employees[index];
			previousClickedEmployee = index;
			fillChipsForClickedEmployee($scope.clickedEmployee);
			$scope.clickedEmployee.firstTimeChipsInit = false;
		} else {
			console.log($scope.clickedEmployee);
			console.log($scope.employees[previousClickedEmployee]);
			$scope.employees[previousClickedEmployee] = $scope.clickedEmployee;
			/*$scope.employees[previousClickedEmployee].projectInfo.jobResponsibilities = $scope.clickedEmployee.projectInfo.jobResponsibilities;
			$scope.employees[previousClickedEmployee].projectInfo.projectExp = $scope.clickedEmployee.projectInfo.projectExp;
			$scope.employees[previousClickedEmployee].projectInfo.durationOnProject = $scope.clickedEmployee.projectInfo.durationOnProject;
			$scope.employees[previousClickedEmployee].projectInfo.seniority = $scope.clickedEmployee.projectInfo.seniority;
			$scope.employees[previousClickedEmployee].jobRoles = $scope.clickedEmployee.jobRoles;
			$scope.employees[previousClickedEmployee].technologies = $scope.clickedEmployee.technologies;
			$scope.employees[previousClickedEmployee].databases = $scope.clickedEmployee.databases;
			$scope.employees[previousClickedEmployee].ides = $scope.clickedEmployee.ides;*/
			
			$scope.clickedEmployee = $scope.employees[index];
			if ($scope.clickedEmployee.firstTimeChipsInit === undefined || $scope.clickedEmployee.firstTimeChipsInit) {
				console.log("usao sam");
				fillChipsForClickedEmployee($scope.clickedEmployee);
				$scope.clickedEmployee.firstTimeChipsInit = false;
			};
			previousClickedEmployee = index;
		};
		
	}
	
	//Cuva izmene nad projektom na serveru
	$scope.updateProject = function() {
		if ($scope.selectedProject.nameProject == ""){
			$scope.selectedProject.nameProject = "No name given";
		}
		projInfos = [];
		
		// if none of the employees is clicked
		for (i=0; i<$scope.employees.length; i++) {
			projectInfoService.update($scope.employees[i].projectInfo);
			saveProjectInfoTags($scope.employees[i]);
		}
		if ($scope.clickedEmployee !== undefined) {
			projectInfoService.update($scope.clickedEmployee.projectInfo);
			saveProjectInfoTags($scope.clickedEmployee);			
		}
		selectedProject.nameProject = $scope.selectedProject.nameProject;
		selectedProject.startDate = $scope.selectedProject.startDate;
		selectedProject.durationOfProject = $scope.selectedProject.durationOfProject;
		selectedProject.companyName = $scope.selectedProject.companyName;
		if ($scope.execom === "true") {
			selectedProject.execom = true;
		} else {
			selectedProject.execom = false;
		}
		$scope.jobResponsibilities = "";
		$scope.projectExp = "";
		saveProjectTags();
		projectService.update(selectedProject).success(function (data) {
			$scope.showSimpleToast();
		});
	};
	
	$scope.addEmployeeToProject = function(employeeIndex) {
		$scope.newProjectInfo = {};
		$scope.newProjectInfo.jobResponsibilities = $scope.jobResponsibilities;
		$scope.newProjectInfo.projectExp = $scope.projectExp;
		if(!angular.equals(selectedProject, {}) && employeeIndex !== undefined){
			$scope.newProjectInfo.active = true;
			projectInfoService.create($scope.newProjectInfo).success(function(data){
				var temp = data;
				projectInfoService.saveProject(temp, $scope.selectedProject._links.self.href).success(function(data){
					if ($scope.otherEmployees[employeeIndex] !== undefined) {
						projectInfoService.saveEmployee(temp, $scope.otherEmployees[employeeIndex]._links.self.href).success(function(){
							getEmployees();
						});	
					};
				});
			});
		};
	};
	
	var checkDuplicateEmployeeOnTheProject = function (employee, array) {
		for (i=0; i<array.length; i++) {
			if (employee._links.self.href === array[i]._links.self.href) {
				return true;
			};
		};
		return false;
	};
	
	$scope.removeEmployeeFromProject = function(employee){
		if ($scope.clickedEmployee !== undefined) {
			if (employee._links.self.href === $scope.clickedEmployee._links.self.href) {
				$scope.firstTimeClicked = false;
			}
		}
		employee.projectInfo.active = false;
		projectInfoService.update(employee.projectInfo).success(function(){
			getEmployees();			
		});
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
		selectedProject.companyName = $scope.selectedProject.companyName;
		if ($scope.execom === "true") {
			selectedProject.execom = true;
		} else {
			selectedProject.execom = false;
		}
		projectService.save(selectedProject).success(function(data){
			selectedProject = data;
			$scope.selectedProject = data;
			saveProjectTags();
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
			};
		});
	};
	
	var fillChipsForClickedEmployee = function (clickedEmployee) {
		$scope.clickedEmployee.jobRoles = [];
		$scope.clickedEmployee.technologies = [];
		$scope.clickedEmployee.databases = [];
		$scope.clickedEmployee.ides = [];
		tagCloudService.getForProjectInfo(clickedEmployee.projectInfo).success(function (data) {
			tagClouds = [];
			if (data._embedded !== undefined) {
				tagClouds = data._embedded.tagClouds;
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
						$scope.clickedEmployee.ides.push(tagClouds[i]);
						break;
					}
				}
			};
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
			for (j=0; j<$scope.oss.length; j++) {
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
				if (!checkDuplicate(newTag.nameTagCloud, $scope.industries)) {
					allIndustries.push(data);
					$scope.industries.push(data);					
				}
			} else if (data.tipTagCloud === "Platform") {
				if (!checkDuplicate(newTag.nameTagCloud, $scope.platforms)) {
					allPlatforms.push(data);
					$scope.platforms.push(data);
				}
			} else if (data.tipTagCloud === "OS") {
				if (!checkDuplicate(newTag.nameTagCloud, $scope.oss)) {
					allOss.push(data);
					$scope.oss.push(data);
				}
			} else if (data.tipTagCloud === "Technologie") {
				if (!checkDuplicate(newTag.nameTagCloud, $scope.clickedEmployee.technologies)) {
					allTechnologies.push(data);
					$scope.clickedEmployee.technologies.push(data);
				}
			} else if (data.tipTagCloud === "Database") {
				if (!checkDuplicate(newTag.nameTagCloud, $scope.clickedEmployee.databases)) {
					allDatabases.push(data);
					$scope.clickedEmployee.databases.push(data);
				}
			} else if (data.tipTagCloud === "IDE") {
				if (!checkDuplicate(newTag.nameTagCloud, $scope.clickedEmployee.ides)) {
					allIDEs.push(data);
					$scope.clickedEmployee.ides.push(data);
				}
			} else if (data.tipTagCloud === "JobRole") {
				if (!checkDuplicate(newTag.nameTagCloud, $scope.clickedEmployee.jobRoles)) {
					allJobRoles.push(data);
					$scope.clickedEmployee.jobRoles.push(data);
				};
			};
		});
	};
	
	var checkDuplicate = function (nameTagCloud, array) {
		for (i=0; i<array.length; i++) {
			if (nameTagCloud === array[i].nameTagCloud) {
				return true;
			};
		};
		return false;
	};
	
	var saveProjectTags = function(){
		console.log("usao sam");
		var req = "";
		var newTags = [];
		if ($scope.industries !== undefined) {
			newTags = newTags.concat($scope.industries);
		};
		if ($scope.platforms !== undefined) {
			newTags = newTags.concat($scope.platforms);
		};
		if ($scope.oss !== undefined) {
			newTags = newTags.concat($scope.oss);
		};
		if (newTags.length > 0) {
			for(i =0; i<newTags.length; i++){
				req += newTags[i]._links.self.href +"\n";
			}
		};
		tagCloudService.saveTag(selectedProject._links.tagClouds.href, req)
	}
	
	var saveProjectInfoTags = function(emp) {
		var req = "";
		var newTags = [];
		if (emp !== undefined) {
			if (emp.technologies !== undefined) {
				newTags = newTags.concat(emp.technologies);
			};
			if (emp.ides !== undefined) {
				newTags = newTags.concat(emp.ides);
			};
			if (emp.databases !== undefined) {
				newTags = newTags.concat(emp.databases);
			};
			if (emp.jobRoles !== undefined) {
				newTags = newTags.concat(emp.jobRoles);
			};
			if (newTags.length > 0) {
				for(i =0; i<newTags.length; i++){
					req += newTags[i]._links.self.href +"\n";
				}
			};
			tagCloudService.saveTag(emp.projectInfo._links.tagClouds.href, req);
		}
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
	
	//Project updated Toast
	$scope.toastPosition = {
	    bottom: false,
	    top: true,
	    left: false,
	    right: true
	};
	
	$scope.getToastPosition = function() {
	    return Object.keys($scope.toastPosition)
	      .filter(function(pos) { return $scope.toastPosition[pos]; })
	      .join(' ');
	};
	
	$scope.showSimpleToast = function() {
	    $mdToast.show(
	      $mdToast.simple()
	        .content("Project " + selectedProject.nameProject + " updated!")
	        .position($scope.getToastPosition())
	        .hideDelay(3000)
	    );
	};
	
}]);