'use strict';
app.run(function($rootScope, $location, loginService){

	var paginasDoAdministrador = [];
		paginasDoAdministrador.push('/home');
		paginasDoAdministrador.push('/minhas-propostas');

	$rootScope.$on('$routeChangeStart', function(){
		
		// if( $location.path() == '/login' ){
		// 	loginService.logout();
		// }else if( $location.path().indexOf( '/avaliar-proposta' ) == 0 ){
		// 	//Para avaliação de proposta com token sem login
		// }else{

		// 	//Capturar o Perfil
		// 	loginService.loggedUser().then(function(response) {

		// 		if( response.profile == "CoordenadorDeEventos" ){

		// 			if( ! ( paginasDoCoordenadorDeEventos.indexOf($location.path() ) != -1 ) ){
		// 				window.location.href = "#login";
		// 			}

		// 		}else if( response.profile == "Administrador" ){

		// 			if( ! ( paginasDoAdministrador.indexOf($location.path() ) != -1 ) ){
		// 				window.location.href = "#login";
		// 			}

		// 		}else if( response.profile == "Professor" ){
					
		// 			var _autorizada = false;
					
		// 			for (var int = 0; int < paginasDoProfessor.length; int++) {
		// 				if( $location.path().indexOf(paginasDoProfessor[int]) != -1 ){
		// 					_autorizada = true;
		// 					break;
		// 				}
		// 			}
					
		// 			if(!_autorizada){
		// 				window.location.href = "#login";
		// 			}

		// 		}else{
		// 			window.location.href = "#login";
		// 		}

		// 	}, function(response) {
		// 		window.location.href = "#login";
		// 	});

		// }

	});

});