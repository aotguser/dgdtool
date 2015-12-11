'use strict';

angular.module('dgdtoolApp').controller('ServiceDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Service',
        function($scope, $stateParams, $modalInstance, entity, Service) {

        $scope.service = entity;
        $scope.load = function(id) {
            Service.get({id : id}, function(result) {
                $scope.service = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:serviceUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.service.id != null) {
                Service.update($scope.service, onSaveFinished);
            } else {
                Service.save($scope.service, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
