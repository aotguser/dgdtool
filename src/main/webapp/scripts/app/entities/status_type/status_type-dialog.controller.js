'use strict';

angular.module('dgdtoolApp').controller('Status_typeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Status_type',
        function($scope, $stateParams, $modalInstance, entity, Status_type) {

        $scope.status_type = entity;
        $scope.load = function(id) {
            Status_type.get({id : id}, function(result) {
                $scope.status_type = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:status_typeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.status_type.id != null) {
                Status_type.update($scope.status_type, onSaveFinished);
            } else {
                Status_type.save($scope.status_type, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
