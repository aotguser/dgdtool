'use strict';

angular.module('dgdtoolApp')
    .controller('Os_versionDetailController', function ($scope, $rootScope, $stateParams, entity, Os_version) {
        $scope.os_version = entity;
        $scope.load = function (id) {
            Os_version.get({id: id}, function(result) {
                $scope.os_version = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:os_versionUpdate', function(event, result) {
            $scope.os_version = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
