'use strict';

angular.module('dgdtoolApp')
    .controller('Package_typeController', function ($scope, Package_type, Package_typeSearch, ParseLinks) {
        $scope.package_types = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Package_type.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.package_types = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Package_type.get({id: id}, function(result) {
                $scope.package_type = result;
                $('#deletePackage_typeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Package_type['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePackage_typeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Package_typeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.package_types = result;
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
            $scope.package_type = {
                package_type_name: null,
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
