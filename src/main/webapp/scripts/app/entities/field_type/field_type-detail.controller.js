'use strict';

angular.module('dgdtoolApp')
    .controller('Field_typeDetailController', function ($scope, $rootScope, $stateParams, entity, Field_type) {
        $scope.field_type = entity;
        $scope.load = function (id) {
            Field_type.get({id: id}, function(result) {
                $scope.field_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:field_typeUpdate', function(event, result) {
            $scope.field_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
