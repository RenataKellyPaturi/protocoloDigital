'use strict';
app.controller('ProcessoPrintController', ['$http', '$location', '$scope','$routeParams', function($http, $location, $scope, $routeParams) {

	var self = this;
	var appCtrl = $scope.appCtrl;
	var idProcesso = $routeParams.idProcesso;
	$scope.page = 'processo-print';
		
		
	$scope.onLoadHtmlFileInNgView = function () {
		appCtrl.carregarUsuarioLogado();
		self.carregarProcesso();
		self.listServiçosIds();
		self.listRequerenteIds();
		self.listSecretariasIds();
	}
	
	// 
	self.carregarProcesso = function (){
		
		appCtrl.loadSpiner(true);
		

		$http.get( "api/processo/" +idProcesso + "?radom=" + Math.random() ).then(function(response) {
			
			appCtrl.loadSpiner(false);
			self.processo = response.data;
    			appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>ALTERADO</span> com sucesso.");
			
		}, function (error) {
			
			appCtrl.loadSpiner(false);
			appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o Dep T.I");
			window.location.href = "#home";
			
		})};

	
		self.listRequerenteIds= function (){
			
			appCtrl.loadSpiner(true);
			
			$http.get( "api/requerente/get-requerentes-for-status/true" + "?radom=" + Math.random() ).then(function(response) {
				
				appCtrl.loadSpiner(false);
				self.requerentes = response.data;
				console.log(response.data);
					appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>ALTERADO</span> com sucesso.");
				
			}, function (error) {
				
				appCtrl.loadSpiner(false);
				appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o Dep T.I");
				window.location.href = "#home";
				
			})};
	
		self.listSecretariasIds= function (){
			
			appCtrl.loadSpiner(true);
			
			$http.get( "api/secretaria/get-secretaria-for-status/true" + "?radom=" + Math.random() ).then(function(response) {
				
				appCtrl.loadSpiner(false);
				self.secretarias = response.data;
	    			appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>ALTERADO</span> com sucesso.");
	    			
			}, function (error) {
				
				appCtrl.loadSpiner(false);
				appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o Dep T.I");
				window.location.href = "#home";
				
			})};
			
			self.listServiçosIds = function (){
				
				appCtrl.loadSpiner(true);
				
				$http.get( "api/servico/get-servicos-for-status/true" + "?radom=" + Math.random() ).then(function(response) {
					
					appCtrl.loadSpiner(false);
					self.servicos = response.data;
		    			appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>ALTERADO</span> com sucesso.");
		    			
				}, function (error) {
					
					appCtrl.loadSpiner(false);
					appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o Dep T.I");
					window.location.href = "#home";
					
				})};
				

	}]);