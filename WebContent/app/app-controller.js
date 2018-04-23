'use strict';

/**
 * Principal Controller da
 * aplicação.
 * 
 */

app.controller('AppCtrl', ['$http', '$location', '$scope', function($http, $location, $scope) {
	
	
	var self = this;

	/*
	 * COLLAPSE MENU
	 * 
	 * */

	self.collapseSideBar = false;

	self.callCollapseSideBar = function () {
		if(self.collapseSideBar){
			self.collapseSideBar = false;
		}else{
			self.collapseSideBar = true;
		}
	}
	
	/*
	 * VERIFICAR USUÁRIO LOGADO
	 * 
	 * */
	self.carregarUsuarioLogado = function () {
		
		self.loadSpiner(true);

		$http.get( "api/login/get-user-loged" + "?radom=" + Math.random() ).then(function(response) {

			self.loadSpiner(false);
			self.usarioLogado = response.data;
		}, function(response) {

			//FALHA
			self.loadSpiner(false);
			window.location.href = '#login';
		});

	}
	// ../VERIFICAR USUÁRIO LOGADO ----------------------------------------------------------------------------------------
	
	/*
	 * EFETUAR LOGIN
	 * 
	 * */
	self.login = function ( user ) {
		
		self.loadSpiner(true);

		$.post( "api/login", user  ).done( function(returnOfRequest) {
		//On Finish
		}).done(function(returnOfRequest) {
			window.location.href = '#home';
		}).fail(function(returnOfRequest) {
			window.location.href = '#login';
		}).always(function() {
		
		});

	}
	// ../EFETUAR LOGIN --------------------------------------------------------------------------------------------------
	
	/*
	 * EFETUAR LOGOUT
	 * 
	 * */
	self.logOut = function () {
		
		self.loadSpiner(true);

		var logoutUrl = "../api/login/logout";

		$.get( logoutUrl ).done( function(returnOfRequest) {

		}).done(function(returnOfRequest) {

			self.loadSpiner(false);
			//window.location.href = '../index.html';
			window.location.href = '#login';
		}).fail(function(returnOfRequest) {

			//FALHA
			self.loadSpiner(false);
//			window.location.href = '../index.html';
			window.location.href = '#login';
		}).always(function() {

		});
		
	}
	// ../EFETUAR LOGOUT --------------------------------------------------------------------------------------------------
	
	// .. /MOSTRA NOTIFICACOES DE STATUS 
 self.showNotification = function (title, msg, type) {
		 
		 var notificacoesPresentes = $('.ui-pnotify');
		 
		 var espacamentoDeTopoDaProximaNotificacao = 25;
		 
		 for (var int = 0; int < notificacoesPresentes.length; int++) {
			 espacamentoDeTopoDaProximaNotificacao += notificacoesPresentes[int].clientHeight;
			 espacamentoDeTopoDaProximaNotificacao += 10;
		 }
		 
		 var div = document.createElement("div");
		 
		 	$(div).attr("class","ui-pnotify");
		 	$(div).attr("style","width: 300px; right: 25px; top: "+espacamentoDeTopoDaProximaNotificacao+"px; opacity: 1; display: block; overflow: visible; cursor: auto;");
		 	
		 	$(div).append('<div class="alert ui-pnotify-container alert-'+type+' ui-pnotify-shadow" style="min-height: 16px; overflow: hidden;">'+
				      '<div class="ui-pnotify-icon"><span class="fa fa-info"></span></div>'+
				      '<h4 class="ui-pnotify-title">'+title+'</h4>'+
				      '<div class="ui-pnotify-text">'+msg+'</div>'+
				      '<div style="margin-top: 5px; clear: both; text-align: right; display: none;"></div>'+
				   '</div>');
		 
		 	$('body').append(div);
		
		 setTimeout(
				 function(){
					 var notificacoesPresentes = $('.ui-pnotify');
					 
					 $(div).remove();
					 
					 var espacamentoDeTopoDaProximaNotificacao = 25;
					 
					 if(notificacoesPresentes.length > 1){
						 for (var int = 1; int < notificacoesPresentes.length; int++) {
							 $(notificacoesPresentes[int]).attr("style","width: 300px; right: 25px; top: "+espacamentoDeTopoDaProximaNotificacao+"px; opacity: 1; display: block; overflow: visible; cursor: auto;");
							 espacamentoDeTopoDaProximaNotificacao += notificacoesPresentes[int].clientHeight;
							 espacamentoDeTopoDaProximaNotificacao += 10;
						}
					 }
				 }, 6000);
		 
	}
 // ---  // .. / FIM MOSTRA NOTIFICACOES DE STATUS a--
	/*
	 * SPINER
	 * 
	 * */
	var myTime;
	self.loadSpiner = function (param) {
		if( param ){
			$("#loadContentSpninner").addClass('active');
		}else{
			$("#loadContentSpninner").removeClass('active');
		}
	}
	// ../SPINER --------------------------------------------------------------------------------------------------

	/*
	 * SNACKBAR
	 * 
	 * */
	var time;
	self.loadSnackbar = function (msg) {
		$("#snackbar").html(msg);
		$("#snackbar").addClass('active');
		myTime = setTimeout(hideSnackbar, 5000);
	}
	function hideSnackbar() {
		$("#snackbar").removeClass('active');
	}
	// ../SNACKBAR --------------------------------------------------------------------------------------------------

	/*
	 * GENERIC LISTS NAVIGATION
	 * 
	 * */
 	$scope.initialDataOfGenericList = null;
	$scope.currentPageOfGenericList = 0;
    $scope.dataOfGenericList = [];
    $scope.exibirRegistrosInativos = "false";
    $scope.pageSizeOfGenericList = "5";
    $scope.numberOfPagesOfGenericList=function(){
        return Math.ceil($scope.dataOfGenericList.length/$scope.pageSizeOfGenericList);                
    }
	// ../GENERIC LISTS NAVIGATION ---------------------------------------------------------------------------------

	/*
	 * FULL SCREEN
	 * 
	 * */
	 self.fullScreen = function () {
		if ((document.fullScreenElement && document.fullScreenElement !== null) || (!document.mozFullScreen && !document.webkitIsFullScreen)) {
			if (document.documentElement.requestFullScreen) {  
				document.documentElement.requestFullScreen();  
			} else if (document.documentElement.mozRequestFullScreen) {  
				document.documentElement.mozRequestFullScreen();  
			} else if (document.documentElement.webkitRequestFullScreen) {  
				document.documentElement.webkitRequestFullScreen(Element.ALLOW_KEYBOARD_INPUT);  
			}  
		} else {  
			if (document.cancelFullScreen) {  
				document.cancelFullScreen();  
			} else if (document.mozCancelFullScreen) {  
				document.mozCancelFullScreen();  
			} else if (document.webkitCancelFullScreen) {  
				document.webkitCancelFullScreen();  
			}  
		}  
	}
	// ../FULL SCREEN ---------------------------------------------------------------------------------

	/*
	 * TOOGLE CLASS OF ELEMENT
	 * 
	 * */
	 self.toggleClassOfElement = function (idElement, classForToggle) {

		$('#'+idElement).toggleClass(classForToggle);

	}
	// ../TOOGLE CLASS OF ELEMENT ---------------------------------------------------------------------------------


}]);