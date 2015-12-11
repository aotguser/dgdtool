'use strict';

angular.module('dgdtoolApp').controller('End_state_designationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'End_state_designation',
        function($scope, $stateParams, $modalInstance, entity, End_state_designation) {

        $scope.end_state_designation = entity;
        $scope.load = function(id) {
            End_state_designation.get({id : id}, function(result) {
                $scope.end_state_designation = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:end_state_designationUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.end_state_designation.id != null) {
                End_state_designation.update($scope.end_state_designation, onSaveFinished);
            } else {
                End_state_designation.save($scope.end_state_designation, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
