'use strict';
app.controller('RequerenteCreateController', ['$http', '$location', '$scope', function($http, $location, $scope) {

	var appCtrl = $scope.appCtrl;

	var self = this;
	$scope.page = 'requerente-new';

	$scope.onLoadHtmlFileInNgView = function () {
		self.listSecretariasIds();
	}
	
self.create = function(){
		
		$.post( "api/requerente", self.requerente  ).done( function(returnOfRequest) {
			console.log(self.requerente);
		//On Finish
		}).done(function(returnOfRequest) {
			var msg = "Requerente atualizado com sucesso!";
			appCtrl.showNotification("Requerente inserido", msg, "success");
			location.href="#requerente-list";
		}).fail(function(returnOfRequest) {
			var msg = "Houve um erro inesperdo, se o problema persistir entre em contrato com o Dep. de T.I";
			appCtrl.showNotification("ERROR", msg, "danger");
			location.href="#requerente-list";
		}).always(function() {
		
		});
		
	}
	self.listSecretariasIds= function (){
		
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
		
		self.novoCadastro = function () {
			
			console.log( "Carregando..." );	
		
			$.post( "api/requerente", { requerente: "" + JSON.stringify(self.requerente) + "" } , function(response) {
				console.log( "success" );
			})
			.done(function(response) {
					window.location.href = '#requerente-list';
			})
			.fail(function(response) {
				console.log(response);
				console.log("FALHA");
				window.location.href='#requerente-list';
			})
			.always(function() {
				console.log( "Concluído" );
			});
			
		}

}]);