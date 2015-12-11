'use strict';

angular.module('dgdtoolApp')
    .controller('Field_typeController', function ($scope, Field_type, Field_typeSearch, ParseLinks) {
        $scope.field_types = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Field_type.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.field_types = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Field_type.get({id: id}, function(result) {
                $scope.field_type = result;
                $('#deleteField_typeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Field_type['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteField_typeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Field_typeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.field_types = result;
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
            $scope.field_type = {
                field_type_name: null,
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
