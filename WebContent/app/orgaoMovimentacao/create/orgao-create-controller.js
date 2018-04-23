'use strict';
app.controller('OrgaoCreateController', ['$http', '$location', '$scope', function($http, $location, $scope) {
	var appCtrl = $scope.appCtrl;
	var self = this;
	$scope.page = 'orgao-new';

	$scope.onLoadHtmlFileInNgView = function () {
		self.listFichaIds();
	}
	
	self.listFichaIds= function (){
		
		appCtrl.loadSpiner(true);
		
		$http.get( "api/processo/get-fichas-for-status/true" + "?radom=" + Math.random() ).then(function(response) {
			
			appCtrl.loadSpiner(false);
			self.fichas = response.data;
    			appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>ALTERADO</span> com sucesso.");
			
		}, function (error) {
			
			appCtrl.loadSpiner(false);
			appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o Dep T.I");
			window.location.href = "#home";
			
	})};
		
		self.createJson = function () {
			$http({
				method : 'POST',
				url : "api/orgaoMovimentacao", 
				headers:{
					'Content-Type' : 'application/json; charset=utf-8'
				},
				data: JSON.stringify(self.orgao)
			})
			.done(function(response) {
				window.location.href = '#orgao-list';
			})
			.fail(function(response) {
			console.log("FALHA");
			window.location.href='#orgao-list';
			})
			.always(function() {
			console.log( "Concluído" );
			});
			
//			$.post( "api/orgaoMovimentacao", { orgao: "" + JSON.stringify(self.orgao) + "" } , function(response) {
//				console.log( "success" );
//				
//			})
//			.done(function(response) {
//					window.location.href = '#orgao-list';
//			})
//			.fail(function(response) {
//				console.log("FALHA");
//				window.location.href='#orgao-list';
//			})
//			.always(function() {
//				console.log( "Concluído" );
//			});
//			
		}
		
		self.create = function(){
			console.log(self.orgao);
			
		$.post( "api/orgaoMovimentacao", self.orgao).done( function(returnOfRequest) {
			//On Finish
			}).done(function(returnOfRequest) {
				var msg = "Orgao movimentacao atualizado com sucesso!";
				appCtrl.showNotification("Orgao Movimentacao inserido", msg, "success");
				location.href='#orgao-list';
			}).fail(function(returnOfRequest) {
				var msg = "Houve um erro inesperdo, se o problema persistir entre em contrato com o Dep. de T.I";
				appCtrl.showNotification("ERROR", msg, "danger");
				location.href='#orgao-list';
			}).always(function() {
			
			});
			
//				var data = self.orgao.dataMovimentacao;
//			
//				var from = data.split("/")
//				var ano_aniversario= from[2];
//				var mes_aniversario= from[1];
//				var dia_aniversario= from[0];
//			
//				self.orgao.dataMovimentacao= +ano_aniversario+"-"+mes_aniversario+"-"+dia_aniversario;
//				
//				
//				console.log(self.orgao.dataMovimentacao);
//				console.log(self.orgao);
//				
//						
//		$http({
//			method : 'POST',
//			url : "api/orgaoMovimentacao", 
//			headers:{
//				'Content-Type' : 'application/json; charset=utf-8'
//			},
//			data: JSON.stringify(self.artist)
//		}).then(function successCallback(response) {
//			// this callback will be called asynchronously
//			// when the response is available
//			console.log("Birrr");
//		}, function errorCallback(response) {
//			// called asynchronously if an error occurs
//			// or server returns response with an error status.
//		});
//		
	}
		
		
}]);