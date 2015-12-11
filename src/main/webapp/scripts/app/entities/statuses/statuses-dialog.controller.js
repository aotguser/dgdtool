'use strict';

angular.module('dgdtoolApp').controller('StatusesDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Statuses',
        function($scope, $stateParams, $modalInstance, entity, Statuses) {

        $scope.statuses = entity;
        $scope.load = function(id) {
            Statuses.get({id : id}, function(result) {
                $scope.statuses = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:statusesUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.statuses.id != null) {
                Statuses.update($scope.statuses, onSaveFinished);
            } else {
                Statuses.save($scope.statuses, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
