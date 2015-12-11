'use strict';

angular.module('dgdtoolApp')
    .controller('Status_typeDetailController', function ($scope, $rootScope, $stateParams, entity, Status_type) {
        $scope.status_type = entity;
        $scope.load = function (id) {
            Status_type.get({id: id}, function(result) {
                $scope.status_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:status_typeUpdate', function(event, result) {
            $scope.status_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
