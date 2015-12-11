'use strict';

angular.module('dgdtoolApp').controller('Asset_typeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Asset_type',
        function($scope, $stateParams, $modalInstance, entity, Asset_type) {

        $scope.asset_type = entity;
        $scope.load = function(id) {
            Asset_type.get({id : id}, function(result) {
                $scope.asset_type = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:asset_typeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.asset_type.id != null) {
                Asset_type.update($scope.asset_type, onSaveFinished);
            } else {
                Asset_type.save($scope.asset_type, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
