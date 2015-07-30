/**
 * employeeService
 * created by: Gavrilo Drljaca
 */

app.factory('employeeFactory', function($http){
	
	var factory = {};
	

	factory.saveEmployee = function(emp){
		
		return $http.post('/employees/saveemployee/', {"employee": emp})
	};
	factory.getEmployees = function(){
		return $http.get('/employees');
	};
	
	factory.deleteEmployee = function(id){
		return $http.post('/employees/deleteemployee', [id])
	}
	
	return factory;
});