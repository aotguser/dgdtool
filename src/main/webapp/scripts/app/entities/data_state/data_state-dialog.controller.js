'use strict';

angular.module('dgdtoolApp').controller('Data_stateDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Data_state',
        function($scope, $stateParams, $modalInstance, entity, Data_state) {

        $scope.data_state = entity;
        $scope.load = function(id) {
            Data_state.get({id : id}, function(result) {
                $scope.data_state = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:data_stateUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.data_state.id != null) {
                Data_state.update($scope.data_state, onSaveFinished);
            } else {
                Data_state.save($scope.data_state, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
