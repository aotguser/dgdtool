'use strict';

angular.module('dgdtoolApp').controller('Initiative_typeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Initiative_type',
        function($scope, $stateParams, $modalInstance, entity, Initiative_type) {

        $scope.initiative_type = entity;
        $scope.load = function(id) {
            Initiative_type.get({id : id}, function(result) {
                $scope.initiative_type = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:initiative_typeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.initiative_type.id != null) {
                Initiative_type.update($scope.initiative_type, onSaveFinished);
            } else {
                Initiative_type.save($scope.initiative_type, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
