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
		},
		saveEmploymentInfos: function(employeeURL, employmentInfos){
			return $http({
				url: employeeUrl,
				data: employmentInfos,
				method: "PUT",
				headers: {"Content-Type": "text/uri-list"}
			});
		},

		deleteAllEmpInfos: function (emp){
			return $http({
				url: emp._links.empInfos.href,
				data: " ",
				method: "PUT",
				headers: {"Content-Type": "text/uri-list"}
			});
		},
		deleteAllTags : function(emp){
			return $http({
				url: emp._links.tagClouds.href,
				data: " ",
				method: "PUT",
				headers: {"Content-Type": "text/uri-list"}
			});
		},
		deleteAllProjectInfos: function(emp){
			projIn = [];
			return $http.get(emp._links.projectInfos.href);
			
			/*return $http({
				url: emp._links.projectInfos.href,
				data: " ",
				method: "PUT",
				headers: {"Content-Type": "text/uri-list"}
			});*/
		}
	}
});