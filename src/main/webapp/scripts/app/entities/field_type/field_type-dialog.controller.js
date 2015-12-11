'use strict';

angular.module('dgdtoolApp').controller('Field_typeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Field_type',
        function($scope, $stateParams, $modalInstance, entity, Field_type) {

        $scope.field_type = entity;
        $scope.load = function(id) {
            Field_type.get({id : id}, function(result) {
                $scope.field_type = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:field_typeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.field_type.id != null) {
                Field_type.update($scope.field_type, onSaveFinished);
            } else {
                Field_type.save($scope.field_type, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
