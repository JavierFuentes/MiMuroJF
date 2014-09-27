/**
 * Created by javierfuentes on 20/06/14.
 */

angular
    .module('myApp')
    .controller('statusDetailCtrl', function ($scope, statusServices, $rootScope, $location, $routeParams) {

        $scope.logOut = function () {
            statusServices.logOut();
        };

        $scope.getLocaleDateString = function (value) {
            return myUtils.getLocaleDateString(value);
        };

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
//                    $scope.getAllStatusDetail();

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

        $scope.newCommentOK = function () {
            if (!$scope.newCommentText)
                return false;

            if (($scope.newCommentText.length > 0) &&
                ($scope.newCommentText.charAt($scope.newCommentText.length - 1) === '.') &&
                ($scope.newCommentText.indexOf(' ') > -1))
                return true;

            return false;
        };

        $scope.addLike = function (id) {
            console.log('Se ha llamado a la funcion addLike(' + id.toString() + ')');

            $scope.responseError = false;

            statusServices.addLike(id)
                .then(function () {
                    $scope.getAllStatusDetail();
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

        $scope.addComment = function () {
            console.log('Se ha llamado a la funcion addComment(' + $scope.newCommentText.toString() + ')');

            $scope.responseError = false;

            statusServices.addComment($scope.allJSONdata.id, $scope.newCommentText.toString())
                .then(function () {
                    $scope.getAllStatusDetail();
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
        $scope.getAllStatusDetail = function () {
            $scope.newCommentText = '';
            $scope.data_statusFiltered = '';
            $scope.data_commentsArray = [];
            $scope.data_likesArray = [];

            $scope.statusFiltered = '';
            if ($routeParams.id)
                $scope.statusFiltered = $routeParams.id;

            statusServices.getAllStatusDetail($routeParams.id)
                .then(function (Response) {
                    $scope.cred_nick = sessionStorage.cred_nick;

                    $scope.allJSONdata = Response.data;
                    $scope.data_statusFiltered = Response.data.id;
                    $scope.data_commentsArray = Response.data.commentList;
                    $scope.data_likesArray = Response.data.likeEntryList;

                    console.log($scope.allJSONdata);
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
        $scope.getAllStatusDetail();  // Carga de datos.

    });

