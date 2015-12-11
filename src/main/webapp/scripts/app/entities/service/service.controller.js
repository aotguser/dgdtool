'use strict';

angular.module('dgdtoolApp')
    .controller('ServiceController', function ($scope, Service, ServiceSearch, ParseLinks) {
        $scope.services = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Service.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.services = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Service.get({id: id}, function(result) {
                $scope.service = result;
                $('#deleteServiceConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Service['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteServiceConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            ServiceSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.services = result;
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
            $scope.service = {
                service_name: null,
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
