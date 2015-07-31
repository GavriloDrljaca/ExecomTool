app.service('employeeService', function($http){
	url = '/employees';
	return{
		create: function(employee){
			return $http.post(url, employee)
		},
		update: function(employee){
			return $http.put(employee._links.self.href, employee)
		},
		list: function(){
			return $http.get(url);
		},
		delete: function(emp){
			return $http.delete(emp._links.self.href)
		}
	}
});