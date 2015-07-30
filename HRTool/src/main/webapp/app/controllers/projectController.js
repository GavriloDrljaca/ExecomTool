/**
 * 
 */
app.controller('projectController', function($scope, $window, $mdDialog, selectedProject, projectInfoFactory){
		
	$scope.selectedProject = selectedProject;	
	
	function init() {
		projectInfoFactory.getEmployeeForProject(selectedProject.idProject).success(function (data) {
			$scope.employees = data;
		})
	}
	
	init();
	
	$scope.answer = function(answer) {
		$mdDialog.hide(answer);
	}
});