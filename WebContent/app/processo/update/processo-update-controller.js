'use strict';
app.controller('ProcessoUpdateController', ['$http', '$location', '$scope', '$routeParams', function($http, $location, $scope, $routeParams) {

		var self = this;
		var appCtrl = $scope.appCtrl;
		var idProcesso = $routeParams.idProcesso;
		$scope.page = 'processo-update';
			
			
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

		// atualizando processo
			self.update = function (){
				
				self.processo.id = idProcesso;
				
				var config = {
						headers:  {
							'Content-Type': 'application/x-www-form-urlencoded'
						}
			};
				
				$http.put("api/processo", $.param({ id: self.processo.id, numeroProcesso: self.processo.numeroProcesso, dataProcesso: self.processo.dataProcesso , documentos: self.processo.documentos}), config).then(function (response) {
					appCtrl.loadSpiner(false);
					appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>Editar</span> com sucesso.");
					window.location.href = "#processo-list";
					
				}, function (error) {
					
					appCtrl.loadSpiner(false);
					appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o T.I");
					window.location.href = "#processo-list";
					
				});
				
			}
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
					
					
					self.atualizarCadastro = function () {
						
						console.log( "Carregando..." );	
						
						var data = self.processo.dataProcesso;
						
						var from = data.split("/")
						var ano_aniversario= from[2];
						var mes_aniversario= from[1];
						var dia_aniversario= from[0];
						
						self.processo.dataProcesso = +ano_aniversario+"-"+mes_aniversario+"-"+dia_aniversario+"T14:42:14Z";
						console.log(self.processo.dataProcesso);
						$.post( "api/processo/" + idProcesso, { processo: "" + JSON.stringify(self.processo) + "" } , function(response) {
							console.log( "success" );
						})
						.done(function(response) {
								window.location.href = '#processo-list';
						})
						.fail(function(response) {
							console.log(response);
							console.log("FALHA");
							window.location.href='#processo-list';
						})
						.always(function() {
							console.log( " Execução Concluída" );
						});
						
					}
		}]);