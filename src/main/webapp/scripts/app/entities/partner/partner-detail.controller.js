'use strict';

angular.module('dgdtoolApp')
    .controller('PartnerDetailController', function ($scope, $rootScope, $stateParams, entity, Partner) {
        $scope.partner = entity;
        $scope.load = function (id) {
            Partner.get({id: id}, function(result) {
                $scope.partner = result;
            });
        };
        var unsubscribe = $rootScope.$on('dgdtoolApp:partnerUpdate', function(event, result) {
            $scope.partner = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
