'use strict';

angular.module('dgdtoolApp')
    .controller('PathDetailController', function ($scope, $rootScope, $stateParams, entity, Path) {
        $scope.path = entity;
        $scope.load = function (id) {
            Path.get({id: id}, function(result) {
                $scope.path = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:pathUpdate', function(event, result) {
            $scope.path = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
