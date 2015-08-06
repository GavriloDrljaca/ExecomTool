app.service('tagCloudService', function($http){
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
			return $http.delete(emp._links.self.href)
		},
		saveTag: function(empUrl, tagClouds){
			return $http({
				url: empUrl,
				data: tagClouds,
				method: "PUT",
				headers: { "Content-Type":"text/uri-list" }
				
			});

		},
		findByTip: function(tiptagcloud) {
			console.log(tiptagcloud);
			return $http({
				url: 'http://localhost:8080/tagClouds/search/findByTipTagCloud',
				data: tiptagcloud,
				method: "GET",
				headers: {"Content-Type":"text/plain"}
			});
		}
	}
	
});