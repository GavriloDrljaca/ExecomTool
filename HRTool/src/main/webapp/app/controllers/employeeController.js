/**
 * Employee controller created by: Gavrilo Drljaca
 */

app.controller('employeeController',
		function($rootScope, $scope, $window, $mdDialog, selectedEmployee, $filter, employeeService) {
			function init() {

				$scope.activeForm = "none";
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

			$scope.saveEmployee = function() {

				// saving (new) DateOfBirth
				$scope.currEmp.dateOfBirth = $scope.dateBirth.toJSON();
				//

				employeeService.save($scope.currEmp).success(function() {
					alert("USPEH!");
				});

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