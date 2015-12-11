'use strict';

angular.module('dgdtoolApp')
    .controller('EnvironmentDetailController', function ($scope, $rootScope, $stateParams, entity, Environment) {
        $scope.environment = entity;
        $scope.load = function (id) {
            Environment.get({id: id}, function(result) {
                $scope.environment = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:environmentUpdate', function(event, result) {
            $scope.environment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
