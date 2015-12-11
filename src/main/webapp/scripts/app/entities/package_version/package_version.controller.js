'use strict';

angular.module('dgdtoolApp')
    .controller('Package_versionController', function ($scope, Package_version, Package_versionSearch, ParseLinks) {
        $scope.package_versions = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Package_version.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.package_versions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Package_version.get({id: id}, function(result) {
                $scope.package_version = result;
                $('#deletePackage_versionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Package_version['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePackage_versionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Package_versionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.package_versions = result;
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
            $scope.package_version = {
                package_version_name: null,
                package_id: null,
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
