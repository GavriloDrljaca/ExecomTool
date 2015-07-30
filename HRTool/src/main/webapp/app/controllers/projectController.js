/**
 * 
 */
app.controller('projectController', function($scope, $window, $mdDialog, selectedProject, projectInfoFactory, projectFactory){
		
	$scope.selectedProject = selectedProject;	
	
	$scope.init = function() {
		projectInfoFactory.getEmployeeForProject(selectedProject.idProject).success(function (data) {
			$scope.employees = data;
		});
	}
	
	$scope.answer = function(answer) {
		$mdDialog.hide(answer);
	}
});