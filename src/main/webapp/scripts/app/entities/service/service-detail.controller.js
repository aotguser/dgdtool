'use strict';

angular.module('dgdtoolApp')
    .controller('ServiceDetailController', function ($scope, $rootScope, $stateParams, entity, Service) {
        $scope.service = entity;
        $scope.load = function (id) {
            Service.get({id: id}, function(result) {
                $scope.service = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:serviceUpdate', function(event, result) {
            $scope.service = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
