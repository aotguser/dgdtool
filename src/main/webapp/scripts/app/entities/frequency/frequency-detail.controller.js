'use strict';

angular.module('dgdtoolApp')
    .controller('FrequencyDetailController', function ($scope, $rootScope, $stateParams, entity, Frequency) {
        $scope.frequency = entity;
        $scope.load = function (id) {
            Frequency.get({id: id}, function(result) {
                $scope.frequency = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:frequencyUpdate', function(event, result) {
            $scope.frequency = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
