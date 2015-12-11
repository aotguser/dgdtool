'use strict';

angular.module('dgdtoolApp')
    .controller('App_typeController', function ($scope, App_type, App_typeSearch, ParseLinks) {
        $scope.app_types = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            App_type.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.app_types = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            App_type.get({id: id}, function(result) {
                $scope.app_type = result;
                $('#deleteApp_typeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            App_type['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteApp_typeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            App_typeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.app_types = result;
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
            $scope.app_type = {
                app_type_name: null,
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
