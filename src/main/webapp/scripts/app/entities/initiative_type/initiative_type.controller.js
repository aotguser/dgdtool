'use strict';

angular.module('dgdtoolApp')
    .controller('Initiative_typeController', function ($scope, Initiative_type, Initiative_typeSearch, ParseLinks) {
        $scope.initiative_types = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Initiative_type.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.initiative_types = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Initiative_type.get({id: id}, function(result) {
                $scope.initiative_type = result;
                $('#deleteInitiative_typeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Initiative_type['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInitiative_typeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Initiative_typeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.initiative_types = result;
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
            $scope.initiative_type = {
                initiative_type_name: null,
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
