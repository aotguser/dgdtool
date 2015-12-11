'use strict';

angular.module('dgdtoolApp').controller('Security_solutionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Security_solution',
        function($scope, $stateParams, $modalInstance, entity, Security_solution) {

        $scope.security_solution = entity;
        $scope.load = function(id) {
            Security_solution.get({id : id}, function(result) {
                $scope.security_solution = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:security_solutionUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.security_solution.id != null) {
                Security_solution.update($scope.security_solution, onSaveFinished);
            } else {
                Security_solution.save($scope.security_solution, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
