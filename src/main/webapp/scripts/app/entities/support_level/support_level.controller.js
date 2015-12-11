'use strict';

angular.module('dgdtoolApp')
    .controller('Support_levelController', function ($scope, Support_level, Support_levelSearch, ParseLinks) {
        $scope.support_levels = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Support_level.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.support_levels = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Support_level.get({id: id}, function(result) {
                $scope.support_level = result;
                $('#deleteSupport_levelConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Support_level['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSupport_levelConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Support_levelSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.support_levels = result;
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
            $scope.support_level = {
                support_level_name: null,
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
