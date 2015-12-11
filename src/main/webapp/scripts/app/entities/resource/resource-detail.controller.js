'use strict';

angular.module('dgdtoolApp')
    .controller('ResourceDetailController', function ($scope, $rootScope, $stateParams, entity, Resource) {
        $scope.resource = entity;
        $scope.load = function (id) {
            Resource.get({id: id}, function(result) {
                $scope.resource = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:resourceUpdate', function(event, result) {
            $scope.resource = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
