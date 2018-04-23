'use strict';
app.controller('HomeController', ['$http', '$location', '$scope', function($http, $location, $scope) {

	var appCtrl = $scope.appCtrl;

	var self = this;

	$scope.onLoadHtmlFileInNgView = function () {
		appCtrl.carregarUsuarioLogado();
	}

}]);
