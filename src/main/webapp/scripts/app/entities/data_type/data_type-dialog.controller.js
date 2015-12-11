'use strict';

angular.module('dgdtoolApp').controller('Data_typeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Data_type',
        function($scope, $stateParams, $modalInstance, entity, Data_type) {

        $scope.data_type = entity;
        $scope.load = function(id) {
            Data_type.get({id : id}, function(result) {
                $scope.data_type = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:data_typeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.data_type.id != null) {
                Data_type.update($scope.data_type, onSaveFinished);
            } else {
                Data_type.save($scope.data_type, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
