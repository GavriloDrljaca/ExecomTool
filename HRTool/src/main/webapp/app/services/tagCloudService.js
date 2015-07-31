app.service('tagCloudService', function($http){
	return{
		create: function(tagCloud){
			return $http.post('/tagClouds', tagCloud)
		},
		update: function(tagCloud){
			return $http.put(tagCloud._links.self.href, employee)
		},
		list: function(){
			return $http.get('/tagClouds');
		},
		delete: function(tagCloud){
			return $http.delete(emp._links.self.href)
		}
	}
	
});