/**
 * 
 */
app.service('projectInfoService', function($http) {
	var url = '/projectInfoes';
	return{
		create: function(projectInfo){
			return $http.post(url, projectInfo)
		},
		update: function(projectInfo){
			return $http.put(projectInfo._links.self.href, projectInfo)
		},
		list: function(){
			return $http.get(url);
		},
		delete: function(projectInfo){
			return $http.delete(projectInfo._links.self.href)
		},
		getOne: function(projectInfo){
			return $http.get(projectInfo._links.employee.href);
		},
		getForProject: function(project){
			return $http.get(project._links.projectInfo.href);
		}
		
	}
});