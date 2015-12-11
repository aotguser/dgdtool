'use strict';

angular.module('dgdtoolApp').controller('StageDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Stage',
        function($scope, $stateParams, $modalInstance, entity, Stage) {

        $scope.stage = entity;
        $scope.load = function(id) {
            Stage.get({id : id}, function(result) {
                $scope.stage = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:stageUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.stage.id != null) {
                Stage.update($scope.stage, onSaveFinished);
            } else {
                Stage.save($scope.stage, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
