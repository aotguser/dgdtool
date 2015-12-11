'use strict';

angular.module('dgdtoolApp').controller('Os_versionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Os_version',
        function($scope, $stateParams, $modalInstance, entity, Os_version) {

        $scope.os_version = entity;
        $scope.load = function(id) {
            Os_version.get({id : id}, function(result) {
                $scope.os_version = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:os_versionUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.os_version.id != null) {
                Os_version.update($scope.os_version, onSaveFinished);
            } else {
                Os_version.save($scope.os_version, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
