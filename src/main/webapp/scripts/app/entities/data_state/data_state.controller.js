'use strict';

angular.module('dgdtoolApp')
    .controller('Data_stateController', function ($scope, Data_state, Data_stateSearch, ParseLinks) {
        $scope.data_states = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Data_state.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.data_states = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Data_state.get({id: id}, function(result) {
                $scope.data_state = result;
                $('#deleteData_stateConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Data_state['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteData_stateConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Data_stateSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.data_states = result;
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
            $scope.data_state = {
                data_state_name: null,
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
