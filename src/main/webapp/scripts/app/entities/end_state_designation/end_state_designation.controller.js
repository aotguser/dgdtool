'use strict';

angular.module('dgdtoolApp')
    .controller('End_state_designationController', function ($scope, End_state_designation, End_state_designationSearch, ParseLinks) {
        $scope.end_state_designations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            End_state_designation.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.end_state_designations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            End_state_designation.get({id: id}, function(result) {
                $scope.end_state_designation = result;
                $('#deleteEnd_state_designationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            End_state_designation['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEnd_state_designationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            End_state_designationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.end_state_designations = result;
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
            $scope.end_state_designation = {
                end_state_designation_name: null,
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
