/**
 * 
 */
app.controller('projectController', function($scope, $window, $mdDialog, selectedProject, projectInfoService){
		
	$scope.selectedProject = selectedProject;	
	
	$scope.init = function() {
		projectInfoService.getEmployeeForProject(selectedProject.idProject).success(function (data) {
			$scope.employees = data;
		});
	}
	
	$scope.answer = function(answer) {
		$mdDialog.hide(answer);
	}
});