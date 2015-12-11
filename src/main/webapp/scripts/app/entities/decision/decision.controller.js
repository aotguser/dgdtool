'use strict';

angular.module('dgdtoolApp')
    .controller('DecisionController', function ($scope, Decision, DecisionSearch, ParseLinks) {
        $scope.decisions = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Decision.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.decisions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Decision.get({id: id}, function(result) {
                $scope.decision = result;
                $('#deleteDecisionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Decision['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDecisionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            DecisionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.decisions = result;
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
            $scope.decision = {
                decision_name: null,
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
