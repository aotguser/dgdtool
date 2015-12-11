'use strict';

angular.module('dgdtoolApp')
    .controller('Project_phaseDetailController', function ($scope, $rootScope, $stateParams, entity, Project_phase) {
        $scope.project_phase = entity;
        $scope.load = function (id) {
            Project_phase.get({id: id}, function(result) {
                $scope.project_phase = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:project_phaseUpdate', function(event, result) {
            $scope.project_phase = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
