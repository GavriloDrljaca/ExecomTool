app.service('tagCloudService', function($http){
	var url = '/tagClouds';
	return{
		create: function(tagCloud){

			return $http.post('/tagClouds', tagCloud);
		},
		update: function(tagCloud){
			return $http.put(tagCloud._links.self.href, employee)
		},
		list: function(tagUrl){
			return $http.get(tagUrl);
		},
		delete: function(tagCloud){
			return $http.delete(tagCloud._links.self.href)
		},
		saveTag: function(empUrl, tagClouds){
			console.log(tagClouds);
			return $http({
				url: empUrl,
				data: tagClouds,
				method: "PUT",
				headers: { "Content-Type":"text/uri-list" }
				
			});

		},
		findByTip: function(type) {
			return $http.get(url + '/search/findByTipTagCloud?tipTagCloud=' + type);
		},
		generate: function(email) {
			return $http.get("/report/cv?email=" + email);
		},
		getForProjectInfo: function(projectInfo) {
			return $http.get(projectInfo._links.tagClouds.href);
		},
		updateEmployees: function(tag, tagClouds){
			return $http({
				url: tag._links.employees.href,
				data:" ",
				method: "PUT",
				headers: { "Content-Type":"text/uri-list" }
				
			});
			
			
			/*var employees = [];
			return $http.get(tag._links.employees.href).success(function(data){
					try{
						
						employees = data._embedded.employees;
					}catch(err){
						employees = [];
					}
					
					console.log(employees);
					angular.forEach(employees, function(emp, key){
						$http({
							url: emp._links.tagClouds.href,
							data: " ",
							method: "PUT",
							headers: { "Content-Type":"text/uri-list" }
							
						});
						
					});
				});*/
			//return $http.delete(tag._links.employees.href);
			/*return $http({
				url: tag._links.employees.href,
				data: tagClouds,
				method: "PUT",
				headers: { "Content-Type":"text/uri-list" }
				
			});*/
		},
		updateEmploymentInfoes: function(tag, tagClouds){
			return $http({
				url: tag._links.employmentInfos.href,
				data: " ",
				method: "PUT",
				headers: { "Content-Type":"text/uri-list" }
				
			});
		},
		updateProjectInfoes: function(tag, tagClouds){
			return $http({
				url: tag._links.projectInfos.href,
				data: " ",
				method: "PUT",
				headers: { "Content-Type":"text/uri-list" }
				
			});
		},
		updateProjects: function(tag, tagClouds){
			return $http({
				url: tag._links.projects.href,
				data:  " ",
				method: "PUT",
				headers: { "Content-Type":"text/uri-list" }
			});
		}
	}
});