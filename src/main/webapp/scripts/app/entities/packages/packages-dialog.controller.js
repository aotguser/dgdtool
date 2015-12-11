'use strict';

angular.module('dgdtoolApp').controller('PackagesDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Packages',
        function($scope, $stateParams, $modalInstance, entity, Packages) {

        $scope.packages = entity;
        $scope.load = function(id) {
            Packages.get({id : id}, function(result) {
                $scope.packages = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:packagesUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.packages.id != null) {
                Packages.update($scope.packages, onSaveFinished);
            } else {
                Packages.save($scope.packages, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
