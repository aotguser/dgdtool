'use strict';

angular.module('dgdtoolApp').controller('FrequencyDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Frequency',
        function($scope, $stateParams, $modalInstance, entity, Frequency) {

        $scope.frequency = entity;
        $scope.load = function(id) {
            Frequency.get({id : id}, function(result) {
                $scope.frequency = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:frequencyUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.frequency.id != null) {
                Frequency.update($scope.frequency, onSaveFinished);
            } else {
                Frequency.save($scope.frequency, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
