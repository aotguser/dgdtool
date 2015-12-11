'use strict';

angular.module('dgdtoolApp')
    .controller('RoleDetailController', function ($scope, $rootScope, $stateParams, entity, Role) {
        $scope.role = entity;
        $scope.load = function (id) {
            Role.get({id: id}, function(result) {
                $scope.role = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:roleUpdate', function(event, result) {
            $scope.role = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
