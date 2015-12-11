'use strict';

angular.module('dgdtoolApp')
    .controller('FrequencyController', function ($scope, Frequency, FrequencySearch, ParseLinks) {
        $scope.frequencys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Frequency.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.frequencys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Frequency.get({id: id}, function(result) {
                $scope.frequency = result;
                $('#deleteFrequencyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Frequency['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFrequencyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            FrequencySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.frequencys = result;
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
            $scope.frequency = {
                frequency_name: null,
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
