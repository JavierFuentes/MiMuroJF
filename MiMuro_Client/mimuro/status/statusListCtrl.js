/**
 * Created by javierfuentes on 20/06/14.
 */

angular
    .module('myApp')
    .controller('statusListCtrl', function ($scope, statusServices, $rootScope, $location, $routeParams) {

        $scope.logOut = function () {
            statusServices.logOut();
        };

        $scope.getLocaleDateString = function (value) {
            return myUtils.getLocaleDateString(value);
        };

        $scope.setOrder = function () {
            $scope.checkOrder = !$scope.checkOrder;

            if ($scope.checkOrder) {
                $scope.orderCriteria = '-relevanceIndicator';
            } else {
                $scope.orderCriteria = '';
            }
        }

        $scope.getStatusByUser = function (nick) {
            console.log('Se ha llamado a la funcion getStatusByUser(' + nick.toString() + ')');

            $location.path('/user/' + nick);
        };

        $scope.goStatusDetail = function (id) {
            console.log('Se ha llamado a la funcion goStatusDetail(' + id.toString() + ')');

            $location.path('/status/' + id);
        };

       /* $scope.getLikesList = function (id) {
            console.log('Se ha llamado a la funcion getLikesList(' + id.toString() + ')');

            statusServices.getLikesList(id)
                .then(function (Response) {
                    debugger;
//                    $scope.getAllStatusList();

                    $scope.data_likesArray = Response.data;
                })
                .catch(function (Response) {
                    if ((Response) && (Response.status)) {
                        $scope.actionMessage = httpErrorManagement.getFormatedMsg(Response);
                    }
                })
                .finally(function () {

                })
        };

        $scope.ShowLikeEntries = function (statusId) {
            if (!$scope.data_likesArray[0])
                return false;

            if ($scope.data_likesArray[0].userStatus == statusId)
                return true;
        };*/

        $scope.newStatusOK = function () {
            if (!$scope.newStatusText)
                return false;

            if (($scope.newStatusText.length > 0) &&
                ($scope.newStatusText.charAt($scope.newStatusText.length - 1) === '.') &&
                ($scope.newStatusText.indexOf(' ') > -1))
                return true;

            return false;
        };

        $scope.addLike = function (id) {
            console.log('Se ha llamado a la funcion addLike(' + id.toString() + ')');

            $scope.responseError = false;

            statusServices.addLike(id)
                .then(function () {
                    $scope.getAllStatusList();
                })
                .catch(function (Response) {
                    if ((Response) && (Response.status)) {
                        $scope.actionMessage = httpErrorManagement.getFormatedMsg(Response);
                    }

                    $scope.responseError = true;
                })
                .finally(function () {

                })
        };

        $scope.addStatus = function () {
            console.log('Se ha llamado a la funcion addStatus(' + $scope.newStatusText.toString() + ')');

            $scope.responseError = false;

            statusServices.addStatus($scope.newStatusText.toString())
                .then(function () {
                    $scope.getAllStatusList();
                })
                .catch(function (Response) {
                    if ((Response) && (Response.status)) {
                        $scope.actionMessage = httpErrorManagement.getFormatedMsg(Response);
                    }

                    $scope.responseError = true;
                })
                .finally(function () {
//                    $scope.isLoading = false;
                });
        };

        // Delegamos en el servicio la obtención de la lista de Estados
        $scope.getAllStatusList = function () {
            $scope.newStatusText = '';
            $scope.data_likesArray = [];

            $scope.userFiltered = '';
            if ($routeParams.id)
                $scope.userFiltered = $routeParams.id;

            statusServices.getAllStatusList($routeParams.id)
                .then(function (Response) {
                    $scope.cred_nick = sessionStorage.cred_nick;
                    $scope.data_statusArray = Response.data;
                })
                .catch(function (Response) {
                    if ((Response) && (Response.status)) {
                        $scope.actionMessage = httpErrorManagement.getFormatedMsg(Response);
                    } else {
                        $location.path('/login')
                    }

                    $scope.responseError = true;

                })
                .finally(function () {
//                    $scope.isLoading = false;
                });
        };
        $scope.getAllStatusList();  // Carga de datos.

    });

