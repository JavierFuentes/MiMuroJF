/**
 * Created by javierfuentes on 20/06/14.
 */

angular
    .module('myApp')
    .controller('loginCtrl', function ($scope, loginServices, $location, $rootScope) {
        // Inicialización de valores para los textbox
        $scope.credentials = {
            data_mail: 'anonimo@mimuro.com',
            data_nick: 'Anonimo',
            data_pass: 'pass'
        };

        if (sessionStorage.cred_nick)
            delete sessionStorage.cred_nick;

        $scope.loginMessage = '';
        $scope.loginError = false;
        $scope.isLoading = false;

        $scope.loginSubmit = function () {

            if ($scope.newUserMode === false) {
                $scope.loginMessage = 'Validando credenciales.';
            } else {
                $scope.loginMessage = 'Registrando usuario.';
            }

            $scope.loginError = false;
            $scope.isLoading = true;

            // Delegamos en el servicio la comprobación del Login
            loginServices.loginSubmit($scope.credentials, $scope.newUserMode)
                .then(function (Response) {
                    // Hacemos que las credenciales sean visibles por el resto de Controladores.
                    // Parece que $rootScope no funciona bien cuando haces F5
                    sessionStorage.cred_nick = Response.data.nick;

                    $location.path('/status/list');        // Requiere inyectar $location al controlador
                    //window.location = "./mimuro/statuslist.html";   // NO Requiere inyectar $location al controlador
                })
                .catch(function (Response) {
                    $scope.loginMessage = httpErrorManagement.getFormatedMsg(Response);
                    $scope.loginError = true;
                })
                .finally(function () {
                    $scope.isLoading = false;
                });
        };

        $scope.setNewUserMode = function () {
            $scope.newUserMode = !$scope.newUserMode;
        };

    });

