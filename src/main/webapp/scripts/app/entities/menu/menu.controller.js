'use strict';

angular.module('dgdtoolApp')
    .controller('MenuController', function ($scope, Menu, MenuSearch, ParseLinks) {
        $scope.menus = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Menu.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.menus = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Menu.get({id: id}, function(result) {
                $scope.menu = result;
                $('#deleteMenuConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Menu['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMenuConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            MenuSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.menus = result;
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
            $scope.menu = {
                parent_menu_id: null,
                menu_item_name: null,
                controller_url: null,
                display_order: null,
                menu_hint: null,
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
