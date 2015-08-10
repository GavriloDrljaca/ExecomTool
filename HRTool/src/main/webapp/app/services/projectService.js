app.service('projectService', function($http) {
	var url = '/projects';
	return {	
		list: function() {
			return $http.get(url);
		},
		save: function(project) {
			return $http.post(url, project);
		},
		delete: function(project) {
			return $http.delete(project._links.self.href);
		},
		update: function(project){
			return $http.put(project._links.self.href, project);
		},
		tagCloudsForProject: function(project) {
			return $http.get(project._links.tagClouds.href);
		},
		saveTagCloudsForProject: function(projectUrl, tagClouds) {
			return $http({
				url: projectUrl,
				data: tagClouds,
				method: "PUT",
				headers: {"Content-Type": "text/uri-list"}
			});
		},
		projectInfosForProject: function(project) {
			return $http.get(project._links.projectInfo.href)
		}
	}
});