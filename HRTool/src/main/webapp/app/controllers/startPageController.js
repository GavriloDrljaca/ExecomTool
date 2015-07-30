app.controller('startPageController', function($scope, $window, $mdDialog, startPageFactory, employeeService, projectService) {

	$scope.init = function() {
		employeeService.list().success(function(data) {
			$scope.employees = data;
		})

		projectService.list().success(function(data) {
			$scope.projects = data._embedded.projects;
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
	
	$scope.showEmployee = function(ev, emp) {
		$scope.selectedEmployee = emp;
		$mdDialog.show({
			controller : 'employeeController',
			controllerAs : 'empCtrl',
			templateUrl : 'app/partials/employeeDialog.html',
			parent : angular.element(document.body),
			targetEvent : ev,
			locals : {
				selectedEmployee : $scope.selectedEmployee
			}
		}).then(function(answer) {
			$scope.alert = 'You said the information was "' + answer + '".';
		}, function() {
			$scope.alert = 'You cancelled the dialog.';
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
