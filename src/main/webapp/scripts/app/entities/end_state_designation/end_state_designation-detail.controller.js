'use strict';

angular.module('dgdtoolApp')
    .controller('End_state_designationDetailController', function ($scope, $rootScope, $stateParams, entity, End_state_designation) {
        $scope.end_state_designation = entity;
        $scope.load = function (id) {
            End_state_designation.get({id: id}, function(result) {
                $scope.end_state_designation = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:end_state_designationUpdate', function(event, result) {
            $scope.end_state_designation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
