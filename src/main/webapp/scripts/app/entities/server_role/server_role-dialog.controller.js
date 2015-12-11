'use strict';

angular.module('dgdtoolApp').controller('Server_roleDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Server_role',
        function($scope, $stateParams, $modalInstance, entity, Server_role) {

        $scope.server_role = entity;
        $scope.load = function(id) {
            Server_role.get({id : id}, function(result) {
                $scope.server_role = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:server_roleUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.server_role.id != null) {
                Server_role.update($scope.server_role, onSaveFinished);
            } else {
                Server_role.save($scope.server_role, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
