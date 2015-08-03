app.service('employeeService', function($http){
	url = '/employees';
	return{
		testGet: function(employee){
			return $http.get('/employees/testEmployeeGet');
		},
		test: function(employee){
			return $http.post('/employees/testEmployee', employee);
		},
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
		},
		getById: function(id){
			return $http.get(url+"/"+id)
		},
		getEmployeeTagClouds: function(employee){
			return $http.get(employee._links.tagClouds.href);
		}
	}
});