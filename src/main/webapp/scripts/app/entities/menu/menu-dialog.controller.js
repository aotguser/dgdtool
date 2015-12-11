'use strict';

angular.module('dgdtoolApp').controller('MenuDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Menu',
        function($scope, $stateParams, $modalInstance, entity, Menu) {

        $scope.menu = entity;
        $scope.load = function(id) {
            Menu.get({id : id}, function(result) {
                $scope.menu = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:menuUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.menu.id != null) {
                Menu.update($scope.menu, onSaveFinished);
            } else {
                Menu.save($scope.menu, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
