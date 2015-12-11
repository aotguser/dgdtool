'use strict';

angular.module('dgdtoolApp')
    .controller('Server_roleController', function ($scope, Server_role, Server_roleSearch, ParseLinks) {
        $scope.server_roles = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Server_role.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.server_roles = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Server_role.get({id: id}, function(result) {
                $scope.server_role = result;
                $('#deleteServer_roleConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Server_role['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteServer_roleConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Server_roleSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.server_roles = result;
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
            $scope.server_role = {
                server_role_name: null,
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
