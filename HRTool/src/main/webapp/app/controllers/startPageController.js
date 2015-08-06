app.controller('startPageController', function($http, $scope, $window, $mdDialog, startPageFactory, employeeService, projectService, tagCloudService) {
	
	$scope.init = function() {
		$scope.initSearchTagDictionary();
		employeeService.list().success(function(data) {
			$scope.employees = data._embedded.employees;
		})

		projectService.list().success(function(data) {
			$scope.projects = data._embedded.projects;
		})
		$scope.newEmployee = {};
		$scope.newProject = {};
	}
	
	
	$scope.submit = function(){
		employeeService.create($scope.currEmp).success(function(data){
			
		})
	}

	$scope.deleteProject = function(project){
		projectService.delete(project).success(function(data){
		
		})
	}
	
	$scope.deleteEmployee = function(emp){
		employeeService.delete(emp).success(function(data){

		})
	}
	
	$scope.showEmployee = function(event, employee) {
		$scope.selectedEmployee = employee;
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
			employeeService.list().success(function(data){
				$scope.employees = data._embedded.employees;
				$scope.newEmployee ={};
			})
		}, function() {
			employeeService.list().success(function(data){
				$scope.employees = data._embedded.employees;
				$scope.newEmployee ={};
			})
		});
	};

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
				$scope.projects = data._embedded.projects;
				$scope.newProject ={};
			})
		}, function() {
			projectService.list().success(function(data){
				$scope.projects = data._embedded.projects;
				$scope.newProject ={};
			})
		});
	};
	
	$scope.initSearchTagDictionary = function(){
		$scope.report = {};
		$scope.report.searchTagDictionary = {};
		// Technologie
		$scope.report.searchTagDictionary['Technologie'] = [];
		// POSITION
		$scope.report.searchTagDictionary['Position'] = [];
		// JobRole
		$scope.report.searchTagDictionary['JobRoles'] = [];
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
	(loadAllTags = function(tagUrl){
		tagCloudService.list(tagUrl).success(function(data){
			
			
			$scope.tagClouds =$scope.tagClouds.concat( data._embedded.tagClouds);
			if(data._links.hasOwnProperty('next') ){
				loadAllTags(data._links.next.href);
			}
			
			// $scope.tagC = $scope.tagClouds;
			
		})
	})('/tagClouds');
	
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
