'use strict';

angular.module('dgdtoolApp')
    .controller('DecisionDetailController', function ($scope, $rootScope, $stateParams, entity, Decision) {
        $scope.decision = entity;
        $scope.load = function (id) {
            Decision.get({id: id}, function(result) {
                $scope.decision = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:decisionUpdate', function(event, result) {
            $scope.decision = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
