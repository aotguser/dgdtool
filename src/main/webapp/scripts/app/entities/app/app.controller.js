'use strict';

angular.module('dgdtoolApp')
    .controller('AppController', function ($scope, App, AppSearch, ParseLinks) {
        $scope.apps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            App.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.apps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            App.get({id: id}, function(result) {
                $scope.app = result;
                $('#deleteAppConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            App['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAppConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AppSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.apps = result;
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
            $scope.app = {
                app_name: null,
                app_type_id: null,
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
