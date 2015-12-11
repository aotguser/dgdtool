'use strict';

angular.module('dgdtoolApp')
    .controller('Server_roleDetailController', function ($scope, $rootScope, $stateParams, entity, Server_role) {
        $scope.server_role = entity;
        $scope.load = function (id) {
            Server_role.get({id: id}, function(result) {
                $scope.server_role = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:server_roleUpdate', function(event, result) {
            $scope.server_role = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
