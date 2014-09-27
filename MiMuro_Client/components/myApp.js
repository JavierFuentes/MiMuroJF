/**
 * Created by javierfuentes on 13/06/14.
 */

angular
    .module('myApp', ['ngRoute', 'yaru22.angular-timeago'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/login',
            {
                templateUrl: './login/login.html',
                controller: 'loginCtrl'
            })
            .when('/status/list',
            {
                templateUrl: './mimuro/status/statuslist.html',
                controller: 'statusListCtrl'
            })
            .when('/status/:id',
            {
                templateUrl: './mimuro/status/statusdetail.html',
                controller: 'statusDetailCtrl'
            })
            .when('/user/:id',
            {
                templateUrl: './mimuro/status/statuslist.html',
                controller: 'statusListCtrl'
            })
            .otherwise(
            {
                redirectTo: '/login'
            })
    });
