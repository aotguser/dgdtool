'use strict';

angular.module('dgdtoolApp')
    .controller('Os_typeDetailController', function ($scope, $rootScope, $stateParams, entity, Os_type) {
        $scope.os_type = entity;
        $scope.load = function (id) {
            Os_type.get({id: id}, function(result) {
                $scope.os_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:os_typeUpdate', function(event, result) {
            $scope.os_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
