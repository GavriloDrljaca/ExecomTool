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
		$scope.empPic = "";
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
			tagCloudService.saveTag($scope.employee._links.tagClouds.href,$scope.req)
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
		$scope.addPicture = function(){
			  var f = document.getElementById('file').files[0],
			  
			  r = new FileReader();
			  r.onloadend = function(e){
				  
			    var data = e.target.result;
			    //send you binary data via $http or $resource or do anything else with it
			  }
			  r.readAsBinaryString(f);
			  //$scope.pic = f;
			}
		 $scope.previewFile = function (){
			 //alert("opa");
		        $scope.preview = document.querySelector('img'); //selects the query named img
		       var file    = document.querySelector('input[type=file]').files[0]; //sames as here
		       var reader  = new FileReader();

		       reader.onloadend = function () {
		           $scope.preview.src = reader.result;
		       }

		       if (file) {
		           reader.readAsDataURL(file); //reads the data as a URL
		       } else {
		           $scope.preview.src = "";
		       }
		  }
		
		 $scope.imageUploaded = function(serverResponse){
			 //alert(serverResponse);
			 $scope.empPic = serverResponse;
			 console.log($scope.empPic);
		 }

});