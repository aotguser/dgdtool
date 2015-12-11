'use strict';

angular.module('dgdtoolApp')
    .factory('Partner_apiSearch', function ($resource) {
        return $resource('api/_search/partner_apis/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
