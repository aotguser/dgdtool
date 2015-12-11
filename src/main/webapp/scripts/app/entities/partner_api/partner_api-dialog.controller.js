'use strict';

angular.module('dgdtoolApp').controller('Partner_apiDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Partner_api',
        function($scope, $stateParams, $modalInstance, entity, Partner_api) {

        $scope.partner_api = entity;
        $scope.load = function(id) {
            Partner_api.get({id : id}, function(result) {
                $scope.partner_api = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:partner_apiUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.partner_api.id != null) {
                Partner_api.update($scope.partner_api, onSaveFinished);
            } else {
                Partner_api.save($scope.partner_api, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
