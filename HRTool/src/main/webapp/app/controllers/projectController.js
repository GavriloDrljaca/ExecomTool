/**
 * 
 */
app.controller('projectController', function($scope, $window, projectFactory){
		
	
	function init(){
		
		projectFactory.getAllProjects().success(function (data) {
			$scope.projects = data;
		});
	}
	
	init();
	
	
});