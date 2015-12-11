'use strict';

angular.module('dgdtoolApp').controller('AppDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'App',
        function($scope, $stateParams, $modalInstance, entity, App) {

        $scope.app = entity;
        $scope.load = function(id) {
            App.get({id : id}, function(result) {
                $scope.app = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:appUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.app.id != null) {
                App.update($scope.app, onSaveFinished);
            } else {
                App.save($scope.app, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
