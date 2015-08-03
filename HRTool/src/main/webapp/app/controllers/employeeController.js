app.controller('employeeController',
		function($http, $rootScope, $scope, $window, $mdDialog, selectedEmployee, $filter, employeeService) {

			if (angular.equals(selectedEmployee, {})){
				$scope.newEmployee = true;
			}else{
				$scope.newEmployee = false;
			}

			$scope.init = function(){
				$scope.infoToShow = {};
				$scope.getProjects();
			}
			
			$scope.getProjects = function(){
				$scope.projects = [];
				$scope.projInfos = {};
				$http.get(selectedEmployee._links.projectInfos.href).success(function (data) {
					if(data._embedded != undefined) {
						$scope.projInfos = data._embedded.projectInfoes;
					} else {
						$scope.projInfos = {};
					}
					for(i = 0; i<$scope.projInfos.length; i++) {
						$http.get($scope.projInfos[i]._links.project.href).success(function (data) {
							$scope.projects.push(data);
						});
					}
				});
			}
			
			$scope.showInfo = function(project, index){
				if (!angular.equals($scope.infoToShow.projectExperiance, undefined)){
					$scope.projInfos[$scope.index].jobResponsibilities = $scope.infoToShow.jobResponsibilities;
					$scope.projInfos[$scope.index].projectExp = $scope.infoToShow.projectExperiance;
				}
				$scope.index = index;
				$scope.infoToShow.projectName = project.nameProject;
				$scope.infoToShow.projectDuration = project.durationOfProject;
				$scope.infoToShow.jobResponsibilities = $scope.projInfos[index].jobResponsibilities;
				$scope.infoToShow.projectExperiance = $scope.projInfos[index].projectExp;
			}
			
			$scope.activeForm = "none";
			$scope.currRealDeal = selectedEmployee;

			$scope.currEmp = $scope.currRealDeal;
			// Date of Birth
			$scope.dateBirth = new Date(selectedEmployee.dateOfBirth);

			// startDate
			$scope.startDate = new Date($scope.currEmp.startDate);
			// endDate
			$scope.endDate = new Date($scope.currEmp.endDate);
			// startDate from booklet
			$scope.startDateFromBooklet = new Date(
					$scope.currEmp.startDateFromBooklet);

			$scope.createEmployee = function(){
				if ($scope.currEmp.nameEmployee == "" || angular.equals($scope.currEmp.nameEmployee,undefined)){
					$scope.currEmp.nameEmployee = "No name";
				}
				$scope.currEmp.dateOfBirth = $scope.dateBirth.toJSON();
				$scope.currEmp.startDate = $scope.startDate;
				$scope.currEmp.endDate = $scope.endDate;
				$scope.currEmp.startDateFromBooklet = $scope.startDateFromBooklet;
				
				employeeService.create($scope.currEmp).success(function() {
					$mdDialog.cancel();
				});	
			}
			$scope.saveEmployee = function() {
				if ($scope.currEmp.nameEmployee == ""){
					$scope.currEmp.nameEmployee = "No name";
				}
				// saving (new) DateOfBirth
				$scope.currEmp.dateOfBirth = $scope.dateBirth.toJSON();
				//saving (new) StartDate
				$scope.currEmp.startDate = $scope.startDate;
				//saving (new) endDate
				$scope.currEmp.endDate = $scope.endDate;
				//saving (new) startDateFromBooklet
				$scope.currEmp.startDateFromBooklet = $scope.startDateFromBooklet;
				
				$scope.projInfos[$scope.index].jobResponsibilities = $scope.infoToShow.jobResponsibilities;
				$scope.projInfos[$scope.index].projectExp = $scope.infoToShow.projectExperiance;
				
				employeeService.update($scope.currEmp).success(function(data){
					for (i = 0; i<$scope.projInfos.length; i++){
						$http.put($scope.projInfos[i]._links.self.href, $scope.projInfos[i]).success(function(data){
							
						})
					}
					for (i=0;i<1000;i++){
					}
					$mdDialog.cancel();
					
				});

			}
			
			$scope.deleteEmployee = function(employee){
				employeeService.delete(employee).success(function(data){
					$mdDialog.cancel();
				})
			}
			// CLOSING DIALOG
			$scope.hide = function() {
				$mdDialog.hide();
			};
			$scope.cancel = function() {
				$mdDialog.cancel();
			};
			$scope.answer = function(answer) {
				$mdDialog.hide(answer);
			}

			$scope.showForm = function(forma) {
				$scope.activeForm = forma;
			}
			$scope.showDate = function(bla) {
				alert($scope.dateOfBirth);
				alert(new Date($scope.DateOfBirth))
			}
			/*
			 * function toJSONLocal (date) { var local = new Date(date);
			 * local.setMinutes(date.getMinutes() - date.getTimezoneOffset());
			 * return local.toJSON().slice(0, 10); }
			 */

		});