'use strict';

angular.module('dgdtoolApp')
    .controller('License_typeDetailController', function ($scope, $rootScope, $stateParams, entity, License_type) {
        $scope.license_type = entity;
        $scope.load = function (id) {
            License_type.get({id: id}, function(result) {
                $scope.license_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:license_typeUpdate', function(event, result) {
            $scope.license_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
