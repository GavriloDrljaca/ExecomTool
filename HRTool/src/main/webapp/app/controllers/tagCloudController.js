app.controller('tagCloudController', function($scope, $window, $filter, tagCloudService, employeeService){
	
	$scope.init = function(){
		tagCloudService.list().success(function(data){
			$scope.tagClouds = data._embedded.tagClouds;

		})
		employeeService.getById(1).success(function(data){

			$scope.employee = data;
			
			alert($scope.employee._links.tagClouds.href);
			/*$scope.employeeTagClouds = $scope.employee.tagClouds;
			$scope.employeeTechnologieTags = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Technologie"} );
			console.log($scope.employee);*/
			employeeService.getEmployeeTagClouds($scope.employee).success(function(data){
				
				$scope.employeeTagClouds = data._embedded.tagClouds;
				$scope.employeeTagClouds[1].nameTagCloud = "BLABLABLA";
				//TAGS BY TYPE
				
				$scope.employeeTechnologieTags = $filter('filter')($scope.employeeTagClouds, {tipTagCloud :"Technologie"} );
			})
			
		})
	 }
		
		//TAG CLOUD, CHIPS, BE CAREFUL NOT TO DUPLICATE VARIABLES !!!
		tagCloudService.list().success(function(data){
			$scope.tagC = data._embedded.tagClouds;
			$scope.tagClouds = $scope.tagC;

		}).success(function(){
			//CHIPS
		 	var self = this;
		 	
		 	$scope.readonly = false;
		    $scope.selectedItem = null;
		    $scope.searchText = null;
		    $scope.querySearch = querySearch;
		    self.vegetables = loadTags();
		    //self.selectedVegetables = [];
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
	    });
		
		$scope.saveTag = function(){
			alert("save");
			tagCloudService.saveTag($scope.employeeTagClouds[4])
		}
		
});