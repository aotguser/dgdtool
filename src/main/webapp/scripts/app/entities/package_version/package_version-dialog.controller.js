'use strict';

angular.module('dgdtoolApp').controller('Package_versionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Package_version',
        function($scope, $stateParams, $modalInstance, entity, Package_version) {

        $scope.package_version = entity;
        $scope.load = function(id) {
            Package_version.get({id : id}, function(result) {
                $scope.package_version = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:package_versionUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.package_version.id != null) {
                Package_version.update($scope.package_version, onSaveFinished);
            } else {
                Package_version.save($scope.package_version, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
