'use strict';

angular.module('dgdtoolApp').controller('Os_typeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Os_type',
        function($scope, $stateParams, $modalInstance, entity, Os_type) {

        $scope.os_type = entity;
        $scope.load = function(id) {
            Os_type.get({id : id}, function(result) {
                $scope.os_type = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:os_typeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.os_type.id != null) {
                Os_type.update($scope.os_type, onSaveFinished);
            } else {
                Os_type.save($scope.os_type, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
