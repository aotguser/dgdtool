'use strict';

angular.module('dgdtoolApp')
    .controller('PartnerController', function ($scope, Partner, PartnerSearch, ParseLinks) {
        $scope.partners = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Partner.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.partners = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope['delete'] = function (id) {
            Partner.get({id: id}, function(result) {
                $scope.partner = result;
                $('#deletePartnerConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Partner['delete']({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePartnerConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PartnerSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.partners = result;
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
            $scope.partner = {
                partner_name: null,
                partner_key: null,
                partner_base_url: null,
                partner_broker_url: null,
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
