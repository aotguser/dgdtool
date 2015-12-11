'use strict';

angular.module('dgdtoolApp')
    .controller('InitiativeController', function ($scope, Initiative, InitiativeSearch, ParseLinks) {
        $scope.initiatives = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Initiative.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.initiatives = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Initiative.get({id: id}, function(result) {
                $scope.initiative = result;
                $('#deleteInitiativeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Initiative['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInitiativeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InitiativeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.initiatives = result;
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
            $scope.initiative = {
                initiative_name: null,
                initiative_type_id: null,
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
