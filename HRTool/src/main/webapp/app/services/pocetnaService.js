
app.factory('pocetnaFactory', function($http){
	
	var factory = {};
	
	factory.getEmployees = function(){
		return $http.get('/employees');
	};
	
	return factory;
});
