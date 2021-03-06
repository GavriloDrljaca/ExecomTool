app.controller('startPageController', function($http, $scope, $window, $mdDialog, startPageFactory, employeeService, projectService, tagCloudService) {

	//Initializes starting data 
	$scope.init = function() {
		$http({
			url: "/employeeRole",
			method: "GET"
		}).success(function(data){
			$scope.role = data.role;
			if ($scope.role=="HRM"){	
				$scope.projectGroup = "Execom";
				$scope.employeeGroup = "Employed";
				$scope.report = {};
				$scope.initSearchTagDictionary();
				employeeService.list().success(function(data) {
					$scope.allEmployees = data._embedded.employees;
					$scope.setEmployeeList("Employed");
				});
				projectService.list().success(function(data) {
					$scope.allProjects = data._embedded.projects;
				});
				$scope.getExecomProjects();
				
				$scope.newEmployee = {};
				$scope.newProject = {};
			}else if($scope.role=="EMP"){
				$http.get('/restrictedEmployees/employee').success(function(data){
					$scope.employees = data;
				})
			}else if ($scope.role=="OFF"){
				$http.get('/restrictedEmployees/officeManager').success(function(data){
					$scope.employees = data;
				})
			}
		});
	}
	
	//Sets the employees that should be visible in the employee table depending on the checked radio button 
	$scope.setEmployeeList = function(employeeGroup){
		$scope.employees = [];
		$scope.employeeGroup = employeeGroup;
		if ($scope.role == "HRM"){
			if ($scope.employeeGroup == "Employed"){
				angular.forEach($scope.allEmployees, function(value, key){
					$http.get(value._links.empInfos.href).success((function(value) {
						return	function(data){
						if (!angular.equals(data._embedded, undefined) && !angular.equals(data._embedded.employmentInfoes, undefined)){	
							for (i=0; i<data._embedded.employmentInfoes.length; i++){
								if (data._embedded.employmentInfoes[i].companyName.toLowerCase() == "execom" &&
										data._embedded.employmentInfoes[i].endDate == undefined){
									$scope.employees.push(value);
									break;
								}
							}
						}
					}
					})(value)); 
				})
			}else {
				$scope.employees = $scope.allEmployees;
			}
		}
	}
	
	//Sets the projects that should be visible in the projects table depending on the checked radio button
	$scope.setProjectList = function(projectGroup){
		$scope.projects = [];
		$scope.projectGroup = projectGroup
		if (projectGroup == "Execom"){
			angular.forEach($scope.allProjects, function(value, key){
				if (value.execom == true){
					$scope.projects.push(value);
				}
			})
		}else if(projectGroup == "Active"){
			angular.forEach($scope.allProjects, function(value, key){
				$http.get(value._links.projectInfo.href).success((function(value) {
					return	function(data){
					if (!angular.equals(data._embedded, undefined) && !angular.equals(data._embedded.projectInfoes, undefined)){	
						for (i=0; i<data._embedded.projectInfoes.length; i++){
							if (data._embedded.projectInfoes[i].active && value.execom == true){
								$scope.projects.push(value);
								break;
							}
						}
					}
				}
				})(value)); 
			})
		}else {
			$scope.projects = $scope.allProjects;
		}
	}
	
	//Gets the projects that are marked as Execom projects from the server
	$scope.getExecomProjects = function(){
		projectService.list().success(function(data) {
			$scope.projects = [];
			angular.forEach(data._embedded.projects, function(value, key){
				if (value.execom == true){
					$scope.projects.push(value);
				}
			})
			$scope.execomProjects = $scope.projects;
		})
	}
	
	$scope.submit = function(){
		employeeService.create($scope.currEmp);
	}

	$scope.deleteProject = function(project){
		projectService.delete(project);
	}
	
	$scope.deleteEmployee = function(emp){
		employeeService.delete(emp);
	}

	//Opens the employee dialog when opened from report tab 
	$scope.showEmployeeReport = function(event, employee) {
		$http.get('/employees/'+employee.idEmployee).success(function(data){
			$scope.selectedEmployee = data;
			$scope.showEmployee(event, $scope.selectedEmployee, employee)
		})
	};
	
	
	//Opens the employee dialog(depends on role of employee)
	$scope.showEmployee = function(event, employee, employeeFromReport) {
		$scope.selectedEmployee = employee;
		if($scope.role=="HRM"){
			$mdDialog.show({
				controller : 'employeeController',
				controllerAs : 'empCtrl',
				templateUrl : 'app/partials/employeeDialog.html',
				parent : angular.element(document.body),
				targetEvent : event,
				locals : {
					selectedEmployee : $scope.selectedEmployee
				}
			}).then(function(answer) {
				if (!angular.equals(employeeFromReport, undefined)){	
					$http.get('/employees/'+employeeFromReport.idEmployee).success(function(data){	
						employeeFromReport.nameEmployee = data.nameEmployee; 
					})
				}
				employeeService.list().success(function(data){
					$scope.allEmployees = data._embedded.employees;
					$scope.setEmployeeList($scope.employeeGroup);
					$scope.newEmployee ={};
				});
				projectService.list().success(function(data){
					$scope.allProjects = data._embedded.projects;
					$scope.setProjectList($scope.projectGroup);
				})
			}, function() {
				if (!angular.equals(employeeFromReport, undefined)){	
					$http.get('/employees/'+employeeFromReport.idEmployee).success(function(data){	
						employeeFromReport.nameEmployee = data.nameEmployee; 
					})
				}
				employeeService.list().success(function(data){
					$scope.allEmployees = data._embedded.employees;
					$scope.setEmployeeList($scope.employeeGroup);
					$scope.newEmployee ={};
				});
				projectService.list().success(function(data){
					$scope.allProjects = data._embedded.projects;
					$scope.setProjectList($scope.projectGroup);
				})
			});
		}else{
			$mdDialog.show({
				controller : 'restrictedEmployeeController',
				controllerAs : 'restrEmpCtrl',
				templateUrl : 'app/partials/restrictedEmployeeDialog.html',
				parent : angular.element(document.body),
				targetEvent : event,
				locals : {
					selectedEmployee : $scope.selectedEmployee,
					role : $scope.role
				}
			}).then(function(answer) {
				
			}, function() {

			});
		}
	};

	//Opens the project dialog
	$scope.showProject = function(ev, prj) {
		$scope.selectedProject = prj;
		$mdDialog.show({
			controller : 'projectController',
			controllerAs : 'prjCtrl',
			templateUrl : 'app/partials/projectDialog.html',
			parent : angular.element(document.body),
			targetEvent : ev,
			locals : {
				selectedProject : $scope.selectedProject
			}
		}).then(function(answer) {
			projectService.list().success(function(data){
				$scope.allProjects = data._embedded.projects;
				$scope.setProjectList($scope.projectGroup);
				$scope.newProject ={};
			})
		}, function() {
			projectService.list().success(function(data){
				$scope.allProjects = data._embedded.projects;
				$scope.setProjectList($scope.projectGroup);
				$scope.newProject ={};
			})
		});
	};
	
	//Sends search parameters and receives the employees from the server 
	$scope.makeReport = function(){
		$scope.report.technology = [];
		$scope.report.education = [];
		$scope.report.position = [];
		$scope.report.database = [];
		$scope.report.language = [];
		$scope.report.jobRole = [];
		for (i=0; i<$scope.report.searchTagDictionary['Technologie'].length; i++){
			$scope.report.technology.push($scope.report.searchTagDictionary['Technologie'][i].nameTagCloud);
		}
		for (i=0; i<$scope.report.searchTagDictionary['Education'].length; i++){
			$scope.report.education.push($scope.report.searchTagDictionary['Education'][i].nameTagCloud);
		}
		for (i=0; i<$scope.report.searchTagDictionary['Position'].length; i++){
			$scope.report.position.push($scope.report.searchTagDictionary['Position'][i].nameTagCloud);
		}
		for (i=0; i<$scope.report.searchTagDictionary['Database'].length; i++){
			$scope.report.database.push($scope.report.searchTagDictionary['Database'][i].nameTagCloud);
		}
		for (i=0; i<$scope.report.searchTagDictionary['ForeignLanguage'].length; i++){
			$scope.report.language.push($scope.report.searchTagDictionary['ForeignLanguage'][i].nameTagCloud);
		}
		for (i=0; i<$scope.report.searchTagDictionary['JobRole'].length; i++){
			$scope.report.jobRole.push($scope.report.searchTagDictionary['JobRole'][i].nameTagCloud);
		}
		var temp = $scope.report.searchTagDictionary;
		delete $scope.report.searchTagDictionary;
		$http.post('/report',$scope.report).success(function(data){
			$scope.reportResult = data;
			$scope.report.searchTagDictionary = temp;
		})
	}
	
	//Clears the form and search results in the report tab
	$scope.clearForm = function(){
		$scope.reportResult = {};
		$scope.report = {};
		$scope.initSearchTagDictionary();
	}
	
	$scope.initSearchTagDictionary = function(){
		$scope.report.searchTagDictionary = {};
		// Technologie
		$scope.report.searchTagDictionary['Technologie'] = [];
		// POSITION
		$scope.report.searchTagDictionary['Position'] = [];
		// JobRole
		$scope.report.searchTagDictionary['JobRole'] = [];
		// Database
		$scope.report.searchTagDictionary['Database'] = [];
		// IDE
		$scope.report.searchTagDictionary['IDE'] = [];
		// Industry
		$scope.report.searchTagDictionary['Industry'] = [];				
		// Platform
		$scope.report.searchTagDictionary['Platform'] = [];
		// OS,
		$scope.report.searchTagDictionary['OS'] = [];
		// Education
		$scope.report.searchTagDictionary['Education'] = [];
		// ForeignLanguage
		$scope.report.searchTagDictionary['ForeignLanguage'] = [];
	}
	
	
	// TAGCLOUD: EVERYTHING FOR CHIPS AND AUTOCOMPLETE
	$scope.tagClouds = [];
	if($scope.role=="HRM"){	
		(loadAllTags = function(tagUrl){
			tagCloudService.list(tagUrl).success(function(data){
				
				$scope.tagClouds =$scope.tagClouds.concat( data._embedded.tagClouds);
				if(data._links.hasOwnProperty('next') ){
					loadAllTags(data._links.next.href);
				}
				// $scope.tagC = $scope.tagClouds;
			})
		})('/tagClouds');
	}
		// $scope.tagC = $scope.tagClouds;
		// $scope.tagClouds = $scope.tagC;
	 	var self = this;
	 	
	 	$scope.readonly = false;
	    $scope.selectedItem = null;
	    $scope.searchText = null;
	    $scope.querySearch = querySearch;
	    self.vegetables = loadTags();
	    $scope.numberChips = [];
	    $scope.numberChips2 = [];
	    $scope.numberBuffer = '';

	    /**
		 * Search for TAGCLOUDS
		 */
	    $scope.loadedAll = false;
	    function querySearch (query, tipQuery) {
	    	$scope.selectedValue = $scope.report.searchTagDictionary[tipQuery];
	    	if(!$scope.loadedAll){
	    		loadTags();
	    		$scope.loadedAll=true;
	    	}
	      var results = query ? $scope.tagClouds.filter(createFilterFor(query, tipQuery)) : [];
	      return results;
	    }

	    /**
		 * Create filter function for a query string
		 */
	    function createFilterFor(query, tipQuery) {
	      var lowercaseQuery = angular.lowercase(query);
	      var lowercaseTipQuery = angular.lowercase(tipQuery);
	      
	      return function filterFn(tag) {
	    	
	        return ((tag._lowername.indexOf(lowercaseQuery) === 0) ||
	        (tag._lowertype.indexOf(lowercaseQuery) === 0) )&&
	            (tag._lowertype.indexOf(lowercaseTipQuery) === 0) ;
	      };

	    }

	    function loadTags() {
			return $scope.tagClouds.map(function (tc) {
		        tc._lowername = tc.nameTagCloud.toLowerCase();
		        tc._lowertype = tc.tipTagCloud.toLowerCase();
		        
		        return tc;
			})
	    }
	
	$scope.checkDuplicate = function(name, type){
		$scope.nonUnique = false;
		angular.forEach($scope.tagDictionary[type], function(value, key){
			if(value.nameTagCloud == name){
				$scope.nonUnique = true;
			}
			
			
		});
		return $scope.nonUnique;
	}
});
