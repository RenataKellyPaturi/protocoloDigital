'use strict';
app.controller('OrgaoUpdateController', ['$http', '$location', '$scope', '$routeParams', function($http, $location, $scope, $routeParams) {

		var self = this;
		var appCtrl = $scope.appCtrl;
		var idOrgao = $routeParams.idOrgao;
		$scope.page = 'orgao-update';
			
		$scope.onLoadHtmlFileInNgView = function () {
			appCtrl.carregarUsuarioLogado();
			self.carregarOrgao();
			self.listFichaIds();

		}
		
		// lista de orgao movimentacao
		self.carregarOrgao = function (){
			appCtrl.loadSpiner(true);
			

			$http.get( "api/orgaoMovimentacao/" + $routeParams.idOrgao + "?radom=" + Math.random() ).then(function(response) {
				
				appCtrl.loadSpiner(false);
				self.orgao = response.data;
	    			appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>ALTERADO</span> com sucesso.");
				
			}, function (error) {
				
				appCtrl.loadSpiner(false);
				appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o Dep T.I");
				window.location.href = "#home";
				
			})};
			
			self.listFichaIds= function (){
				
				appCtrl.loadSpiner(true);
				
				$http.get( "api/processo/get-fichas-for-status/true" + "?radom=" + Math.random() ).then(function(response) {
					
					appCtrl.loadSpiner(false);
					self.processos = response.data;
		    			appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>ALTERADO</span> com sucesso.");
					
				}, function (error) {
					
					appCtrl.loadSpiner(false);
					appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o Dep T.I");
					window.location.href = "#home";
					
			})};

			// orgao movimentacao
			self.editarOrgao = function (){
				
				self.orgao.id = idOrgao;
				
				var config = {
						headers:  {
							'Content-Type': 'application/x-www-form-urlencoded'
						}
			};
				
				$http.put("api/orgaoMovimentacao", $.param({ id: self.orgao.id, origemEnviada: self.orgao.origemEnviada,dataMovimentacao: self.orgao.dataMovimentacao , processo: self.orgao.processo}), config).then(function (response) {
					appCtrl.loadSpiner(false);
					appCtrl.loadSnackbar("Grupo <span style='color:#00ff18;'>Editar</span> com sucesso.");
					window.location.href = "#orgao-list";
	
				}, function (error) {
					
					appCtrl.loadSpiner(false);
					appCtrl.loadSnackbar("<span style='color:#ff0000;'>FALHA.</span> Entre em contato com o N.I.T. PMC");
					window.location.href = "#orgao-list";
					
				});
				
			}

		}]);