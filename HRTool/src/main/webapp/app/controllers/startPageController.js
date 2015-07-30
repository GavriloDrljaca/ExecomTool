app.controller('startPageController', function($scope, $window, $mdDialog,
		startPageFactory, employeeFactory, projectFactory) {

	function init() {
		employeeFactory.getEmployees().success(function(data) {
			$scope.employees = data;
		})

		projectFactory.getAllProjects().success(function(data) {
			$scope.projects = data;
		})
	}

	init();

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
			templateUrl : 'app/partials/projekatDialog.html',
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
