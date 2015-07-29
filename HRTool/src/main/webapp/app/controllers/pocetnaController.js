app.controller('pocetnaController', function($rootScope, $scope, $window, logovanjeFactory){
	
	function init(){
			
		logovanjeFactory.getUser().success(function(data){
			if (data.logedIn==true)
				$window.location.href = "#/pocetnaStrana";
			$scope.user = data;
		});
	}
	
	init();
});