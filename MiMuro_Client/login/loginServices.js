/**
 * Created by javierfuentes on 20/06/14.
 */

angular
    .module('myApp')
    .factory('loginServices', function ($q, $http, $timeout) {
        return {
            loginSubmit: function (credentials, newUser) {

////////////////// Pruebas de envío y recepción de JSON complejos
//                credentials.data = {
//                    name : 'fakeName',
//                    document : {
//                        number: '11222333',
//                        letter: 'N'
//                    },
//                    array: [{first:1, second:2}, {first:11, second:22}, {first:111, second:222}]
//                };
////////////////// fin. Pruebas

                //$http.post ya devuelve una promesa directamente (no hace falta usar $q
                if (newUser) {
                    return $http.post(appServerLocation.url + '/services/users/add', credentials);
                } else {
                    return $http.post(appServerLocation.url + '/services/users/check', credentials);
                }


                // Simulamos un retardo de 2 seg. en la validación de las credenciales
//                $timeout(function () {
//                    if ((credentials.data_mail !== 'admin@admin.com') ||
//                        (credentials.data_pass !== 'admin')) {
//
//                        deferred.reject();
//                        return;
//                    }
//
//                    deferred.resolve();
//                }, 2000);
            }
        }
    });