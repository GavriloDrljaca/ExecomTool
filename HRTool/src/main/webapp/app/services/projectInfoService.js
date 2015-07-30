/**
 * 
 */
app.factory('projectInfoFactory', function($http) {
	
	var factory = {};
	
	factory.getEmployeeForProject = function(idProject) {
		return $http.get('/projectinfo/projectemployee', [idProject])
	};
	
	return factory;
});