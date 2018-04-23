'use strict';
app.controller('ProcessoCreateController', ['$http', '$location', '$scope', function($http, $location, $scope) {

	var appCtrl = $scope.appCtrl;

	var self = this;
	$scope.page = 'processo-new';

	$scope.onLoadHtmlFileInNgView = function () {
		self.listSercicosIds();
		self.listRequerenteIds();
		self.listSecretariaIds();
	}
	

	self.listSercicosIds= function (){
		
		appCtrl.loadSpiner(true);
		
		$http.get( "api/servico/get-servicos-for-status/true" + "?radom=" + Math.random() ).then(function(response) {
			
			appCtrl.loadSpiner(false);
			self.servicos = response.data;
			console.log(response.data);
    			appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>ALTERADO</span> com sucesso.");
			
		}, function (error) {
			
			appCtrl.loadSpiner(false);
			appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o Dep T.I");
			window.location.href = "#home";
			
		})};
		
		self.listSecretariaIds= function (){
			
			appCtrl.loadSpiner(true);
			
			$http.get( "api/secretaria/get-secretaria-for-status/true" + "?radom=" + Math.random() ).then(function(response) {
				
				appCtrl.loadSpiner(false);
				self.secretarias = response.data;
				console.log(response.data);
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
		
		self.create = function () {
			
			console.log( "Carregando..." );	
				
			var data = self.processo.dataProcesso;
			console.log(data);
			
			var from = data.split("/")
			var ano_aniversario= from[2];
			var mes_aniversario= from[1];
			var dia_aniversario= from[0];
			
			self.processo.dataProcesso = +ano_aniversario+"-"+mes_aniversario+"-"+dia_aniversario+"T14:42:14Z";
						
			console.log(self.processo.dataProcesso);
		
			$.post( "api/processo", { processo: "" + JSON.stringify(self.processo) + "" } , function(response) {
				console.log( "success" );
			})
			.done(function(response) {
					window.location.href = '#processo-list';
			})
			.fail(function(response) {
				console.log("FALHA");
				window.location.href='#processo-list';
			})
			.always(function() {
				console.log( "Concluído" );
			});
			
//			$http({
//			    method : "POST",
//			    url : "api/processo",
//			    data : JSON.stringify(self.processo),
//			    headers : {
//			        'Content-Type' : 'application/json'
//			    }
//			}).then(function (response) {
//				appCtrl.loadSpiner(false);
//				appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>Editar</span> com sucesso.");
//				window.location.href = "#processo-list";
//				
//			}, function (error) {		
//				appCtrl.loadSpiner(false);
//				appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o T.I");
//				window.location.href = "#processo-list";			
//			});
		}

}]);