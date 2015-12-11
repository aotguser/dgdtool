'use strict';

angular.module('dgdtoolApp')
    .controller('Os_typeController', function ($scope, Os_type, Os_typeSearch, ParseLinks) {
        $scope.os_types = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Os_type.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.os_types = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Os_type.get({id: id}, function(result) {
                $scope.os_type = result;
                $('#deleteOs_typeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Os_type['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOs_typeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Os_typeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.os_types = result;
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
            $scope.os_type = {
                os_type_name: null,
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
