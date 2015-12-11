'use strict';

angular.module('dgdtoolApp')
    .controller('Asset_typeDetailController', function ($scope, $rootScope, $stateParams, entity, Asset_type) {
        $scope.asset_type = entity;
        $scope.load = function (id) {
            Asset_type.get({id: id}, function(result) {
                $scope.asset_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:asset_typeUpdate', function(event, result) {
            $scope.asset_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
