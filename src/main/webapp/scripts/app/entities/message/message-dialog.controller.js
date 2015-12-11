'use strict';

angular.module('dgdtoolApp').controller('MessageDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Message',
        function($scope, $stateParams, $modalInstance, entity, Message) {

        $scope.message = entity;
        $scope.load = function(id) {
            Message.get({id : id}, function(result) {
                $scope.message = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:messageUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.message.id != null) {
                Message.update($scope.message, onSaveFinished);
            } else {
                Message.save($scope.message, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
