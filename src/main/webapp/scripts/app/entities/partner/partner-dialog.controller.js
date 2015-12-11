'use strict';

angular.module('dgdtoolApp').controller('PartnerDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Partner',
        function($scope, $stateParams, $modalInstance, entity, Partner) {

        $scope.partner = entity;
        $scope.load = function(id) {
            Partner.get({id : id}, function(result) {
                $scope.partner = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:partnerUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.partner.id != null) {
                Partner.update($scope.partner, onSaveFinished);
            } else {
                Partner.save($scope.partner, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
