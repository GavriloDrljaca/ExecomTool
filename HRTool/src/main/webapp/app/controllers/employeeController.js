app.controller('employeeController', function($http, $rootScope, $scope, $window, $mdDialog, selectedEmployee, $filter, employeeService, tagCloudService, employmentInfoesService, projectInfoService, projectService ) {


			$scope.init = function(){
				$scope.newProjectInfoEntry = false;
				$scope.newProjectOrNot = {};
				
				$scope.newProjectOrNot.bool = "";
				if (angular.equals(selectedEmployee, {})){
					$scope.newEmployee = true;
					$scope.firstTimeClicked = true;
					// TAGCLOUDS ARE EMPTY!!
					$scope.initEmptyTagClouds();
					// INIT EMPLOYMENT INFOES
					$scope.employmentInfos = [];
				}else{
					$scope.newEmployee = false;
					$scope.firstTimeClicked = false;
					// FILL THE TAGCLOUDS
					$scope.fillTagClouds();
					// LOAD EMPLOYMENT INFOES
					$scope.loadEmploymentInfo(selectedEmployee);
					
				}
				$scope.infoToShow = {};
				$scope.getProjects();
			}

			// START OF EMPLOYMENT INFO
			// EMPLOYEMENT INFOES LOADER
			$scope.loadEmploymentInfo = function(employee){
				employeeService.getEmploymentInfos(employee).success(function(data){
					if(data.hasOwnProperty('_embedded')){
						if(data._embedded.hasOwnProperty('employmentInfoes')){
							$scope.employmentInfos = data._embedded.employmentInfoes;
						}else{
							$scope.employmentInfos = [];
						}
					}else{
						$scope.employmentInfos = [];
					}
					// EXTRACT tagClouds
					$scope.EItagClouds = {};
					$scope.extractEmploymentInfosTags();
					// EXTRACT DATES
					$scope.dateDictionary = {};
					
					$scope.extractEmploymentDates();
				});
			}
			// EXTRACT EMPLOYMENT INFOES TAG CLOUDS
			$scope.extractEmploymentInfosTags = function(){
				$scope.EItagClouds = {};
				angular.forEach($scope.employmentInfos, function(empInfo, key){
					console.log(empInfo.companyName);
					
					employmentInfoesService.getEmploymentInfoesTagClouds(empInfo).success(function(data){
						$scope.EItagClouds[empInfo.companyName] = {};
						if(data.hasOwnProperty('_embedded')){
							$scope.EItagClouds[empInfo.companyName].tagClouds = data._embedded.tagClouds;
							// console.log(data);
						}else{
							$scope.EItagClouds[empInfo.companyName].tagClouds = [];
						}
					})
					
				});
			}
			// UPDATE EMPLOYMENT INFOES TAG CLOUDS (POSITION)
			$scope.updateEmploymentInfoesTagClouds = function(){
				$scope.empInfoesURLs ="";
				
				angular.forEach($scope.employmentInfos, function(empInfo, key){
					angular.forEach($scope.EItagClouds[empInfo.companyName].tagClouds, function(tagCloud, key){
						$scope.empInfoesURLs = $scope.empInfoesURLs + tagCloud._links.self.href + "\n";
						
					})
					
					employmentInfoesService.updateEmploymentInfoesTags(empInfo,$scope.empInfoesURLs).success(function(data){
						console.log(data);
					})
					$scope.empInfoesURLs = "";
				});
			}
			// EXTRACTING EMPLOYEMENT DATES
			$scope.extractEmploymentDates = function(){
				$scope.dateDictionary = {};
				// $scope.startDate = new Date($scope.currEmp.startDate);
				$scope.totalWorkExperience = 0;
				angular.forEach($scope.employmentInfos, function(empInfo, key){
					$scope.dateDictionary[empInfo.companyName] = {};
					$scope.dateDictionary[empInfo.companyName].startDate = 
						new Date(empInfo.startDate)
					if(empInfo.endDate != null){
						$scope.dateDictionary[empInfo.companyName].endDate = 
							new Date(empInfo.endDate);
						// Calculate date diference
						$scope.dateDictionary[empInfo.companyName].workExperience = 
							($scope.dateDictionary[empInfo.companyName].endDate.getMonth() -
							$scope.dateDictionary[empInfo.companyName].startDate.getMonth()) + 
							(12* (($scope.dateDictionary[empInfo.companyName].endDate.getFullYear() -
							$scope.dateDictionary[empInfo.companyName].startDate.getFullYear())));
						
					}else{
						$scope.dateDictionary[empInfo.companyName].endDate = null;
						// Calculate date diference
						today = new Date();
						$scope.dateDictionary[empInfo.companyName].workExperience = 
							(today.getMonth() - $scope.dateDictionary[empInfo.companyName].startDate.getMonth()) + 
							(12*(today.getFullYear() -$scope.dateDictionary[empInfo.companyName].startDate.getFullYear()));
					}
					$scope.totalWorkExperience = $scope.totalWorkExperience + $scope.dateDictionary[empInfo.companyName].workExperience; 
				});
				if(Math.floor($scope.totalWorkExperience/12) == 1){
					year = "year"
				}else{
					year = "years"
				}
				
				
				$scope.totalExperienceString = Math.floor($scope.totalWorkExperience/12) +" " +year+" (months: " + $scope.totalWorkExperience + ")"; 
				
			}
			
			$scope.saveEmploymentDates = function(){
				angular.forEach($scope.employmentInfos, function(empInfo, key){
						empInfo.startDate = $scope.dateDictionary[empInfo.companyName].startDate;
						empInfo.endDate = $scope.dateDictionary[empInfo.companyName].endDate; 
				
				});
			}
			$scope.saveEmploymentInfos = function(){
				$scope.saveEmploymentDates();
				
				angular.forEach($scope.employmentInfos, function(empInf){
					employmentInfoesService.update(empInf);
				});
				
				// SAVE NEW EMPLOYMENT INFOES -> like tag clouds
				/*
				 * employeeService.saveEmploymentInfos($curr.emp).success(function(){
				 * 
				 * });
				 */
			}
			// ADD NEW EMPLOYMENT HISTORY
			$scope.newEmpInfo = {};
			$scope.addNewEmploymentHistory = function(){
				$scope.newEmpInfo.companyName = "";
				$scope.newEmpInfo.startDate = null;
				$scope.newEmpInfo.endDate = null;
				$scope.newEmpInfo.tagClouds = [];
				$scope.addNewEmpInfo = "true";
			}
			// SAVE NEW EMPLOYMENT HISTORY
			$scope.saveNewEmploymentHistory = function(){
				employmentInfoesService.create($scope.newEmpInfo).success(function(data){
					$scope.employmentInfos.push(data);
					$scope.extractEmploymentDates();
					$scope.extractEmploymentInfosTags();
					console.log(data);
					// ATTACH IT TO EMPLOYEEE
					// IT WILL BE ATTACH IN FUNCTION saveEItoEMployee
					/*
					 * employmentInfoesService.saveEmployee(data,
					 * $scope.currEmp).success(function(){ alert("saved!"); })
					 */
				});
				$scope.addNewEmpInfo = "false";
			}
			$scope.saveEIsToEmployee = function(){
				angular.forEach($scope.employmentInfos, function(ei, key){
					employmentInfoesService.saveEmployee(ei, $scope.currEmp);
					
				});
			}
			// ADDING NEW TAG TO EMPLOYMENT INFO
			$scope.newTag = {};
			$scope.addNewTagCloudEI= function(companyName, newName, type){
				$scope.newTag.nameTagCloud = newName;
				$scope.newTag.tipTagCloud = type;
				/*
				 * if($scope.checkDuplicate(newName, type)){ return; }
				 */
				tagCloudService.create($scope.newTag).success(function(data){
					$scope.tagClouds.push(data);
					// $scope.tagC.push(data);
					$scope.loadedAll = false;
					loadTags();
					// PUSH TO APPROPRIATE ARRAY
					$scope.EItagClouds[companyName].tagClouds.push(data);
				});
			}
			// END OF EMPLOYMENT INFO
			
			// Da spreci automatsko sortiranje ng-repeata
			$scope.objectKeys = function(obj){
				  return Object.keys(obj);
			}
			// PROJECT INFOS : WORK EXPERIENCE TAB
			$scope.getProjects = function(){
				$scope.projects = [];
				$scope.projectInfosTagClouds = [];
				$scope.projInfos = {};
				if (selectedEmployee._links!=undefined)	
					$http.get(selectedEmployee._links.projectInfos.href).success(function (data) {
						if(data._embedded != undefined) {
							$scope.projInfos = data._embedded.projectInfoes;
						} else {
							$scope.projInfos = {};
						}
						for(i = 0; i<$scope.projInfos.length; i++) {
							$http.get($scope.projInfos[i]._links.project.href).success((function (i) {
								return function (data) {
							
								$scope.projects[i] =  data;
								console.log("Projekat" +i);
								console.log("Projekat");
								console.log(data);
								}
								
							})(i));
							
							$http.get($scope.projInfos[i]._links.tagClouds.href).success( (function (i) {
									
								return function(data){
								if(data._embedded != undefined){
									if(data._embedded.hasOwnProperty('tagClouds')){
										$scope.projectInfosTagClouds[i] = (data._embedded.tagClouds);
										console.log(i);
										console.log(data._embedded.tagClouds);
									}else{
										$scope.projectInfosTagClouds.push([]);
									}
								}else{
									$scope.projectInfosTagClouds.push([]);
								}
								}
								})(i));
						}
					});
				
				$scope.extractProjectInfosTagClouds();
			}
			
			// Sluzi za prikazivanje projectInfo-a u employeeWorkExperiance
			// tab-u
			$scope.showInfo = function(project, index){
				if ($scope.firstTimeClicked == false){
					$scope.extractProjectInfosTagClouds();
					$scope.firstTimeClicked = true;
				}
				$scope.newProjectInfoEntry = false;
				if (!angular.equals($scope.infoToShow.projectExperiance, undefined)){
					$scope.projInfos[$scope.index].jobResponsibilities = $scope.infoToShow.jobResponsibilities;
					$scope.projInfos[$scope.index].projectExp = $scope.infoToShow.projectExperiance;
					$scope.projInfos[$scope.index].seniority = $scope.infoToShow.seniority;
					$scope.projInfos[$scope.index].durationOnProject = $scope.infoToShow.durationOnProject;
					try{
						console.log($scope.projects[$scope.index]);
						$scope.projectInfosTagClouds[$scope.index].technologieTags = $scope.infoToShow.technologieTags;
						console.log($scope.projectInfosTagClouds[$scope.index].technologieTags);
						$scope.projectInfosTagClouds[$scope.index].databaseTags = $scope.infoToShow.databaseTags;
						$scope.projectInfosTagClouds[$scope.index].ideTags = $scope.infoToShow.ideTags;
						$scope.projectInfosTagClouds[$scope.index].jobRoleTags = $scope.infoToShow.jobRoleTags;
					}catch(err){
					
					}
				}
				$scope.index = index;
				$scope.infoToShow.projectName = project.nameProject;
				$scope.infoToShow.projectDuration = project.durationOfProject;
				$scope.infoToShow.jobResponsibilities = $scope.projInfos[index].jobResponsibilities;
				$scope.infoToShow.projectExperiance = $scope.projInfos[index].projectExp;
				$scope.infoToShow.seniority = $scope.projInfos[index].seniority;
				$scope.infoToShow.durationOnProject = $scope.projInfos[index].durationOnProject;
				try{
					$scope.infoToShow.technologieTags = $scope.projectInfosTagClouds[index].technologieTags;
					$scope.infoToShow.databaseTags = $scope.projectInfosTagClouds[index].databaseTags;
					$scope.infoToShow.ideTags = $scope.projectInfosTagClouds[index].ideTags;
					$scope.infoToShow.jobRoleTags = $scope.projectInfosTagClouds[index].jobRoleTags;
				}catch(err){
					$scope.infoToShow.technologieTags = [];
					$scope.infoToShow.databaseTags = [];
					$scope.infoToShow.ideTags = [];
					$scope.infoToShow.jobRoleTags = [];
				
				}
			}
			
			// EXTRACT PROJECT INFOES TAG CLOUDS, TECHNOLOGIE, DATABASE, IDE
			
			$scope.extractProjectInfosTagClouds = function(){
				angular.forEach($scope.projectInfosTagClouds, function(tagClouds, key){
					$scope.projectInfosTagClouds[key].technologieTags = $filter('filter')(tagClouds, {tipTagCloud :"Technologie"} );
					$scope.projectInfosTagClouds[key].databaseTags = $filter('filter')(tagClouds, {tipTagCloud :"Database"} );
					$scope.projectInfosTagClouds[key].ideTags = $filter('filter')(tagClouds, {tipTagCloud :"IDE"} );
					$scope.projectInfosTagClouds[key].jobRoleTags = $filter('filter')(tagClouds, {tipTagCloud :"JobRole"} );
				});
				
			}
			$scope.newTag = {};
			$scope.addNewTagCloudProjectInfo= function(newName, type){
				$scope.newTag.nameTagCloud = newName;
				$scope.newTag.tipTagCloud = type;
				
				if($scope.checkDuplicateProjectInfoTag(newName, type)){ 
					return; 
				}
				 
				tagCloudService.create($scope.newTag).success(function(data){
					$scope.tagClouds.push(data);
					// $scope.tagC.push(data);
					$scope.loadedAll = false;
					loadTags();
					// PUSH TO APPROPRIATE ARRAY
					if(type == "Technologie"){
						$scope.projectInfosTagClouds[$scope.index].technologieTags.push(data);
					}else if(type == "JobRole"){
						$scope.projectInfosTagClouds[$scope.index].jobRoleTags.push(data);
					}else if(type == "Database"){
						$scope.projectInfosTagClouds[$scope.index].databaseTags.push(data);
						
					}else if(type == "IDE"){
						$scope.projectInfosTagClouds[$scope.index].ideTags.push(data);
					}
				});
			}
			
			$scope.unigueProjTag = false;
			$scope.checkDuplicateProjectInfoTag = function(newName, type){
				$scope.unigueProjTag = false;
				if(type == "Technologie"){
					angular.forEach($scope.projectInfosTagClouds[$scope.index].technologieTags, function(tag, key){
						if(tag.nameTagCloud == newName){
							$scope.uniqueProjTag = true;
						}
					});
				}else if(type == "JobRole"){
					angular.forEach($scope.projectInfosTagClouds[$scope.index].jobRoleTags, function(tag, key){
						if(tag.nameTagCloud == newName){
							$scope.uniqueProjTag = true;
						}
					});
				}else if(type == "Database"){
					angular.forEach($scope.projectInfosTagClouds[$scope.index].databaseTags, function(tag, key){
						if(tag.nameTagCloud == newName){
							$scope.uniqueProjTag = true;
						}
					});
					
				}else if(type == "IDE"){
					angular.forEach($scope.projectInfosTagClouds[$scope.index].ideTags, function(tag, key){
						if(tag.nameTagCloud == newName){
							$scope.uniqueProjTag = true;
						}
					});
				}
				
				return $scope.uniqueProjTag;
				
			}
			//NEW PROJECTI INFO ENTRY
			
			var clickedOnNewProjectInfo = false;
			$scope.newProjectInfo = function(){
				if(!clickedOnNewProjectInfo){
					$scope.getAllProjects();
					clickedOnNewProjectInfo = true;
				}
				if($scope.newProjectInfoEntry == false){
					$scope.newProjectInfoEntry = true;
				}else{
					$scope.newProjectInfoEntry = false;
				}
			}
			$scope.newProject = {};
			$scope.startDateOfProject = null;
			
			//GET ALL PROJECTS
			
			$scope.getAllProjects = function(){
				$scope.newProjectOrNot.selectedProjectToAdd = null;
				projectService.list().success(function (data){
					$scope.allProjects = data._embedded.projects;
				});
				
			};
			//add (newProject) and newProjectInfo()
			var projectToAdd = {};
			$scope.addExistingProject = function(){
				$scope.createNewProjectInfo($scope.newProjectOrNot.selectedProjectToAdd);
			}
			
			$scope.addNewProject = function(){
				projectService.save($scope.newProject).success(function(data){
					$scope.createNewProjectInfo(data);
				});
			}
			
			$scope.createNewProjectInfo = function(project){
				console.log(project);
				//create new projectInfo
				var newProjectInfo = {};
				projectInfoService.create(newProjectInfo).success(function(data){
					projectInfoService.saveEmployee(data, $scope.currEmp._links.self.href).success(function(){
						console.log("saved employee");
					});
					
					projectInfoService.saveProject(data, project._links.self.href).success(function(){
						console.log("saved project");
					});
					
				});
					//attach employee
					
					//attach project
				
				
			}
			
			// END OF PROJECT INFOES CONTROLS ?
			
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
				/*
				 * $scope.currEmp.startDate = $scope.startDate;
				 * $scope.currEmp.endDate = $scope.endDate;
				 * $scope.currEmp.startDateFromBooklet =
				 * $scope.startDateFromBooklet;
				 */
				
				employeeService.create($scope.currEmp).success(function(data) {
					$scope.currEmp = data;
					// $scope.saveTags();
					$scope.newEmployee = false;
					// $mdDialog.cancel();
					
				});	
			}
			$scope.saveEmployee = function() {
				if ($scope.currEmp.nameEmployee == ""){
					$scope.currEmp.nameEmployee = "No name";
				}
				// saving (new) DateOfBirth
				$scope.currEmp.dateOfBirth = $scope.dateBirth.toJSON();

				// saving (new) startDateFromBooklet
				$scope.currEmp.startDateFromBooklet = $scope.startDateFromBooklet;

				$scope.saveEmploymentInfos();
				$scope.updateEmploymentInfoesTagClouds();
				$scope.saveEIsToEmployee();
				$scope.currEmp.yearsOfWorking = Math.floor($scope.totalWorkExperience/12);
				if($scope.dateDictionary['Execom'] != undefined){
					$scope.currEmp.yearsOfWorkingExpInExecom = Math.floor($scope.dateDictionary['Execom'].workExperience/12);
				}else{
					$scope.currEmp.yearsOfWorkingExpInExecom =0;
				}
				if($scope.index != undefined){
					//SAVING PROJECT INFOS !
					$scope.projInfos[$scope.index].jobResponsibilities = $scope.infoToShow.jobResponsibilities;
					$scope.projInfos[$scope.index].projectExp = $scope.infoToShow.projectExperiance;
					$scope.projInfos[$scope.index].seniority = $scope.infoToShow.seniority;
					$scope.projInfos[$scope.index].durationOnProject = $scope.infoToShow.durationOnProject;
					try{
						$scope.projectInfosTagClouds[$scope.index].technologieTags = $scope.infoToShow.technologieTags;
						$scope.projectInfosTagClouds[$scope.index].databaseTags = $scope.infoToShow.databaseTags;
						$scope.projectInfosTagClouds[$scope.index].ideTags = $scope.infoToShow.ideTags;
						$scope.projectInfosTagClouds[$scope.index].jobRoleTags = $scope.infoToShow.jobRoleTags;
					}catch(err){
					
					}
					
				}
				
				$scope.saveTags();
				employeeService.update($scope.currEmp).success(function(data){
					for (i = 0; i<$scope.projInfos.length; i++){
						$http.put($scope.projInfos[i]._links.self.href, $scope.projInfos[i]).success(function(data){
							
						});
						//save tagClouds to projectInfos
						try{
							tags = [];
							
							tags = tags.concat($scope.projectInfosTagClouds[i].technologieTags,
									$scope.projectInfosTagClouds[i].databaseTags, $scope.projectInfosTagClouds[i].ideTags,
									$scope.projectInfosTagClouds[i].jobRoleTags);
							
							tagURLs = "";
							angular.forEach(tags, function(t, k){
								tagURLs = tagURLs+t._links.self.href + "\n";
							});
							console.log(tagURLs);
	
							projectInfoService.saveTagClouds($scope.projInfos[i], tagURLs);
						}catch(err){
						}
					}
					$mdDialog.cancel();
					
				});

			}
			
			/*
			 * $scope.showConfirm = function(ev, employee) {
			 * console.log($rootScope.employees); // Appending dialog to
			 * document.body to cover sidenav in docs app var confirm =
			 * $mdDialog.confirm() .parent(angular.element(document.body))
			 * .title('Are you sure you want to delete employee ' +
			 * employee.nameEmployee + ' ?') .content(employee.nameEmployee + '
			 * will be deleted permanently!') .ariaLabel('Unlucky ' +
			 * employee.nameEmployee + ' :(') .ok('Yes') .cancel('No')
			 * .targetEvent(ev); $mdDialog.show(confirm).then(function() {
			 * console.log("delete");
			 * employeeService.delete(employee).success(function (data) {
			 * $mdDialog.cancel(); }); }); };
			 */
			
			$scope.deleteEmployee = function(emp) {
				if($window.confirm("Do you really want to delete " + emp.nameEmployee + " ?")) {
					employeeService.delete(emp).success(function (data) {
			    	    $mdDialog.cancel();
				    });
				};
			};
			
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
			/*
			 * function toJSONLocal (date) { var local = new Date(date);
			 * local.setMinutes(date.getMinutes() - date.getTimezoneOffset());
			 * return local.toJSON().slice(0, 10); }
			 */
			// TAGCLOUD: FILL tagCLOUDS
			$scope.fillTagClouds = function(){
				console.log(selectedEmployee);
				employeeService.getEmployeeTagClouds(selectedEmployee).success(function(data){
					console.log(data);
					
					if(!data.hasOwnProperty('_embedded')){
						$scope.initEmptyTagClouds();
						
						
					}else{
					$scope.employeeTagClouds = data._embedded.tagClouds;
					// TAGS BY TYPE
					// <md chips ng-model = ""
					$scope.tagDictionary = {};
					// Technologie
					$scope.tagDictionary['Technologie'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Technologie"} );
					// POSITION
					$scope.tagDictionary['Position'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Position"} );
					// JobRole
					$scope.tagDictionary['JobRoles'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"JobRole"} );
					// Database
					$scope.tagDictionary['Database'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Database"} );
					// IDE
					$scope.tagDictionary['IDE'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"IDE"} );
					// Industry
					$scope.tagDictionary['Industry'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Industry"} );				
					// Platform
					$scope.tagDictionary['Platform'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Platform"} )
					// OS,
					$scope.tagDictionary['OS'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"OS"} )
					// Education
					$scope.tagDictionary['Education'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Education"} )
					// ForeignLanguage
					$scope.tagDictionary['ForeignLanguage'] = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"ForeignLanguage"} )
					}
				})
			}
			// TAGCLOUD: init if tagClouds are empty
			$scope.initEmptyTagClouds = function(){

				$scope.employeeTagClouds =[];
				// TAGS BY TYPE
				// <md chips ng-model = ""
				$scope.tagDictionary = {};
				// Technologie
				$scope.tagDictionary['Technologie'] = [];
				// POSITION
				$scope.tagDictionary['Position'] = [];
				// JobRole
				$scope.tagDictionary['JobRoles'] = [];
				// Database
				$scope.tagDictionary['Database'] = [];
				// IDE
				$scope.tagDictionary['IDE'] = [];
				// Industry
				$scope.tagDictionary['Industry'] = [];				
				// Platform
				$scope.tagDictionary['Platform'] = [];
				// OS,
				$scope.tagDictionary['OS'] = [];
				// Education
				$scope.tagDictionary['Education'] = [];
				// ForeignLanguage
				$scope.tagDictionary['ForeignLanguage'] = [];
			}
			
			// TAGCLOUD: EVERYTHING FOR CHIPS AND AUTOCOMPLETE
			$scope.tagClouds = [];
			(loadAllTags = function(tagUrl){
				tagCloudService.list(tagUrl).success(function(data){
					
					
					$scope.tagClouds =$scope.tagClouds.concat( data._embedded.tagClouds);
					if(data._links.hasOwnProperty('next') ){
						loadAllTags(data._links.next.href);
					}
					
					// $scope.tagC = $scope.tagClouds;
					
				})
			})('/tagClouds');
			
				// $scope.tagC = $scope.tagClouds;
				// $scope.tagClouds = $scope.tagC;
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
				
				  if($scope.checkDuplicate(newName, type)){ 
					  return; 
				 }
				 
				tagCloudService.create($scope.newTag).success(function(data){
					$scope.tagClouds.push(data);
					// $scope.tagC.push(data);
					$scope.loadedAll = false;
					loadTags();
					// PUSH TO APPROPRIATE ARRAY
					$scope.tagDictionary[type].push(data);
				});
			}
			
			
			$scope.nonUnique = false;
			$scope.checkDuplicate = function(name, type){
				$scope.nonUnique = false;
				angular.forEach($scope.tagDictionary[type], function(value, key){
					if(value.nameTagCloud == name){
						$scope.nonUnique = true;
					}
					
					
				});
				
				return $scope.nonUnique;
			}
			// IMAGE CONTROLS
			
			$scope.imageSwitch = true;
			$scope.imageUploaded = function(serverResponse){
				$scope.currEmp.image = serverResponse;
				console.log(serverResponse);
				$scope.imageSwitch = true;
			}

		});