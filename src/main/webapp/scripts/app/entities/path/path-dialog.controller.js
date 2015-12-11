'use strict';

angular.module('dgdtoolApp').controller('PathDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Path',
        function($scope, $stateParams, $modalInstance, entity, Path) {

        $scope.path = entity;
        $scope.load = function(id) {
            Path.get({id : id}, function(result) {
                $scope.path = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:pathUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.path.id != null) {
                Path.update($scope.path, onSaveFinished);
            } else {
                Path.save($scope.path, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
