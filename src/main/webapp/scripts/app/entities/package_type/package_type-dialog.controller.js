'use strict';

angular.module('dgdtoolApp').controller('Package_typeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Package_type',
        function($scope, $stateParams, $modalInstance, entity, Package_type) {

        $scope.package_type = entity;
        $scope.load = function(id) {
            Package_type.get({id : id}, function(result) {
                $scope.package_type = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:package_typeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.package_type.id != null) {
                Package_type.update($scope.package_type, onSaveFinished);
            } else {
                Package_type.save($scope.package_type, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
