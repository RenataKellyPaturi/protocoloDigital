'use strict';
app.controller('RequerimentoUpdateController', ['$http', '$location', '$scope', '$routeParams', function($http, $location, $scope, $routeParams) {

		var self = this;
		var appCtrl = $scope.appCtrl;
		var idRequerimento = $routeParams.idRequerimento;
		$scope.page = 'requerimento-update';
			
		$scope.onLoadHtmlFileInNgView = function () {
			appCtrl.carregarUsuarioLogado();
			self.carregarFolhaRequerimento();
			self.listSercicosIds();
			self.listRequerenteIds();
		}
		
		
		self.carregarFolhaRequerimento = function (){
			
			appCtrl.loadSpiner(true);
			

			$http.get( "api/folha_requerimento/" + idRequerimento + "?radom=" + Math.random() ).then(function(response) {
				
				appCtrl.loadSpiner(false);
				self.requerimento= response.data;
				
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
			
			self.listSercicosIds= function (){
				
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

				self.listRequerenteIds= function (){
					
					appCtrl.loadSpiner(true);
					
					$http.get( "api/requerente/get-requerentes-for-status/true" + "?radom=" + Math.random() ).then(function(response) {
						
						appCtrl.loadSpiner(false);
						self.requerentes = response.data;
			    			appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>ALTERADO</span> com sucesso.");
						
					}, function (error) {
						
						appCtrl.loadSpiner(false);
						appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o Dep T.I");
						window.location.href = "#home";
						
					})};
				
		
				self.update = function () {
					
					var dataRequerimento = self.requerimento.dataRequerimento;
					
					var from = dataRequerimento.split("/")
					var ano_aniversario= from[2];
					var mes_aniversario= from[1];
					var dia_aniversario= from[0];
					self.requerimento.dataRequerimento = +ano_aniversario+"-"+mes_aniversario+"-"+dia_aniversario+"T13:42:14Z";
					
					var data = self.requerimento.dataNumeroProtocolo;
					var from = data.split("/")
					var ano_atual= from[2];
					var mes_atual= from[1];
					var dia_atual= from[0];
					
					self.requerimento.dataNumeroProtocolo = +dia_atual+"-"+mes_atual+"-"+ano_atual+"T12:42:17Z";


//					self.requerimento.dataRequerimento = $("#dataRequerimento").val();
//					self.requerimento.dataRequerimento=+"T13:42:14Z";
//					
//					self.requerimento.dataNumeroProtocolo = $("#dataProtocolo").val();
//					self.requerimento.dataNumeroProtocolo=+"T13:42:14Z";
					
					var valorTaxaTotal = $("#valorTotal").val();
					valorTaxaTotal.replace(".",",");
					self.requerimento.valorTaxaTotal = parseFloat(valorTaxaTotal);
				
					$.post( "api/folha_requerimento/" + $routeParams.idRequerimento, { requerimento: "" + JSON.stringify(self.requerimento) + "" } , function(response) {
						console.log( "success" );
					})
					.done(function(response) {
							window.location.href = '#requerimento-list';
					})
					.fail(function(response) {
						console.log(response);
						console.log("FALHA");
						window.location.href='#requerimento-list';
					})
					.always(function() {
						console.log( "Concluído" );
					});
					
				}
				
				
			
		}]);