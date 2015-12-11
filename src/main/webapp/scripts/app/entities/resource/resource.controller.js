'use strict';

angular.module('dgdtoolApp')
    .controller('ResourceController', function ($scope, Resource, ResourceSearch, ParseLinks) {
        $scope.resources = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Resource.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.resources = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Resource.get({id: id}, function(result) {
                $scope.resource = result;
                $('#deleteResourceConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Resource['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteResourceConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            ResourceSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.resources = result;
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
            $scope.resource = {
                resource_name: null,
                resource_number: null,
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
