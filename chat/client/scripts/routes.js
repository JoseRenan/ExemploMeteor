angular.module('app').config(function ($stateProvider, $urlRouterProvider) {
	
	$urlRouterProvider.otherwise('/main');

	$stateProvider
		.state('main', {
		    url: '/main',
		    templateUrl: 'client/templates/home.html',
		    controller: 'postCtrl',
		    controllerAs: 'teste'
	  	});

});