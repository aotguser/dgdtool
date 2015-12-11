'use strict';

angular.module('dgdtoolApp').controller('Server_typeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Server_type',
        function($scope, $stateParams, $modalInstance, entity, Server_type) {

        $scope.server_type = entity;
        $scope.load = function(id) {
            Server_type.get({id : id}, function(result) {
                $scope.server_type = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:server_typeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.server_type.id != null) {
                Server_type.update($scope.server_type, onSaveFinished);
            } else {
                Server_type.save($scope.server_type, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
