app.controller('tagCloudController', function($scope, $window, $filter, tagCloudService, employeeService){
	
	$scope.init = function(){
		/*$scope.tagClouds = [];
		(loadAllTags = function(tagUrl){
			tagCloudService.list(tagUrl).success(function(data){
				
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
			})
			
		})
	 }
		//NEW TAG CLOUD

		//TAG CLOUD, CHIPS, BE CAREFUL NOT TO DUPLICATE VARIABLES !!!
		$scope.tagClouds = [];
		(loadAllTags = function(tagUrl){
			tagCloudService.list(tagUrl).success(function(data){
				
				
				$scope.tagClouds =$scope.tagClouds.concat( data._embedded.tagClouds);
				if(data._links.hasOwnProperty('next') ){
					loadAllTags(data._links.next.href);
				}
				
				$scope.tagC = $scope.tagClouds;
				
			})
		})('/tagClouds');
		
			$scope.tagC = $scope.tagClouds;
			$scope.tagClouds = $scope.tagC;
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
			tagCloudService.saveTag($scope.employee._links.tagClouds.href,$scope.req)
		}
		
		$scope.newTag = {};
		$scope.addNewTagCloud= function(newName, type){
			$scope.newTag.nameTagCloud = newName;
			$scope.newTag.tipTagCloud = type;
			
			tagCloudService.create($scope.newTag).success(function(data){
				console.log(data);
				$scope.tagClouds.push(data);
				$scope.loadedAll = false;
				//PUSH TO APPROPRIATE ARRAY 
				$scope.tagDictionary[type].push(data);
			});
		}
		
		
		
		$scope.saveNewTag = function(newTag){
			
		}
		
});