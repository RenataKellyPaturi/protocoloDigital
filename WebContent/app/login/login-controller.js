'use strict';
app.controller('LoginController', ['$http', '$location', '$scope', function($http, $location, $scope) {

	var appCtrl = $scope.appCtrl;

	var self = this;

	$scope.onLoadHtmlFileInNgView = function () {
	}
	
	self.submit = function() {
		console.log( self.user.name );
		appCtrl.login( self.user );
	}

}]);