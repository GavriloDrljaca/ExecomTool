app.controller('tagCloudController', function($scope, $window, $filter, tagCloudService, employeeService){
	
	$scope.init = function(){
		/*$scope.tagClouds = [];
		(loadAllTags = function(tagUrl){
			tagCloudService.list(tagUrl).success(function(data){
				alert("success");
				
				console.log(data);
				$scope.tagClouds =$scope.tagClouds.concat( data._embedded.tagClouds);
				if(data._links.hasOwnProperty('next') ){
					loadAllTags(data._links.next.href);
				}
				
			})
		})('/tagClouds');*/
		employeeService.getById(1).success(function(data){

			$scope.employee = data;
			
			
			employeeService.getEmployeeTagClouds($scope.employee).success(function(data){
				
				$scope.employeeTagClouds = data._embedded.tagClouds;
				//TAGS BY TYPE
				//<md chips ng-model = ""
				//Technologie
				$scope.employeeTechnologieTags = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Technologie"} );
				//POSITION
				$scope.employeePositionTags = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Position"} );
				//JobRole
				$scope.employeeJobRoleTags = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"JobRole"} );
				//Database
				$scope.employeeDatabaseTags = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Database"} );
				//IDE
				$scope.employeeIDETags = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"IDE"} );
				//Industry
				$scope.employeeIndustryTags = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Industry"} );				
				//Platform
				$scope.employeePlatformTags = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Platform"} )
				//OS,
				$scope.employeeOSTags = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"OS"} )
				//Education
				$scope.employeeEducationTags = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Education"} )
				//ForeignLanguage
				$scope.employeeForeignLanguageTags = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"ForeignLanguage"} )
			})
			
		})
	 }
		//NEW TAG CLOUD
		$scope.newTag = {};
		$scope.newTag.nameTagCloud = "";
		$scope.newTag.tipTagCloud = "";
		
		$scope.saveNewTag = function(newTag){
			tagCloudService.create(newTag).success(function(){
				alert("saved!");
			});
		}
		//TAG CLOUD, CHIPS, BE CAREFUL NOT TO DUPLICATE VARIABLES !!!
		$scope.tagClouds = [];
		(loadAllTags = function(tagUrl){
			tagCloudService.list(tagUrl).success(function(data){
				
				
				console.log(data);
				$scope.tagClouds =$scope.tagClouds.concat( data._embedded.tagClouds);
				if(data._links.hasOwnProperty('next') ){
					loadAllTags(data._links.next.href);
				}
				$scope.tagC = $scope.tagClouds;
				
			})
		})('/tagClouds');
		
			$scope.tagC = $scope.tagClouds;
		 	var self = this;
		 	
		 	$scope.readonly = false;
		    $scope.selectedItem = null;
		    $scope.searchText = null;
		    $scope.querySearch = querySearch;
		    self.vegetables = loadTags();
		    self.numberChips = [];
		    self.numberChips2 = [];
		    self.numberBuffer = '';
	
		    /**
		     * Search for TAGCLOUDS
		     */
		    function querySearch (query, tipQuery) {

		      var results = query ? $scope.tagC.filter(createFilterFor(query, tipQuery)) : [];
		      return results;
		    }
	
		    /**
		     * Create filter function for a query string
		     */
		    function createFilterFor(query, tipQuery) {
		      var lowercaseQuery = angular.lowercase(query);
		      var lowercaseTipQuery = angular.lowercase(tipQuery);
		      
		      return function filterFn(tag) {
		    	
		        return (tag._lowername.indexOf(lowercaseQuery) === 0) &&
		            (tag._lowertype.indexOf(lowercaseTipQuery) === 0);
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
			alert("save");
			$scope.req = "";
			$scope.newTags = [];
			$scope.newTags = $scope.newTags.concat($scope.employeeTechnologieTags,$scope.employeePositionTags,
					$scope.employeeJobRoleTags, $scope.employeeDatabaseTags,
					$scope.employeeIDETags,$scope.employeeIndustryTags, $scope.employeePlatformTags,$scope.employeeOSTags , 
					$scope.employeeEducationTags,
					$scope.employeeForeignLanguageTags);
			
			for(i =0; i<$scope.newTags.length; i++){
				$scope.req+=$scope.newTags[i]._links.self.href +"\n";
			}
			tagCloudService.saveTag($scope.employee._links.tagClouds.href,$scope.req)
		}
		
});