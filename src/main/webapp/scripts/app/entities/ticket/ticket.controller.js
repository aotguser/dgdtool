'use strict';

angular.module('dgdtoolApp')
    .controller('TicketController', function ($scope, Ticket, TicketSearch, ParseLinks) {
        $scope.tickets = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Ticket.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.tickets = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Ticket.get({id: id}, function(result) {
                $scope.ticket = result;
                $('#deleteTicketConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Ticket['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTicketConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            TicketSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.tickets = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.ticket = {
                ticket_ref_number: null,
                ticket_created_date: null,
                description: null,
                status_id: null,
                created_by: null,
                created_date: null,
                modified_by: null,
                modified_date: null,
                id: null
            };
        };
    });
