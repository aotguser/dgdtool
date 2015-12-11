'use strict';

angular.module('dgdtoolApp')
    .controller('Partner_apiController', function ($scope, Partner_api, Partner_apiSearch, ParseLinks) {
        $scope.partner_apis = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Partner_api.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.partner_apis = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Partner_api.get({id: id}, function(result) {
                $scope.partner_api = result;
                $('#deletePartner_apiConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Partner_api['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePartner_apiConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Partner_apiSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.partner_apis = result;
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
            $scope.partner_api = {
                partner_id: null,
                partner_req_url: null,
                partner_req_obj: null,
                partner_res_url: null,
                asp_method: null,
                asp_app_id: null,
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
