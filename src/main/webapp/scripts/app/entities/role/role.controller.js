'use strict';

angular.module('dgdtoolApp')
    .controller('RoleController', function ($scope, Role, RoleSearch, ParseLinks) {
        $scope.roles = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Role.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.roles = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Role.get({id: id}, function(result) {
                $scope.role = result;
                $('#deleteRoleConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Role['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteRoleConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            RoleSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.roles = result;
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
            $scope.role = {
                role_name: null,
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
