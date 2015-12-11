'use strict';

angular.module('dgdtoolApp')
    .controller('Project_phaseController', function ($scope, Project_phase, Project_phaseSearch, ParseLinks) {
        $scope.project_phases = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Project_phase.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.project_phases = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Project_phase.get({id: id}, function(result) {
                $scope.project_phase = result;
                $('#deleteProject_phaseConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Project_phase['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProject_phaseConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Project_phaseSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.project_phases = result;
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
            $scope.project_phase = {
                project_phase_name: null,
                description: null,
                status_id: null,
                created_by: null,
                modified_by: null,
                modified_date: null,
                id: null
            };
        };
    });
