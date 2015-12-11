'use strict';

angular.module('dgdtoolApp')
    .controller('License_typeController', function ($scope, License_type, License_typeSearch, ParseLinks) {
        $scope.license_types = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            License_type.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.license_types = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            License_type.get({id: id}, function(result) {
                $scope.license_type = result;
                $('#deleteLicense_typeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            License_type['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteLicense_typeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            License_typeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.license_types = result;
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
            $scope.license_type = {
                license_type_name: null,
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
