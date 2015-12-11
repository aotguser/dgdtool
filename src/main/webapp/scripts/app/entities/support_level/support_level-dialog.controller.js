'use strict';

angular.module('dgdtoolApp').controller('Support_levelDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Support_level',
        function($scope, $stateParams, $modalInstance, entity, Support_level) {

        $scope.support_level = entity;
        $scope.load = function(id) {
            Support_level.get({id : id}, function(result) {
                $scope.support_level = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:support_levelUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.support_level.id != null) {
                Support_level.update($scope.support_level, onSaveFinished);
            } else {
                Support_level.save($scope.support_level, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
