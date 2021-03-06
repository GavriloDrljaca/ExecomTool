app.controller('employeeController', function($http, $rootScope, $scope, $window, $mdDialog, $mdToast, selectedEmployee, $filter, employeeService, tagCloudService, employmentInfoesService, projectInfoService, projectService ) {


			$scope.init = function(){
				$scope.newProjectInfoEntry = false;
				$scope.newProjectOrNot = {};
				$scope.projInfos = [];
				$scope.newProject = {};
				$scope.newProject.startDate = new Date();
				
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
					})
					$scope.empInfoesURLs = "";
				});
			}
			// EXTRACTING EMPLOYEMENT DATES
			$scope.extractEmploymentDates = function(){
				$scope.dateDictionary = {};
				// $scope.startDate = new Date($scope.currentEmployee.startDate);
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
				$window.document.getElementById("companyNameInput").focus();
			}
			//CANCEL INPUT OF NEW EMPLOYMENT HISTORY
			
			$scope.cancelAddNewEmpHistory = function(){
				$scope.addNewEmpInfo = "false";
			}
			// SAVE NEW EMPLOYMENT HISTORY
			$scope.saveNewEmploymentHistory = function(){
				employmentInfoesService.create($scope.newEmpInfo).success(function(data){
					$scope.employmentInfos.push(data);
					$scope.extractEmploymentDates();
					$scope.extractEmploymentInfosTags();
					//NOVI EMPLOYEMNT INFO SE DIREKTNO CUVA BEZ potrebe
					
					// ATTACH IT TO EMPLOYEEE
					// IT WILL BE ATTACH IN FUNCTION saveEItoEMployee
					/*
					 * employmentInfoesService.saveEmployee(data,
					 * $scope.currentEmployee).success(function(){ alert("saved!"); })
					 */
				});
				
				$scope.addNewEmpInfo = "false";
			}
			$scope.saveEIsToEmployee = function(){
				angular.forEach($scope.employmentInfos, function(ei, key){
					employmentInfoesService.saveEmployee(ei, $scope.currentEmployee);
					
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
				$scope.projInfos = [];
				if (selectedEmployee._links!=undefined)	
					$http.get(selectedEmployee._links.projectInfos.href).success(function (data) {
						if(data._embedded != undefined) {
							$scope.projInfos = data._embedded.projectInfoes;
						} else {
							$scope.projInfos = [];
						}
						for(i = 0; i<$scope.projInfos.length; i++) {
							$http.get($scope.projInfos[i]._links.project.href).success((function (i) {
								return function (data) {
							
								$scope.projects[i] =  data;
								}
								
							})(i));
							
							$http.get($scope.projInfos[i]._links.tagClouds.href).success( (function (i) {
									
								return function(data){
								if(data._embedded != undefined){
									if(data._embedded.hasOwnProperty('tagClouds')){
										$scope.projectInfosTagClouds[i] = (data._embedded.tagClouds);
									}else{
										$scope.projectInfosTagClouds[i] = [];
									}
								}else{
									$scope.projectInfosTagClouds[i] = [];
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
					$scope.projInfos[$scope.index].active = $scope.infoToShow.active;
					$scope.projInfos[$scope.index].durationOnProject = $scope.infoToShow.durationOnProject;
					try{
						$scope.projectInfosTagClouds[$scope.index].technologieTags = $scope.infoToShow.technologieTags;
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
				$scope.infoToShow.active = $scope.projInfos[index].active;
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
			//NEW PROJECT INFO ENTRY
			
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
			
			//GET ALL PROJECTS
			$scope.getAllProjects = function(){
				$scope.newProjectOrNot.selectedProjectToAdd = null;
				projectService.list().success(function (data){
					
					$scope.allProjects = data._embedded.projects;
					
					//show only projects that are not previously added
					angular.forEach($scope.projects, function(projOption, key){
						angular.forEach($scope.allProjects, function(proj, key2){
							if(projOption._links.self.href == proj._links.self.href){
								$scope.allProjects.splice(key2,1);
							}
						});
						
					});
				});
				
			};
			
			//add (newProject) and newProjectInfo()
			var projectToAdd = {};
			$scope.addExistingProject = function(){
				$scope.createNewProjectInfo($scope.newProjectOrNot.selectedProjectToAdd);
				
				//delete project from list of all projects
				
				angular.forEach($scope.allProjects, function(proj, key){
					if(proj._links.self.href == $scope.newProjectOrNot.selectedProjectToAdd){
						$scope.allProjects.splice(key, 1);
					}
				});
				
				
				$scope.newProjectOrNot.selectedProjectToAdd = {};
			}
			
			$scope.addNewProject = function(){
				projectService.save($scope.newProject).success(function(data){
					alert(data._links.self.href)
					$scope.createNewProjectInfo(data._links.self.href);
				});
			}
			
			$scope.createNewProjectInfo = function(project){
				//create new projectInfo
				var newProjectInfo = {};
				projectInfoService.create(newProjectInfo).success(function(data){
					newProjectInfo = data;
					projectInfoService.saveEmployee(data, $scope.currentEmployee._links.self.href).success(function(){
						projectInfoService.saveProject(data, project).success(function(data){
							//updateVIEW
							$scope.updateProjectInfoView(data,newProjectInfo)
							
						});
					});
				});
			}
			
			$scope.updateProjectInfoView = function(newProject , newProjectInfo){
				$scope.projInfos.push(newProjectInfo);
				$scope.getProjects();
				$scope.newProjectInfoEntry = false;
				$scope.newProject = {};
			}
			
			// END OF PROJECT INFOES CONTROLS ?
			
			$scope.activeForm = "none";
			$scope.currRealDeal = selectedEmployee;

			$scope.currentEmployee = $scope.currRealDeal;
			// Date of Birth
			$scope.dateBirth = new Date(selectedEmployee.dateOfBirth);

			// startDate
			$scope.startDate = new Date($scope.currentEmployee.startDate);
			// endDate
			$scope.endDate = new Date($scope.currentEmployee.endDate);
			// startDate from booklet
			/*$scope.startDateFromBooklet = new Date(
					$scope.currentEmployee.startDateFromBooklet);*/

			$scope.createEmployee = function(){
				if ($scope.currentEmployee.nameEmployee == "" || angular.equals($scope.currentEmployee.nameEmployee,undefined)){
					$scope.currentEmployee.nameEmployee = "No name";
				}
				$scope.currentEmployee.dateOfBirth = $scope.dateBirth.toJSON();
				/*
				 * $scope.currentEmployee.startDate = $scope.startDate;
				 * $scope.currentEmployee.endDate = $scope.endDate;
				 * $scope.currentEmployee.startDateFromBooklet =
				 * $scope.startDateFromBooklet;
				 */
				
				employeeService.create($scope.currentEmployee).success(function(data) {
					$scope.currentEmployee = data;
					// $scope.saveTags();
					selectedEmployee = $scope.currentEmployee;
					$scope.newEmployee = false;
					// $mdDialog.cancel();
					$scope.init();
					
				});	
			}
			$scope.saveEmployee = function() {
				if ($scope.currentEmployee.nameEmployee == ""){
					$scope.currentEmployee.nameEmployee = "No name";
				}
				// saving (new) DateOfBirth
				$scope.currentEmployee.dateOfBirth = $scope.dateBirth.toJSON();

				// saving (new) startDateFromBooklet
				//$scope.currentEmployee.startDateFromBooklet = $scope.startDateFromBooklet;

				$scope.saveEmploymentInfos();
				$scope.updateEmploymentInfoesTagClouds();
				$scope.saveEIsToEmployee();
				$scope.currentEmployee.yearsOfWorking = Math.floor($scope.totalWorkExperience/12);
				if($scope.dateDictionary['Execom'] != undefined){
					$scope.currentEmployee.yearsOfWorkingExpInExecom = Math.floor($scope.dateDictionary['Execom'].workExperience/12);
				}else{
					$scope.currentEmployee.yearsOfWorkingExpInExecom =0;
				}
				if($scope.index != undefined){
					//SAVING PROJECT INFOS !
					$scope.projInfos[$scope.index].jobResponsibilities = $scope.infoToShow.jobResponsibilities;
					$scope.projInfos[$scope.index].active = $scope.infoToShow.active;
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
				employeeService.update($scope.currentEmployee).success(function(data){
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
	
							projectInfoService.saveTagClouds($scope.projInfos[i], tagURLs);
						}catch(err){
						}
					}
					$scope.showSimpleToast();
					//$mdDialog.cancel();
					
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
					//frist you need to delete emloyees connections to other entities!!!
				/*	employeeService.deleteAllTags(emp).success(function(data){
						employeeService.getEmploymentInfos(emp).success(function(data){
							angular.forEach(data._embedded.employmentInfoes, function(empInf, key){
								employmentInfoesService.deleteEmployee(empInf, " ");
							});
							employeeService.deleteAllProjectInfos(emp).success(function(data){
								angular.forEach(data._embedded.projectInfoes, function(projInf, key){
									projectInfoService.saveEmployee(projInf, " ");
								});
								employeeService.delete(emp).success(function (data) {
									$mdDialog.cancel();
								});
							})
						})

					});*/
					employeeService.deleteAllTags(emp).success(function(data){
						employeeService.deleteAllProjectInfos(emp).success(function(data){
							try{
							angular.forEach(data._embedded.projectInfoes, function(projInf, key){
								projectInfoService.saveTagClouds(projInf, " ");
							});
							}catch(err){
								
							}
							
						}).then(function(){
							employeeService.getEmploymentInfos(emp).success(function(data){
								try{
									angular.forEach(data._embedded.employmentInfoes, function(empInf, key){
										employmentInfoesService.updateEmploymentInfoesTags (empInf, " ");
									});
								}catch(err){
									
								}
							}).then(function(){
								employeeService.delete(emp).success(function (data) {
									$mdDialog.cancel();
								});
							});
						});
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
				employeeService.getEmployeeTagClouds($scope.currentEmployee).success(function(data){
					
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
				tagCloudService.saveTag($scope.currentEmployee._links.tagClouds.href,$scope.req)
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
			$scope.imageUploaded = function(serverResponse, size){
				$scope.currentEmployee.image = serverResponse;
				$scope.imageSwitch = true;
				alert(serverResponse);
			}
			$scope.generateCV = function() {
				emp = $scope.currentEmployee;
				array = emp._links.self.href.split('/');
				$window.open("/report/cv?id=" + array[array.length-1],"_self");
			}
			//CONFIRM EXIT
			$scope.confirmExit = function(ev){
				var confirm = $mdDialog.confirm()
					.title("Do you want to save changes to employee : "+ $scope.currentEmployee.nameEmployee)
					.ok("YES")
					.cancel("NO")
					.targetEvent(ev);
				
				$mdDialog.show(confirm).then(function(){
					$scope.saveEmployee();
					$mdDialog.cancel();
				}, function(){
					$mdDialog.cancel();
				})
			}
			//MD-TOAST
			$scope.toastPosition = {
				    bottom: false,
				    top: true,
				    left: false,
				    right: true
				};
				
				$scope.getToastPosition = function() {
				    return Object.keys($scope.toastPosition)
				      .filter(function(pos) { return $scope.toastPosition[pos]; })
				      .join(' ');
				};
				
				$scope.showSimpleToast = function() {
				    $mdToast.show(
				      $mdToast.simple()
				        .content("Employee " + $scope.currentEmployee.nameEmployee + " updated!")
				        .position($scope.getToastPosition())
				        .hideDelay(3000)
				    );
				};
		});
