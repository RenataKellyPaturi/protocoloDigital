'use strict';
app.controller('SecretariaUpdateController', ['$http', '$location', '$scope', '$routeParams', function($http, $location, $scope, $routeParams) {

		var self = this;
		var appCtrl = $scope.appCtrl;
		var idSecretaria = $routeParams.idSecretaria;
		$scope.page = 'secretaria-update';
			
		$scope.onLoadHtmlFileInNgView = function () {
			appCtrl.carregarUsuarioLogado();
			self.carregarSecretaria();
		}
		
		// 
		self.carregarSecretaria = function (){
			
			appCtrl.loadSpiner(true);
			

			$http.get( "api/secretaria/" + $routeParams.idSecretaria + "?radom=" + Math.random() ).then(function(response) {
				
				appCtrl.loadSpiner(false);
				self.secretaria = response.data;
	    			appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>ALTERADO</span> com sucesso.");
				
			}, function (error) {
				
				appCtrl.loadSpiner(false);
				appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o Dep T.I");
				window.location.href = "#home";
				
			})};

		// atualizando secretaria
			self.editarSecretaria = function (){
				
				self.secretaria.id = idSecretaria;
				
				var config = {
						headers:  {
							'Content-Type': 'application/x-www-form-urlencoded'
						}
			};
				
				$http.put("api/secretaria", $.param({ id: self.secretaria.id, nome: self.secretaria.nome}), config).then(function (response) {
					appCtrl.loadSpiner(false);
					appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>Editar</span> com sucesso.");
					window.location.href = "#secretaria-list";
					
				}, function (error) {
					
					appCtrl.loadSpiner(false);
					appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o N.I.T. pmc");
					window.location.href = "#secretaria-list";
					
				});
				
			}
		
		// atualizando secretaria
			self.updateSecretaria = function (){
				
				appCtrl.loadSpiner(true);
				
				
				$.post("api/secretaria/update", self.secretaria ).done( function(returnOfRequest) {
				//On Finish
				}).done(function(returnOfRequest) {
					
					appCtrl.loadSpiner(false);
					
					var msg = "Secretaria atualizado com sucesso!";
					appCtrl.showNotification("Secretaria Atualizado", msg, "success");

					window.location.href = "#secretaria-list";

				}).fail(function() {

					var msg = "Houve um erro inesperdo, se o problema persistir entre em contrato com o Dep. de T.I";
					appCtrl.showNotification("ERROR", msg, "danger");
					window.location.href = "#secretaria-list";
				
				}).always(function() {
				
				});

			}
		}]);