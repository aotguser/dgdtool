'use strict';

angular.module('dgdtoolApp').controller('Datasource_typeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Datasource_type',
        function($scope, $stateParams, $modalInstance, entity, Datasource_type) {

        $scope.datasource_type = entity;
        $scope.load = function(id) {
            Datasource_type.get({id : id}, function(result) {
                $scope.datasource_type = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:datasource_typeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.datasource_type.id != null) {
                Datasource_type.update($scope.datasource_type, onSaveFinished);
            } else {
                Datasource_type.save($scope.datasource_type, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
