'use strict';

angular.module('dgdtoolApp')
    .controller('Data_stateDetailController', function ($scope, $rootScope, $stateParams, entity, Data_state) {
        $scope.data_state = entity;
        $scope.load = function (id) {
            Data_state.get({id: id}, function(result) {
                $scope.data_state = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:data_stateUpdate', function(event, result) {
            $scope.data_state = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
