'use strict';

angular.module('dgdtoolApp')
    .controller('ProjectController', function ($scope, Project, ProjectSearch, ParseLinks) {
        $scope.projects = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Project.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.projects = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Project.get({id: id}, function(result) {
                $scope.project = result;
                $('#deleteProjectConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Project['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProjectConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            ProjectSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.projects = result;
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
            $scope.project = {
                requestor_id: null,
                associated_project_id: null,
                initiative_id: null,
                ticket_id: null,
                service_id: null,
                app_id: null,
                package_id: null,
                legacy_owner: null,
                business_unit: null,
                planned_start_date: null,
                planned_end_date: null,
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
