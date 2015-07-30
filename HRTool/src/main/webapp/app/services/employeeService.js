/**
 * employeeService
 * created by: Gavrilo Drljaca
 */

app.service('employeeService', function($http){
	return{	
		save: function(emp){
			return $http.post('/employees, emp)
		},
		list: function(){
			return $http.get('/employees');
		},
		delete: function(emp){
			return $http.delete(emp._links.self.href)
		}
	}
});