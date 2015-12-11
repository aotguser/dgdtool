'use strict';

angular.module('dgdtoolApp')
    .controller('Partner_apiDetailController', function ($scope, $rootScope, $stateParams, entity, Partner_api) {
        $scope.partner_api = entity;
        $scope.load = function (id) {
            Partner_api.get({id: id}, function(result) {
                $scope.partner_api = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:partner_apiUpdate', function(event, result) {
            $scope.partner_api = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
