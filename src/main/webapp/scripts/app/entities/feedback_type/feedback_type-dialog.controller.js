'use strict';

angular.module('dgdtoolApp').controller('Feedback_typeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Feedback_type',
        function($scope, $stateParams, $modalInstance, entity, Feedback_type) {

        $scope.feedback_type = entity;
        $scope.load = function(id) {
            Feedback_type.get({id : id}, function(result) {
                $scope.feedback_type = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:feedback_typeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.feedback_type.id != null) {
                Feedback_type.update($scope.feedback_type, onSaveFinished);
            } else {
                Feedback_type.save($scope.feedback_type, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
