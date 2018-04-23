'use strict';
app.controller('SecretariaCreateController', ['$http', '$location', '$scope', function($http, $location, $scope) {

	var appCtrl = $scope.appCtrl;

	var self = this;
	$scope.page = 'secretaria-new';

	$scope.onLoadHtmlFileInNgView = function () {
	}
	
	self.create = function(){
		
		$.post( "api/secretaria", self.secretaria  ).done( function(returnOfRequest) {
		//On Finish
		}).done(function(returnOfRequest) {
			var msg = "Secretaria atualizado com sucesso!";
			appCtrl.showNotification("Secretaria inserido", msg, "success");
			location.href='#secretaria-list';
		}).fail(function(returnOfRequest) {
			var msg = "Houve um erro inesperdo, se o problema persistir entre em contrato com o Dep. de T.I";
			appCtrl.showNotification("ERROR", msg, "danger");
			location.href='#secretaria-list';
		}).always(function() {
		
		});
		
	}
	
self.createJson = function () {
		
		console.log( "Carregando..." );
		
		$.post( "api/secretaria", { secretaria: "" + JSON.stringify(self.secretaria) + "" } , function(response) {
			console.log( "success" );
		})
		.done(function(response) {
			var msg = "Secretaria atualizado com sucesso!";
			appCtrl.showNotification("Secretaria inserido", msg, "success");
			location.href='#secretaria-list';
			
		})
		.fail(function(response) {
			console.log(response);
			console.log("FALHA");
		})
		.always(function() {
			console.log( "Concluído" );
		});
		
	}

}]);