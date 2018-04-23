'use strict';
app.controller('ServicoCreateController', ['$http', '$location', '$scope', function($http, $location, $scope) {

	var appCtrl = $scope.appCtrl;

	var self = this;
	
	
	$scope.page = 'servico-new';

	$scope.onLoadHtmlFileInNgView = function () {
	}
	
	self.create = function(){
		
		var servico = self.servico;
		
		if(servico.isServicoUrb == null){
			servico.isServicoUrb == false;
		}
		$.post( "api/servico", self.servico  ).done( function(returnOfRequest) {
		//On Finish
		}).done(function(returnOfRequest) {
			var msg = "Servico atualizado com sucesso!";
			appCtrl.showNotification("Servico inserido", msg, "success");
			location.href='#servico-list';
		}).fail(function(returnOfRequest) {
			var msg = "Houve um erro inesperdo, se o problema persistir entre em contrato com o Dep. de T.I";
			appCtrl.showNotification("ERROR", msg, "danger");
			location.href='#servico-list';
		}).always(function() {
		
		});
		
	}
	

}]);