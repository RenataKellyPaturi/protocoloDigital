'use strict';
app.controller('RequerenteUpdateController', ['$http', '$location', '$scope', '$routeParams', function($http, $location, $scope, $routeParams) {

		var self = this;
		var appCtrl = $scope.appCtrl;
		var idRequerente = $routeParams.idRequerente;
		$scope.page = 'requerente-update';
			
		$scope.onLoadHtmlFileInNgView = function () {
			appCtrl.carregarUsuarioLogado();
			self.listSecretariasIds();
		}
		
		
		self.carregarRequerente = function (){
			
			appCtrl.loadSpiner(true);
			

			$http.get( "api/requerente/" + $routeParams.idRequerente + "?radom=" + Math.random() ).then(function(response) {
				
				appCtrl.loadSpiner(false);
				self.requerente= response.data;
				
				if(response.data.cpf != null) {
					self.selecionadocpf = true;
					console.log("chegou com cpf");
				} else if(response.data.cnpj != null) {
					self.selecionadocnpj = true;
					console.log("chegou com cnpj");
				}
				
				console.log(response.data);
	    		//	appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>ALTERADO</span> com sucesso.");
				
			}, function (error) {
				
				appCtrl.loadSpiner(false);
				appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o Dep T.I");
				window.location.href = "#home";
				
			})};

		// atualizando requerente
			self.editarRequerente = function (){
				
				self.requerente.id = idRequerente;
				
				var config = {
						headers:  {
							'Content-Type': 'application/x-www-form-urlencoded'
						}
			};
				
				$http.put("api/requerente", $.param({ id: self.requerente.id, nome: self.requerente.nome}), config).then(function (response) {
					appCtrl.loadSpiner(false);
					appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>Editar</span> com sucesso.");
					window.location.href = "#requerente-list";
					
				}, function (error) {
					
					appCtrl.loadSpiner(false);
					appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o T.I");
					window.location.href = "#requerente-list";
					
				});
				
			}
			
			self.listSecretariasIds= function (){
				
				appCtrl.loadSpiner(true);
				
				$http.get( "api/secretaria/" + "?radom=" + Math.random() ).then(function(response) {
					
					appCtrl.loadSpiner(false);
					self.secretarias = response.data;
					self.carregarRequerente();
		    			appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>ALTERADO</span> com sucesso.");
		    			
				}, function (error) {
					
					appCtrl.loadSpiner(false);
					appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o Dep T.I");
					window.location.href = "#home";
					
				})};
				
		
				self.atualizarCadastro = function () {
					
					console.log( "Carregando..." );	
				
					$.post( "api/requerente/" + $routeParams.idRequerente, { requerente: "" + JSON.stringify(self.requerente) + "" } , function(response) {
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