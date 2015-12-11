'use strict';

angular.module('dgdtoolApp').controller('TicketDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Ticket',
        function($scope, $stateParams, $modalInstance, entity, Ticket) {

        $scope.ticket = entity;
        $scope.load = function(id) {
            Ticket.get({id : id}, function(result) {
                $scope.ticket = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('dgdtoolApp:ticketUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.ticket.id != null) {
                Ticket.update($scope.ticket, onSaveFinished);
            } else {
                Ticket.save($scope.ticket, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
