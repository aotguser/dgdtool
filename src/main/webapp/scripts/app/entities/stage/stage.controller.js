'use strict';

angular.module('dgdtoolApp')
    .controller('StageController', function ($scope, Stage, StageSearch, ParseLinks) {
        $scope.stages = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Stage.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.stages = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Stage.get({id: id}, function(result) {
                $scope.stage = result;
                $('#deleteStageConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Stage['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteStageConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            StageSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.stages = result;
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
            $scope.stage = {
                stage_name: null,
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
