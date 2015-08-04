app.controller('employeeController', function($http, $rootScope, $scope, $window, $mdDialog, selectedEmployee, $filter, employeeService, tagCloudService ) {

			if (angular.equals(selectedEmployee, {})){
				$scope.newEmployee = true;
				$scope.firstTimeCliced = true;
			}else{
				$scope.newEmployee = false;
			}

			$scope.init = function(){
				if (angular.equals(selectedEmployee, {})){
					$scope.newEmployee = true;
					$scope.firstTimeClicked = true;
					//TAGCLOUDS ARE EMPTY!!
					$scope.initEmptyTagClouds();
				}else{
					$scope.newEmployee = false;
					$scope.firstTimeClicked = false;
					//FILL THE TAGCLOUDS
					$scope.fillTagClouds();
					
				}
				$scope.infoToShow = {};
				$scope.getProjects();
				
				
			}
			
			
			$scope.getProjects = function(){
				$scope.projects = [];
				$scope.projInfos = {};
				if (selectedEmployee._links!=undefined)	
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
				if ($scope.firstTimeClicked == false)
					$scope.firstTimeClicked = true;
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
				
				employeeService.create($scope.currEmp).success(function(data) {
					$scope.currEmp = data;
					$scope.saveTags();
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
				if($scope.index != undefined){
					$scope.projInfos[$scope.index].jobResponsibilities = $scope.infoToShow.jobResponsibilities;
					$scope.projInfos[$scope.index].projectExp = $scope.infoToShow.projectExperiance;
				}
				
				$scope.saveTags();
				employeeService.update($scope.currEmp).success(function(data){
					for (i = 0; i<$scope.projInfos.length; i++){
						$http.put($scope.projInfos[i]._links.self.href, $scope.projInfos[i]).success(function(data){
							
						})
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
			//TAGCLOUD: FILL tagCLOUDS
			$scope.fillTagClouds = function(){
				console.log(selectedEmployee);
				employeeService.getEmployeeTagClouds(selectedEmployee).success(function(data){
					console.log(data);
					
					if(!data.hasOwnProperty('_embedded')){
						$scope.initEmptyTagClouds();
						
						
					}else{
					$scope.employeeTagClouds = data._embedded.tagClouds;
					//TAGS BY TYPE
					//<md chips ng-model = ""
					$scope.tagDictionary = {};
					//Technologie
					$scope.tagDictionary['Technologie'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Technologie"} );
					//POSITION
					$scope.tagDictionary['Position'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Position"} );
					//JobRole
					$scope.tagDictionary['JobRoles'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"JobRole"} );
					//Database
					$scope.tagDictionary['Database'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Database"} );
					//IDE
					$scope.tagDictionary['IDE'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"IDE"} );
					//Industry
					$scope.tagDictionary['Industry'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Industry"} );				
					//Platform
					$scope.tagDictionary['Platform'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Platform"} )
					//OS,
					$scope.tagDictionary['OS'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"OS"} )
					//Education
					$scope.tagDictionary['Education'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Education"} )
					//ForeignLanguage
					$scope.tagDictionary['ForeignLanguage'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"ForeignLanguage"} )
					}
				})
			}
			//TAGCLOUD: init if tagClouds are empty
			$scope.initEmptyTagClouds = function(){

				$scope.employeeTagClouds =[];
				//TAGS BY TYPE
				//<md chips ng-model = ""
				$scope.tagDictionary = {};
				//Technologie
				$scope.tagDictionary['Technologie'] = [];
				//POSITION
				$scope.tagDictionary['Position'] = [];
				//JobRole
				$scope.tagDictionary['JobRoles'] = [];
				//Database
				$scope.tagDictionary['Database'] = [];
				//IDE
				$scope.tagDictionary['IDE'] = [];
				//Industry
				$scope.tagDictionary['Industry'] = [];				
				//Platform
				$scope.tagDictionary['Platform'] = [];
				//OS,
				$scope.tagDictionary['OS'] = [];
				//Education
				$scope.tagDictionary['Education'] = [];
				//ForeignLanguage
				$scope.tagDictionary['ForeignLanguage'] = [];
			}
			
			//TAGCLOUD: EVERYTHING FOR CHIPS AND AUTOCOMPLETE
			$scope.tagClouds = [];
			(loadAllTags = function(tagUrl){
				tagCloudService.list(tagUrl).success(function(data){
					
					
					$scope.tagClouds =$scope.tagClouds.concat( data._embedded.tagClouds);
					if(data._links.hasOwnProperty('next') ){
						loadAllTags(data._links.next.href);
					}
					
					//$scope.tagC = $scope.tagClouds;
					
				})
			})('/tagClouds');
			
				//$scope.tagC = $scope.tagClouds;
				//$scope.tagClouds = $scope.tagC;
			 	var self = this;
			 	
			 	$scope.readonly = false;
			    $scope.selectedItem = null;
			    $scope.searchText = null;
			    $scope.querySearch = querySearch;
			    self.vegetables = loadTags();
			    $scope.numberChips = [];
			    $scope.numberChips2 = [];
			    $scope.numberBuffer = '';
		
			    /**
			     * Search for TAGCLOUDS
			     */
			    $scope.loadedAll = false;
			    function querySearch (query, tipQuery) {
			    	$scope.selectedValue = $scope.tagDictionary[tipQuery];
			    	if(!$scope.loadedAll){
			    		loadTags();
			    		$scope.loadedAll=true;
			    	}
			      var results = query ? $scope.tagClouds.filter(createFilterFor(query, tipQuery)) : [];
			      return results;
			    }
		
			    /**
			     * Create filter function for a query string
			     */
			    function createFilterFor(query, tipQuery) {
			      var lowercaseQuery = angular.lowercase(query);
			      var lowercaseTipQuery = angular.lowercase(tipQuery);
			      
			      return function filterFn(tag) {
			    	
			        return ((tag._lowername.indexOf(lowercaseQuery) === 0) ||
			        (tag._lowertype.indexOf(lowercaseQuery) === 0) )&&
			            (tag._lowertype.indexOf(lowercaseTipQuery) === 0) ;
			      };
		
			    }
		
			    function loadTags() {
					return $scope.tagClouds.map(function (tc) {

				        tc._lowername = tc.nameTagCloud.toLowerCase();
				        tc._lowertype = tc.tipTagCloud.toLowerCase();
				        
				        return tc;
				      
					})
			    }

			$scope.saveTags = function(){
				$scope.req = "";
				$scope.newTags = [];
				$scope.newTags = $scope.newTags.concat($scope.tagDictionary['Technologie'],$scope.tagDictionary['Position'],
						$scope.tagDictionary['JobRoles'], $scope.tagDictionary['Database'],
						$scope.tagDictionary['IDE'],$scope.tagDictionary['Industry'], $scope.tagDictionary['Platform'],
						$scope.tagDictionary['OS'] , 
						$scope.tagDictionary['Education'],
						$scope.tagDictionary['ForeignLanguage']);
				
				for(i =0; i<$scope.newTags.length; i++){
					$scope.req+=$scope.newTags[i]._links.self.href +"\n";
				}
				tagCloudService.saveTag($scope.currEmp._links.tagClouds.href,$scope.req)
			}
			
			$scope.newTag = {};
			$scope.addNewTagCloud= function(newName, type){
				$scope.newTag.nameTagCloud = newName;
				$scope.newTag.tipTagCloud = type;
				/*if($scope.checkDuplicate(newName, type)){
					return;
				}*/
				tagCloudService.create($scope.newTag).success(function(data){
					$scope.tagClouds.push(data);
					//$scope.tagC.push(data);
					$scope.loadedAll = false;
					loadTags();
					//PUSH TO APPROPRIATE ARRAY 
					$scope.tagDictionary[type].push(data);
				});
			}
			
			
			
			$scope.checkDuplicate = function(name, type){
				$scope.nonUnique = false;
				angular.forEach($scope.tagDictionary[type], function(value, key){
					if(value.nameTagCloud == name){
						$scope.nonUnique = true;
					}
					
					
				});
				
				return $scope.nonUnique;
			}

		});