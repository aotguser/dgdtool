'use strict';

angular.module('dgdtoolApp')
    .controller('Initiative_typeDetailController', function ($scope, $rootScope, $stateParams, entity, Initiative_type) {
        $scope.initiative_type = entity;
        $scope.load = function (id) {
            Initiative_type.get({id: id}, function(result) {
                $scope.initiative_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:initiative_typeUpdate', function(event, result) {
            $scope.initiative_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
