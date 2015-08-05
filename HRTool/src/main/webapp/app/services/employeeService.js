app.service('employeeService', function($http){
	var url = '/employees';
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
		},
		getById: function(id){
			return $http.get(url+"/"+id)
		},
		getEmployeeTagClouds: function(employee){
			return $http.get(employee._links.tagClouds.href);
		},
		getEmploymentInfos : function(employee){
			return $http.get(employee._links.empInfos.href);
		}
	}
});