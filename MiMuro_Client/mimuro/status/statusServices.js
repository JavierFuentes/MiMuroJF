/**
 * Created by javierfuentes on 29/08/14.
 */

angular
    .module('myApp')
    .factory('statusServices', function ($q, $http, $location) {
        return {

            logOut: function () {
                $location.path('/login');
            },

            addLike: function(statusId) {
                // Si no se ha hecho Login, no enviamos datos del servidor
                if (!sessionStorage.cred_nick)
                    return $q.reject();

                var JSONData = {
                    "data_nick": sessionStorage.cred_nick,
                    "data_statusId": statusId
                };

                return $http.post(appServerLocation.url + '/services/likes/add', JSONData)
            },

            addStatus: function(newStatusText) {
                // Si no se ha hecho Login, no enviamos datos del servidor
                if (!sessionStorage.cred_nick)
                    return $q.reject();

                var JSONData = {
                    "data_nick": sessionStorage.cred_nick,
                    "data_text": newStatusText
                };

                return $http.post(appServerLocation.url + '/services/status/add', JSONData);
            },

            addComment: function(statusId, newCommentText) {
                // Si no se ha hecho Login, no enviamos datos del servidor
                if (!sessionStorage.cred_nick)
                    return $q.reject();

                var JSONData = {
                    "data_nick": sessionStorage.cred_nick,
                    "data_statusId": statusId,
                    "data_text": newCommentText
                };

                return $http.post(appServerLocation.url + '/services/comments/add', JSONData);
            },

            getAllStatusList: function (nick) {
                // Si no se ha hecho Login, no devolvemos datos del servidor
                if (!sessionStorage.cred_nick)
                    return $q.reject();

                if (!nick) {
                    return $http.get(appServerLocation.url + '/services/status/list');
                } else {
                    return $http.get(appServerLocation.url + '/services/status/user/' + nick.toString());
                }
            },

            getAllStatusDetail: function (statusId) {
                // Si no se ha hecho Login, no devolvemos datos del servidor
                if (!sessionStorage.cred_nick)
                    return $q.reject();

                if (statusId) {
                    return $http.get(appServerLocation.url + '/services/status/' + statusId.toString());
                } else {
                    return $q.reject();
                }
            }
        }
    })
;