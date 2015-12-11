'use strict';

angular.module('dgdtoolApp')
    .controller('Project_teamController', function ($scope, Project_team, Project_teamSearch, ParseLinks) {
        $scope.project_teams = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Project_team.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.project_teams = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Project_team.get({id: id}, function(result) {
                $scope.project_team = result;
                $('#deleteProject_teamConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Project_team['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProject_teamConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Project_teamSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.project_teams = result;
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
            $scope.project_team = {
                project_id: null,
                resource_id: null,
                role_id: null,
                support_level: null,
                est_hours: null,
                assigned_date: null,
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
