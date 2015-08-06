app.service('employmentInfoesService', function($http){
	return{
		create: function(empInfo){
			return $http.post('/employmentInfoes', empInfo);
		},
		update: function(empInfo){
			return $http.put(empInfo._links.self.href, empInfo);
		},
		list: function(empInfoURL){
			return $http.get(empInfoURl);
		},
		delete: function(empInfo){
			return $http.delete(empInfo._links.self.href);
		},
		getEmploymentInfoesTagClouds: function(empInfo){
			return $http.get(empInfo._links.tagClouds.href);
		},
		updateEmploymentInfoesTags : function(empInfo, tagURLs){

			return $http({
				url: empInfo._links.tagClouds.href,
				data: tagURLs,
				method: "PUT",
				headers: {"Content-Type": "text/uri-list"}
			});
		},
		saveEmployee: function(employmentInfo, employee){
			return $http({
				url:employmentInfo._links.employee.href,
				data: employee._links.self.href,
				method: "PUT",
				headers: {"Content-Type": "text/uri-list"}
			});
		}
		
	}
});