/**
 * 
 */
app.service('projectInfoService', function($http) {
	
	return{	
		getEmployeesForProject: function(idProject) {
			return $http.get('/projectinfo/projectemployee', [idProject])
		}
	}	
});