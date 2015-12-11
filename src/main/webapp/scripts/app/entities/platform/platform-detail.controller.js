'use strict';

angular.module('dgdtoolApp')
    .controller('PlatformDetailController', function ($scope, $rootScope, $stateParams, entity, Platform) {
        $scope.platform = entity;
        $scope.load = function (id) {
            Platform.get({id: id}, function(result) {
                $scope.platform = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:platformUpdate', function(event, result) {
            $scope.platform = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
