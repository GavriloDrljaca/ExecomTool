/**
 * 
 */
app.factory('projectFactory', function($http){
	
	var factory={};
	
	factory.getAllProjects = function(){
		return $http.get('/projects/getAll');
	};
	
	factory.getProject = function(id){
		return $http.get('/projects/getProject', [id])
	}
	
	return factory;
});