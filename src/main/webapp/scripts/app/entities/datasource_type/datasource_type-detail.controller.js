'use strict';

angular.module('dgdtoolApp')
    .controller('Datasource_typeDetailController', function ($scope, $rootScope, $stateParams, entity, Datasource_type) {
        $scope.datasource_type = entity;
        $scope.load = function (id) {
            Datasource_type.get({id: id}, function(result) {
                $scope.datasource_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:datasource_typeUpdate', function(event, result) {
            $scope.datasource_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
