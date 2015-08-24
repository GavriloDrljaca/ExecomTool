/*
 * app
 * created by Gavrilo Drljaca
 */

var app = angular.module('app', ['ngRoute', 'ngMaterial', 'ng', 'flow']);

app.filter("jsDate", function () {
    return function (x) {
       // return new Date(parseInt(x.toString().substring(6)));
    	return new Date(parseInt(x));
    };
});

app.config(function($routeProvider, $locationProvider){
	$routeProvider
		.when('/tagCloudTab', {                                            
	        templateUrl: "app/partials/tagCloudsTab.html",                                               
	        controller:'tagCloudController',                                
	       })                                                                      
	       .otherwise({                      
	           template: 'does not exists'   
	       });      
});