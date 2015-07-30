app.service('projectService', function($http) {
	return {	
		list: function() {
			return $http.get('/projects');
		},
		save: function(project) {
			return $http.post('/projects', project);
		},
		delete: function(project) {
			return $http.delete(project._links.self.href);
		}
	}
});