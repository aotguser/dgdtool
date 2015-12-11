'use strict';

angular.module('dgdtoolApp')
    .controller('Support_levelDetailController', function ($scope, $rootScope, $stateParams, entity, Support_level) {
        $scope.support_level = entity;
        $scope.load = function (id) {
            Support_level.get({id: id}, function(result) {
                $scope.support_level = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:support_levelUpdate', function(event, result) {
            $scope.support_level = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
