'use strict';

angular.module('dgdtoolApp')
    .controller('FeedbackDetailController', function ($scope, $rootScope, $stateParams, entity, Feedback) {
        $scope.feedback = entity;
        $scope.load = function (id) {
            Feedback.get({id: id}, function(result) {
                $scope.feedback = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:feedbackUpdate', function(event, result) {
            $scope.feedback = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
