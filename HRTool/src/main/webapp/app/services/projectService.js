/**
 * 
 */
app.factory('projectFactory', function($http) {

	var factory = {};

	factory.getAllProjects = function() {
		return $http.get('/projects/getAll');
	};

	factory.getProject = function(id) {
		return $http.get('/projects/getProject', [ id ])
	};

	factory.saveProject = function(project) {
		return $http.post('/projects/saveProject', [project])
	};
	
	factory.deleteProject = function(id) {
		return $http.post('/projects/deleteProject', [id])
	}

	return factory;
});