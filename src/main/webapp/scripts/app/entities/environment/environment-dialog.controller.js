'use strict';

angular.module('dgdtoolApp').controller('EnvironmentDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Environment',
        function($scope, $stateParams, $modalInstance, entity, Environment) {

        $scope.environment = entity;
        $scope.load = function(id) {
            Environment.get({id : id}, function(result) {
                $scope.environment = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:environmentUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.environment.id != null) {
                Environment.update($scope.environment, onSaveFinished);
            } else {
                Environment.save($scope.environment, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
