'use strict';
app.controller('RequerenteListController', ['$http', '$location', '$scope','$routeParams', function($http, $location, $scope, $routeParams) {

	var self = this;
	var appCtrl = $scope.appCtrl;
	
	$scope.page = 'requerente-list';
	
	$scope.onLoadHtmlFileInNgView = function () {
		appCtrl.carregarUsuarioLogado();
		self.listarRequerentes();
	}

	self.listarRequerentes = function () {

		appCtrl.loadSpiner(true);

		$http.get( "api/requerente/get-requerentes-for-status/true"+"?radom="+Math.random()).then(function(response) {

			appCtrl.loadSpiner(false);

			var arrayForNgRepeat = response.data;
			
			//Ordenar Por Name -- INICIO
			arrayForNgRepeat.sort(function(a,b) {
			    if(a.name < b.name) return -1;
			    if(a.name > b.name) return 1;
			    return 0;
			});
			//Ordenar Por Name -- TÉRMINO

			$scope.dataOfGenericList = arrayForNgRepeat;
			$scope.initialDataOfGenericList = arrayForNgRepeat;
			

		}, function(response) {
			//ERRO
			appCtrl.loadSpiner(false);
			alert("Foi Erroo");
			window.location.href = "#home";
		});
	}
	//inativar requerente
	 self.inativaRequerente = function (idRequerente) {
			
	    	console.log(idRequerente);
	    	
	    	var req = {
	    			method: 'DELETE',
	    			url: "api/requerente/" + idRequerente,
	    	}
			
			$http(req).then(function (response) {
				
				appCtrl.loadSpiner(false);
				appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>ALTERADO</span> com sucesso.");
				window.location.href = "#requerente-list";
				
			}, function (error) {
				
				appCtrl.loadSpiner(false);
				appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o T.I PMC");
				window.location.href = "#home";
				
			})};
	
}]);