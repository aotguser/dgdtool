'use strict';

angular.module('dgdtoolApp')
    .controller('FeedbackController', function ($scope, Feedback, FeedbackSearch, ParseLinks) {
        $scope.feedbacks = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Feedback.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.feedbacks = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Feedback.get({id: id}, function(result) {
                $scope.feedback = result;
                $('#deleteFeedbackConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Feedback['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFeedbackConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            FeedbackSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.feedbacks = result;
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
            $scope.feedback = {
                feedback_type_id: null,
                feedback_name: null,
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
