'use strict';

angular.module('dgdtoolApp')
    .controller('PathController', function ($scope, Path, PathSearch, ParseLinks) {
        $scope.paths = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Path.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.paths = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Path.get({id: id}, function(result) {
                $scope.path = result;
                $('#deletePathConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Path['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePathConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PathSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.paths = result;
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
            $scope.path = {
                path_name: null,
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
