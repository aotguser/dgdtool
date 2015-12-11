'use strict';

angular.module('dgdtoolApp')
    .controller('EnvironmentController', function ($scope, Environment, EnvironmentSearch, ParseLinks) {
        $scope.environments = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Environment.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.environments = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Environment.get({id: id}, function(result) {
                $scope.environment = result;
                $('#deleteEnvironmentConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Environment['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEnvironmentConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            EnvironmentSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.environments = result;
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
            $scope.environment = {
                environment_name: null,
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
