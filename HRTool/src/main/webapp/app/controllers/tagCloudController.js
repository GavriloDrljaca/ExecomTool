app.controller('tagCloudController', function($scope, $window, tagCloudService){
	
	$scope.init = function(){
		//alert("OLA");
		tagCloudService.list().success(function(data){
			$scope.tagClouds = data._embedded.tagClouds;

		})
	}
	
});