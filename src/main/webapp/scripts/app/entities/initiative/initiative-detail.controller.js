'use strict';

angular.module('dgdtoolApp')
    .controller('InitiativeDetailController', function ($scope, $rootScope, $stateParams, entity, Initiative) {
        $scope.initiative = entity;
        $scope.load = function (id) {
            Initiative.get({id: id}, function(result) {
                $scope.initiative = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:initiativeUpdate', function(event, result) {
            $scope.initiative = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
