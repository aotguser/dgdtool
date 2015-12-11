'use strict';

angular.module('dgdtoolApp').controller('App_typeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'App_type',
        function($scope, $stateParams, $modalInstance, entity, App_type) {

        $scope.app_type = entity;
        $scope.load = function(id) {
            App_type.get({id : id}, function(result) {
                $scope.app_type = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:app_typeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.app_type.id != null) {
                App_type.update($scope.app_type, onSaveFinished);
            } else {
                App_type.save($scope.app_type, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
