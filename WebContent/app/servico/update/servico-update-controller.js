'use strict';
app.controller('ServicoUpdateController', ['$http', '$location', '$scope', '$routeParams', function($http, $location, $scope, $routeParams) {

		var self = this;
		var appCtrl = $scope.appCtrl;
		var idServico = $routeParams.idServico;
		$scope.page = 'servico-update';
			
		$scope.onLoadHtmlFileInNgView = function () {
			appCtrl.carregarUsuarioLogado();
			self.carregarServico();
		}
		
		// 
		self.carregarServico = function (){
			
			appCtrl.loadSpiner(true);
			

			$http.get( "api/servico/" + $routeParams.idServico + "?radom=" + Math.random() ).then(function(response) {
				
				appCtrl.loadSpiner(false);
				self.servico = response.data;
	    			appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>ALTERADO</span> com sucesso.");
				
			}, function (error) {
				
				appCtrl.loadSpiner(false);
				appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o Dep T.I");
				window.location.href = "#home";
				
			})};

		// atualizando servico
			self.editarServico = function (){
				
				self.servico.id = idServico;
				
				var config = {
						headers:  {
							'Content-Type': 'application/x-www-form-urlencoded'
						}
			};
				
				$http.put("api/servico", $.param({ id: self.servico.id, nome: self.servico.nome, isServicoUrb: self.servico.isServicoUrb , valorServico: self.servico.valor}), config).then(function (response) {
					appCtrl.loadSpiner(false);
					appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>Editar</span> com sucesso.");
					window.location.href = "#servico-list";
					
				}, function (error) {
					
					appCtrl.loadSpiner(false);
					appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o T.I");
					window.location.href = "#servico-list";
					
				});
				
			}
		
		// atualizando servico
			self.updateServico= function (){
				
				appCtrl.loadSpiner(true);
				
				$.post("api/servico/update", self.servico ).done( function(returnOfRequest) {
				//On Finish
				}).done(function(returnOfRequest) {
					
					appCtrl.loadSpiner(false);
					
					var msg = "Servico atualizado com sucesso!";
					appCtrl.showNotification("Servico Atualizado", msg, "success");

					window.location.href = "#servico-list";

				}).fail(function() {

					var msg = "Houve um erro inesperdo, se o problema persistir entre em contrato com o Dep. de T.I";
					appCtrl.showNotification("ERROR", msg, "danger");
					window.location.href = "#servico-list";
				
				}).always(function() {
				
				});

			}
		}]);