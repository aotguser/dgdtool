'use strict';

angular.module('dgdtoolApp')
    .controller('Security_solutionController', function ($scope, Security_solution, Security_solutionSearch, ParseLinks) {
        $scope.security_solutions = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Security_solution.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.security_solutions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Security_solution.get({id: id}, function(result) {
                $scope.security_solution = result;
                $('#deleteSecurity_solutionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Security_solution['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSecurity_solutionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Security_solutionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.security_solutions = result;
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
            $scope.security_solution = {
                security_solution_name: null,
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
