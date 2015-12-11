'use strict';

angular.module('dgdtoolApp')
    .controller('TicketDetailController', function ($scope, $rootScope, $stateParams, entity, Ticket) {
        $scope.ticket = entity;
        $scope.load = function (id) {
            Ticket.get({id: id}, function(result) {
                $scope.ticket = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:ticketUpdate', function(event, result) {
            $scope.ticket = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
