'use strict';

angular.module('dgdtoolApp').controller('Project_phaseDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Project_phase',
        function($scope, $stateParams, $modalInstance, entity, Project_phase) {

        $scope.project_phase = entity;
        $scope.load = function(id) {
            Project_phase.get({id : id}, function(result) {
                $scope.project_phase = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:project_phaseUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.project_phase.id != null) {
                Project_phase.update($scope.project_phase, onSaveFinished);
            } else {
                Project_phase.save($scope.project_phase, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
