'use strict';

angular.module('dgdtoolApp')
    .controller('StageDetailController', function ($scope, $rootScope, $stateParams, entity, Stage) {
        $scope.stage = entity;
        $scope.load = function (id) {
            Stage.get({id: id}, function(result) {
                $scope.stage = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:stageUpdate', function(event, result) {
            $scope.stage = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
