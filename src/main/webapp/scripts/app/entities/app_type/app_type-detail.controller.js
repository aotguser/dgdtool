'use strict';

angular.module('dgdtoolApp')
    .controller('App_typeDetailController', function ($scope, $rootScope, $stateParams, entity, App_type) {
        $scope.app_type = entity;
        $scope.load = function (id) {
            App_type.get({id: id}, function(result) {
                $scope.app_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:app_typeUpdate', function(event, result) {
            $scope.app_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
