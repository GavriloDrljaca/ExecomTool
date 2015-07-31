app.controller('projectController', function($http, $scope, $window, $mdDialog, selectedProject, projectInfoService){
		
	$scope.selectedProject = selectedProject;	
	 
	$scope.init = function() {
		$scope.employees = [];
		getEmployees();	
	}
	
	function getEmployees() {
		
		var projInfos = {};
		$http.get(selectedProject._links.projectInfo.href).success(function (data) {
			if(data._embedded != undefined) {
				projInfos = data._embedded.projectInfoes;
			} else {
				projInfos = {};
			}
			temp =[];
			counter = 0;
			for(i = 0; i<projInfos.length; i++) {
				temp.push(projInfos[i]);
				$http.get(projInfos[i]._links.employee.href).success(function (data) {
					$scope.employees.push(data);
					test(data, temp[counter]);
					counter++;
				});
			}
		});
	}
	function test(employee, projInfo){
		console.log(projInfo);
		$http.get(employee._links.projectInfos.href).success(function (data) {
			employee.resp = projInfo.jobResponsibilities;
			employee.prExp = projInfo.projectExp;
		});
	}
	
	$scope.answer = function(answer) {
		$mdDialog.hide(answer);
	}
});