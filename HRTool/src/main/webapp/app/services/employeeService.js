/**
 * employeeService
 * created by: Gavrilo Drljaca
 */

app.factory('employeeFactory', function($http){
	
	var factory = {};
	

	factory.saveEmployee = function(emp){
		// return $http.post('')
	};
	factory.getEmployees = function(){
		return $http.get('/employees');
	};
});