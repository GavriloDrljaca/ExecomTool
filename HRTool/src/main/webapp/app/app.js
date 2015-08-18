/*
 * app
 * created by Gavrilo Drljaca
 */

var app = angular.module('app', ['ngRoute', 'ngMaterial', 'ng', 'flow']);

app.config(['$routeProvider', function ($routeProvider) {
	$routeProvider
		.when('/tagCloudTab', {                                               
	        controller:'tagCloudController',               
	        templateUrl: 'app/partials/tagCloudsTab.html',
	    })
	    .when('/', {
	    	controller: 'app/controllers/LoginController',
	    	templateUrl: 'app/partials/login.html',
	    })
	    .otherwise({                      
	        redirectTo: '/'
	    });      
}]);

app.filter("jsDate", function () {
    return function (x) {
       // return new Date(parseInt(x.toString().substring(6)));
    	return new Date(parseInt(x));
    };
});