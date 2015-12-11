'use strict';

angular.module('dgdtoolApp')
    .controller('PackagesDetailController', function ($scope, $rootScope, $stateParams, entity, Packages) {
        $scope.packages = entity;
        $scope.load = function (id) {
            Packages.get({id: id}, function(result) {
                $scope.packages = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:packagesUpdate', function(event, result) {
            $scope.packages = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
