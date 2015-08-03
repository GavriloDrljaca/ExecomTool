app.service('tagCloudService', function($http){
	return{
		create: function(tagCloud){
			alert(tagCloud.nameTagCloud);
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
			
			/*alert("save2");
			return $http.post('/employees/1/tagClouds', tagClouds);*/
			alert(empUrl);
			alert(tagClouds);
			return $http({
				url: empUrl,
				data: tagClouds,
				method: "PUT",
				headers: { "Content-Type":"text/uri-list" }
				
			});

		}
	}
	
});