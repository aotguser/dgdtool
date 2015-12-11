'use strict';

angular.module('dgdtoolApp')
    .controller('Feedback_typeController', function ($scope, Feedback_type, Feedback_typeSearch, ParseLinks) {
        $scope.feedback_types = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Feedback_type.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.feedback_types = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Feedback_type.get({id: id}, function(result) {
                $scope.feedback_type = result;
                $('#deleteFeedback_typeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Feedback_type['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFeedback_typeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Feedback_typeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.feedback_types = result;
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
            $scope.feedback_type = {
                feedback_type_name: null,
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
