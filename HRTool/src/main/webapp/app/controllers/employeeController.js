/**
 * Employee controller created by: Gavrilo Drljaca
 */

app.controller('employeeController',
		function($rootScope, $scope, $window, $mdDialog, selectedEmployee, $filter, employeeService) {

			if (angular.equals(selectedEmployee, {})){
				$scope.newEmployee = true;
			}else{
				$scope.newEmployee = false;
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
				
				employeeService.update($scope.currEmp).success(function() {
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