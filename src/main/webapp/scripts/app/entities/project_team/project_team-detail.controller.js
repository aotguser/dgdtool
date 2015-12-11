'use strict';

angular.module('dgdtoolApp')
    .controller('Project_teamDetailController', function ($scope, $rootScope, $stateParams, entity, Project_team) {
        $scope.project_team = entity;
        $scope.load = function (id) {
            Project_team.get({id: id}, function(result) {
                $scope.project_team = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:project_teamUpdate', function(event, result) {
            $scope.project_team = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
