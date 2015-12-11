'use strict';

angular.module('dgdtoolApp')
    .controller('Server_typeDetailController', function ($scope, $rootScope, $stateParams, entity, Server_type) {
        $scope.server_type = entity;
        $scope.load = function (id) {
            Server_type.get({id: id}, function(result) {
                $scope.server_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:server_typeUpdate', function(event, result) {
            $scope.server_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
