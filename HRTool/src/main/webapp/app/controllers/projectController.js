/**
 * 
 */
app.controller('projectController', function($scope, $window, $mdDialog, selectedProject){
		
	$scope.selectedProject = selectedProject;	
	
	function init() {
		projectInfoFactory.getEmployeeForProject(selectedProject.idProject).success(data);
		$scope.eployees = data;
	}
	
	$scope.answer = function(answer) {
		$mdDialog.hide(answer);
	}
});