'use strict';

angular.module('dgdtoolApp')
    .controller('StatusesController', function ($scope, Statuses, StatusesSearch, ParseLinks) {
        $scope.statusess = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Statuses.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.statusess = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Statuses.get({id: id}, function(result) {
                $scope.statuses = result;
                $('#deleteStatusesConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Statuses['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteStatusesConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            StatusesSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.statusess = result;
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
            $scope.statuses = {
                status_type_id: null,
                status_name: null,
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
