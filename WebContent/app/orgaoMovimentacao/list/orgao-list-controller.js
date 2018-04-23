'use strict';
app.controller('OrgaoListController', ['$http', '$location', '$scope','$routeParams', function($http, $location, $scope, $routeParams) {

	var self = this;
	var appCtrl = $scope.appCtrl;
	
	$scope.page = 'orgao-list';
	
	$scope.onLoadHtmlFileInNgView = function () {
		appCtrl.carregarUsuarioLogado();
		self.listarOrgaos();
	}

	self.listarOrgaos= function () {

		appCtrl.loadSpiner(true);

		$http.get( "api/orgaoMovimentacao/get-orgao-for-status/true"+"?radom="+Math.random()).then(function(response) {

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
	//inativar orgao
	 self.inativaOrgao = function (idOrgao) {
			
	    	console.log(idOrgao);
	    	
	    	var req = {
	    			method: 'DELETE',
	    			url: "api/orgaoMovimentacao/" + idOrgao,
	    	}
			
			$http(req).then(function (response) {
				
				appCtrl.loadSpiner(false);
				appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>ALTERADO</span> com sucesso.");
				window.location.href = "#orgao-list";
				
			}, function (error) {
				
				appCtrl.loadSpiner(false);
				appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o N.I.T. PMC");
				window.location.href = "#home";
				
			})};
	
}]);