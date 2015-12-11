'use strict';

angular.module('dgdtoolApp')
    .controller('Security_solutionDetailController', function ($scope, $rootScope, $stateParams, entity, Security_solution) {
        $scope.security_solution = entity;
        $scope.load = function (id) {
            Security_solution.get({id: id}, function(result) {
                $scope.security_solution = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:security_solutionUpdate', function(event, result) {
            $scope.security_solution = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
