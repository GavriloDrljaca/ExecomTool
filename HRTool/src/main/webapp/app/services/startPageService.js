
app.factory('startPageFactory', function($http){
	
	var factory = {};
	
	factory.getEmployees = function(){
		return $http.get('/employees');
	};
	
	factory.getProjects = function(){
		return $http.get('/projects/getAll');
	};
	
	return factory;
});
