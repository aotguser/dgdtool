'use strict';

angular.module('dgdtoolApp')
    .controller('Server_typeController', function ($scope, Server_type, Server_typeSearch, ParseLinks) {
        $scope.server_types = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Server_type.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.server_types = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Server_type.get({id: id}, function(result) {
                $scope.server_type = result;
                $('#deleteServer_typeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Server_type['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteServer_typeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Server_typeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.server_types = result;
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
            $scope.server_type = {
                server_type_name: null,
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
