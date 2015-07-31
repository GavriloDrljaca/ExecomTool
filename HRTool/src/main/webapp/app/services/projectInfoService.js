/**
 * 
 */
app.service('projectInfoService', function($http) {
	url = '/projectInfoes';
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
		}
	}
});