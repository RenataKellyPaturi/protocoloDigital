'use strict';

app.config(function($routeProvider) {
	
	$routeProvider
	
		.when('/login', {
			templateUrl: 'app/login/login-view.html',
			controller: 'LoginController as loginController'
		})
		.when('/home', {
			templateUrl: 'app/home/home-view.html',
			controller: 'HomeController as homeController'
		})
		.when('/secretaria-new', {
			templateUrl: 'app/secretaria/create/secretaria-create-view.html',
			controller: 'SecretariaCreateController as secretariaCreateController'
		})
		.when('/secretaria-update/:idSecretaria', {
			templateUrl: 'app/secretaria/update/secretaria-update-view.html',
			controller: 'SecretariaUpdateController as secretariaUpdateController'
		})
		.when('/secretaria-list', {
			templateUrl: 'app/secretaria/list/secretaria-list-view.html',
			controller: 'SecretariaListController as secretariaListController'
		})
		.when('/servico-new', {
			templateUrl: 'app/servico/create/servico-create-view.html',
			controller: 'ServicoCreateController as servicoCreateController'
		})
		.when('/servico-update/:idServico', {
			templateUrl: 'app/servico/update/servico-update-view.html',
			controller: 'ServicoUpdateController as servicoUpdateController'
		})
		.when('/servico-list', {
			templateUrl: 'app/servico/list/servico-list-view.html',
			controller: 'ServicoListController as servicoListController'
		})
		.when('/orgao-new', {
			templateUrl: 'app/orgaoMovimentacao/create/orgao-create-view.html',
			controller: 'OrgaoCreateController as orgaoCreateController'
		})
		.when('/orgao-update/:idOrgao', {
			templateUrl: 'app/orgaoMovimentacao/update/orgao-update-view.html',
			controller: 'OrgaoUpdateController as orgaoUpdateController'
		})
		.when('/orgao-list', {
			templateUrl: 'app/orgaoMovimentacao/list/orgao-list-view.html',
			controller: 'OrgaoListController as orgaoListController'
		})
		.when('/requerente-list', {
			templateUrl: 'app/requerente/list/requerente-list-view.html',
			controller: 'RequerenteListController as requerenteListController'
		})
		.when('/requerente-new', {
			templateUrl: 'app/requerente/create/requerente-create-view.html',
			controller: 'RequerenteCreateController as requerenteCreateController'
		})
		.when('/requerente-update/:idRequerente', {
			templateUrl: 'app/requerente/update/requerente-update-view.html',
			controller: 'RequerenteUpdateController as requerenteUpdateController'
		})
		.when('/processo-list', {
			templateUrl: 'app/processo/list/processo-list-view.html',
			controller: 'ProcessoListController as processoListController'
		})
		.when('/processo-new', {
			templateUrl: 'app/processo/create/processo-create-view.html',
			controller: 'ProcessoCreateController as processoCreateController'
		})
		.when('/processo-update/:idProcesso', {
			templateUrl: 'app/processo/update/processo-update-view.html',
			controller: 'ProcessoUpdateController as processoUpdateController'
		})
		.when('/processo-print/:idProcesso', {
			templateUrl: 'app/processo/print/processo-print-view.html',
			controller: 'ProcessoPrintController as processoPrintController'
		})
		.when('/requerimento-new', {
			templateUrl: 'app/ficha-requerimento/create/requerimento-create-view.html',
			controller: 'RequerimentoCreateController as requerimentoCreateController'
		})
		.when('/requerimento-update/:idRequerimento', {
			templateUrl: 'app/ficha-requerimento/update/requerimento-update-view.html',
			controller: 'RequerimentoUpdateController as requerimentoUpdateController'
		})
		.when('/requerimento-list', {
			templateUrl: 'app/ficha-requerimento/list/requerimento-list-view.html',
			controller: 'RequerimentoListController as requerimentoListController'
		})
		.when('/usuario-list', {
			templateUrl: 'app/usuarios/list/usuario-list-view.html',
			controller: 'UsuarioListController as usuarioListController'
		})
		.otherwise({
			redirectTo: 'home'
		})

});
