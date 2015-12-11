'use strict';

angular.module('dgdtoolApp').controller('InitiativeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Initiative',
        function($scope, $stateParams, $modalInstance, entity, Initiative) {

        $scope.initiative = entity;
        $scope.load = function(id) {
            Initiative.get({id : id}, function(result) {
                $scope.initiative = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:initiativeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.initiative.id != null) {
                Initiative.update($scope.initiative, onSaveFinished);
            } else {
                Initiative.save($scope.initiative, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
