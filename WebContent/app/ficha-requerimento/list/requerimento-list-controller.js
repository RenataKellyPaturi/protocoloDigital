'use strict';
app.controller('RequerimentoListController', ['$http', '$location', '$scope','$routeParams', function($http, $location, $scope, $routeParams) {

	var self = this;
	var appCtrl = $scope.appCtrl;
	
	$scope.page = 'requerimento-list';
	
	$scope.onLoadHtmlFileInNgView = function () {
		appCtrl.carregarUsuarioLogado();
		self.listarRequerimentos();
	}

	self.listarRequerimentos = function () {

		appCtrl.loadSpiner(true);

		$http.get( "api/folha_requerimento/get-folhas-by-status-requerimentos/true"+"?radom="+Math.random()).then(function(response) {

			appCtrl.loadSpiner(false);

			var arrayForNgRepeat = response.data;
			console.log(response.data);
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
	//inativar requerimento
	 self.inativaRequerimento = function (idRequerimento) {
			
	    	console.log(idRequerimento);
	    	
	    	var req = {
	    			method: 'DELETE',
	    			url: "api/folha_requerimento/" + idRequerimento,
	    	}
			
			$http(req).then(function (response) {
				
				appCtrl.loadSpiner(false);
				appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>ALTERADO</span> com sucesso.");
				window.location.href = "#requerimento-list";
				
			}, function (error) {
				
				appCtrl.loadSpiner(false);
				appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o T.I PMC");
				window.location.href = "#home";
				
			})};
	
}]);