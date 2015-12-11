'use strict';

angular.module('dgdtoolApp')
    .controller('Data_typeController', function ($scope, Data_type, Data_typeSearch, ParseLinks) {
        $scope.data_types = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Data_type.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.data_types = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Data_type.get({id: id}, function(result) {
                $scope.data_type = result;
                $('#deleteData_typeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Data_type['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteData_typeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Data_typeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.data_types = result;
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
            $scope.data_type = {
                data_type_name: null,
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
