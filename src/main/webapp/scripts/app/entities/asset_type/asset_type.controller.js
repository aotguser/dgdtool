'use strict';

angular.module('dgdtoolApp')
    .controller('Asset_typeController', function ($scope, Asset_type, Asset_typeSearch, ParseLinks) {
        $scope.asset_types = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Asset_type.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.asset_types = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Asset_type.get({id: id}, function(result) {
                $scope.asset_type = result;
                $('#deleteAsset_typeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Asset_type['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAsset_typeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Asset_typeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.asset_types = result;
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
            $scope.asset_type = {
                asset_type_name: null,
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
