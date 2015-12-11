'use strict';

angular.module('dgdtoolApp').controller('Project_teamDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Project_team',
        function($scope, $stateParams, $modalInstance, entity, Project_team) {

        $scope.project_team = entity;
        $scope.load = function(id) {
            Project_team.get({id : id}, function(result) {
                $scope.project_team = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:project_teamUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.project_team.id != null) {
                Project_team.update($scope.project_team, onSaveFinished);
            } else {
                Project_team.save($scope.project_team, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
