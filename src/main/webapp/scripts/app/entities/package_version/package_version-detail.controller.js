'use strict';

angular.module('dgdtoolApp')
    .controller('Package_versionDetailController', function ($scope, $rootScope, $stateParams, entity, Package_version) {
        $scope.package_version = entity;
        $scope.load = function (id) {
            Package_version.get({id: id}, function(result) {
                $scope.package_version = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:package_versionUpdate', function(event, result) {
            $scope.package_version = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
