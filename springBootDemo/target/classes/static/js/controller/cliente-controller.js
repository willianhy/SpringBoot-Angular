appCliente.controller("clienteController", function($scope, $http){
	$scope.nome = "Jão";
	$scope.clientes = [];
	$scope.cliente={};
	
	carregarClientes = function(){
		
		token = localStorage.getItem("userToken");
		//$http.defaults.headers.common.Authorization = 'Bearer ' + token;
		
		$http({method: 'GET', url: 'http://localhost:8080/admin/clientes'})
		.then(function(response){
			$scope.clientes = response.data;
			console.log(response.data);
			console.log(response.status);
		}, 
		function(response){
			console.log(response.data);
			console.log(response.status);	
		});
	};
	
	$scope.salvarCliente = function(){
		$http({method: 'POST', url: 'http://localhost:8080/admin/clientes', data: $scope.cliente})
		.then(function(response){
			carregarClientes();
			$scope.cliente={};
			$scope.frmCliente.$setPristine(true);
		}, 
		function(response){
			console.log(response.data);
			console.log(response.status);	
		});
	};
	
	$scope.excluirCliente = function(cliente){
		$http({method: 'DELETE', url: 'http://localhost:8080/admin/clientes/' + cliente.id})
		.then(function(response){
			var index = $scope.clientes.indexOf(cliente);
			if (index > -1)
				$scope.clientes.splice(index, 1);
			
		}, 
		function(response){
			console.log(response.data);
			console.log(response.status);	
		});
		
	};
	
	$scope.alterarCliente = function(cliente){
		$scope.cliente = angular.copy(cliente);
	};
	
	$scope.cancelarAlteracao = function(){
		$scope.cliente={};
	};
	
	carregarClientes();
});