'use strict';

angular.module('dgdtoolApp')
    .controller('MenuDetailController', function ($scope, $rootScope, $stateParams, entity, Menu) {
        $scope.menu = entity;
        $scope.load = function (id) {
            Menu.get({id: id}, function(result) {
                $scope.menu = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:menuUpdate', function(event, result) {
            $scope.menu = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
