'use strict';

angular.module('dgdtoolApp')
    .controller('Package_typeDetailController', function ($scope, $rootScope, $stateParams, entity, Package_type) {
        $scope.package_type = entity;
        $scope.load = function (id) {
            Package_type.get({id: id}, function(result) {
                $scope.package_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:package_typeUpdate', function(event, result) {
            $scope.package_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
