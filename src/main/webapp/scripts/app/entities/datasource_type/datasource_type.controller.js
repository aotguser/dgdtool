'use strict';

angular.module('dgdtoolApp')
    .controller('Datasource_typeController', function ($scope, Datasource_type, Datasource_typeSearch, ParseLinks) {
        $scope.datasource_types = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Datasource_type.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.datasource_types = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Datasource_type.get({id: id}, function(result) {
                $scope.datasource_type = result;
                $('#deleteDatasource_typeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Datasource_type['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDatasource_typeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Datasource_typeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.datasource_types = result;
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
            $scope.datasource_type = {
                datasource_type_name: null,
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
