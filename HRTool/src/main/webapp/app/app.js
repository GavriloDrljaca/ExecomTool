/*
 * app
 * created by Gavrilo Drljaca
 */

var app = angular.module('app', ['ngRoute', 'ngMaterial']);

app.filter("jsDate", function () {
    return function (x) {
       // return new Date(parseInt(x.toString().substring(6)));
    	return new Date(parseInt(x));
    };
});