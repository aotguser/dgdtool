'use strict';

angular.module('dgdtoolApp')
    .controller('Status_typeController', function ($scope, Status_type, Status_typeSearch, ParseLinks) {
        $scope.status_types = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Status_type.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.status_types = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Status_type.get({id: id}, function(result) {
                $scope.status_type = result;
                $('#deleteStatus_typeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Status_type['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteStatus_typeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Status_typeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.status_types = result;
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
            $scope.status_type = {
                status_type_name: null,
                description: null,
                created_by: null,
                created_date: null,
                modified_by: null,
                modified_date: null,
                id: null
            };
        };
    });
