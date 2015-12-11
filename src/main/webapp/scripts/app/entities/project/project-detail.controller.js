'use strict';

angular.module('dgdtoolApp')
    .controller('ProjectDetailController', function ($scope, $rootScope, $stateParams, entity, Project) {
        $scope.project = entity;
        $scope.load = function (id) {
            Project.get({id: id}, function(result) {
                $scope.project = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:projectUpdate', function(event, result) {
            $scope.project = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
