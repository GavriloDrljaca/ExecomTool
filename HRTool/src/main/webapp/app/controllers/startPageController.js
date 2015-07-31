app.controller('startPageController', function($scope, $window, $mdDialog, startPageFactory, employeeService, projectService) {

	
	$scope.init = function() {
		employeeService.list().success(function(data) {
			$scope.employees = data._embedded.employees;
		})

		projectService.list().success(function(data) {
			$scope.projects = data._embedded.projects;
		})
		
		$scope.newEmployee = {};
	}
	
	
	$scope.submit = function(){
		employeeService.create($scope.currEmp).success(function(data){
			
		})
	}
	
	$scope.updateProject = function(pr){
		pr.nameProject = "Test";
		projectService.update(pr).success(function(data){
			
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
			$scope.alert = 'You said the information was "' + answer + '".';
		}, function() {
			$scope.alert = 'You cancelled the dialog.';
		});
	};
});
