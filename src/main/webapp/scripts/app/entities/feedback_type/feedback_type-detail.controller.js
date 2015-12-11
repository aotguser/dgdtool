'use strict';

angular.module('dgdtoolApp')
    .controller('Feedback_typeDetailController', function ($scope, $rootScope, $stateParams, entity, Feedback_type) {
        $scope.feedback_type = entity;
        $scope.load = function (id) {
            Feedback_type.get({id: id}, function(result) {
                $scope.feedback_type = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:feedback_typeUpdate', function(event, result) {
            $scope.feedback_type = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
