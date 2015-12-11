'use strict';

angular.module('dgdtoolApp')
    .controller('MessageController', function ($scope, Message, MessageSearch, ParseLinks) {
        $scope.messages = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Message.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.messages = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Message.get({id: id}, function(result) {
                $scope.message = result;
                $('#deleteMessageConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Message['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMessageConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            MessageSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.messages = result;
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
            $scope.message = {
                sender_id: null,
                recipient_id: null,
                sender_deleted: null,
                recipient_deleted: null,
                recipient_viewed: null,
                message: null,
                status_id: null,
                created_by: null,
                created_date: null,
                modified_by: null,
                modified_date: null,
                id: null
            };
        };
    });
