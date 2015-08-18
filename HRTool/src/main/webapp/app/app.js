/*
 * app
 * created by Gavrilo Drljaca
 */

var app = angular.module('app', ['ngRoute', 'ngMaterial', 'flow']);

app.config(['$routeProvider', function ($routeProvider) {
	$routeProvider
		.when('/tagCloudTab', {                                               
	        controller:'tagCloudController',               
	        templateUrl: 'app/partials/tagCloudsTab.html',
	    })
	    .when('/', {
	    	controller: 'LoginController',
	    	templateUrl: 'app/partials/login.html',
	    })
	    .when('/startPage', {
	    	controller: 'startPageController',
	    	templateUrl: 'app/partials/startPage.html',
	    })
	    .otherwise({                      
	        redirectTo: '/'
	    });      
}]);