'use strict';

angular.module('dgdtoolApp').controller('DecisionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Decision',
        function($scope, $stateParams, $modalInstance, entity, Decision) {

        $scope.decision = entity;
        $scope.load = function(id) {
            Decision.get({id : id}, function(result) {
                $scope.decision = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:decisionUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.decision.id != null) {
                Decision.update($scope.decision, onSaveFinished);
            } else {
                Decision.save($scope.decision, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
