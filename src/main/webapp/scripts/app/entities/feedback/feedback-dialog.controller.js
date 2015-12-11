'use strict';

angular.module('dgdtoolApp').controller('FeedbackDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Feedback',
        function($scope, $stateParams, $modalInstance, entity, Feedback) {

        $scope.feedback = entity;
        $scope.load = function(id) {
            Feedback.get({id : id}, function(result) {
                $scope.feedback = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:feedbackUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.feedback.id != null) {
                Feedback.update($scope.feedback, onSaveFinished);
            } else {
                Feedback.save($scope.feedback, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
