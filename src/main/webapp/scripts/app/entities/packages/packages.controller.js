'use strict';

angular.module('dgdtoolApp')
    .controller('PackagesController', function ($scope, Packages, PackagesSearch, ParseLinks) {
        $scope.packagess = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Packages.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.packagess = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Packages.get({id: id}, function(result) {
                $scope.packages = result;
                $('#deletePackagesConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Packages['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePackagesConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PackagesSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.packagess = result;
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
            $scope.packages = {
                package_name: null,
                package_type_id: null,
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
