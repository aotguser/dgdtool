'use strict';

angular.module('dgdtoolApp')
    .controller('PlatformController', function ($scope, Platform, PlatformSearch, ParseLinks) {
        $scope.platforms = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Platform.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.platforms = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Platform.get({id: id}, function(result) {
                $scope.platform = result;
                $('#deletePlatformConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Platform['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePlatformConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PlatformSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.platforms = result;
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
            $scope.platform = {
                platform_name: null,
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
