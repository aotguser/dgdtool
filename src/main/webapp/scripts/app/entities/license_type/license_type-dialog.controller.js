'use strict';

angular.module('dgdtoolApp').controller('License_typeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'License_type',
        function($scope, $stateParams, $modalInstance, entity, License_type) {

        $scope.license_type = entity;
        $scope.load = function(id) {
            License_type.get({id : id}, function(result) {
                $scope.license_type = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:license_typeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.license_type.id != null) {
                License_type.update($scope.license_type, onSaveFinished);
            } else {
                License_type.save($scope.license_type, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
