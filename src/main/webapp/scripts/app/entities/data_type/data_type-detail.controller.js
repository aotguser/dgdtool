'use strict';

angular.module('dgdtoolApp')
    .controller('Data_typeDetailController', function ($scope, $rootScope, $stateParams, entity, Data_type) {
        $scope.data_type = entity;
        $scope.load = function (id) {
            Data_type.get({id: id}, function(result) {
                $scope.data_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:data_typeUpdate', function(event, result) {
            $scope.data_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
