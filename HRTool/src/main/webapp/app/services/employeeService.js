app.service('employeeService', function($http){
	return{
		create: function(employee){
			return $http.post('/employees', employee)
		},
		update: function(employee){
			return $http.put(employee._links.self.href, employee)
		},
		list: function(){
			return $http.get('/employees');
		},
		delete: function(emp){
			return $http.delete(emp._links.self.href)
		}
	}
});