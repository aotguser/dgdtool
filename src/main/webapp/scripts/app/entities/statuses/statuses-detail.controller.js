'use strict';

angular.module('dgdtoolApp')
    .controller('StatusesDetailController', function ($scope, $rootScope, $stateParams, entity, Statuses) {
        $scope.statuses = entity;
        $scope.load = function (id) {
            Statuses.get({id: id}, function(result) {
                $scope.statuses = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:statusesUpdate', function(event, result) {
            $scope.statuses = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
