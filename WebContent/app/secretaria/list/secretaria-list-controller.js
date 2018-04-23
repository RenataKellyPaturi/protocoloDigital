'use strict';
app.controller('SecretariaListController', ['$http', '$location', '$scope','$routeParams', function($http, $location, $scope, $routeParams) {

	var self = this;
	var appCtrl = $scope.appCtrl;
	
	$scope.page = 'secretaria-list';
	
	$scope.onLoadHtmlFileInNgView = function () {
		appCtrl.carregarUsuarioLogado();
		self.listarSecretaria();
	}

	self.listarSecretaria = function () {

		appCtrl.loadSpiner(true);

		$http.get( "api/secretaria/get-secretaria-for-status/true"+"?radom="+Math.random()).then(function(response) {

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
	//inativar secretaria
	 self.inativaSecretaria = function (idSecretaria) {
			
	    	console.log(idSecretaria);
	    	
	    	var req = {
	    			method: 'DELETE',
	    			url: "api/secretaria/" + idSecretaria,
	    	}
			
			$http(req).then(function (response) {
				
				appCtrl.loadSpiner(false);
				appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>ALTERADO</span> com sucesso.");
				window.location.href = "#secretaria-list";
				
			}, function (error) {
				
				appCtrl.loadSpiner(false);
				appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o N.I.T. PMC");
				window.location.href = "#home";
				
			})};
	
}]);