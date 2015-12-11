'use strict';

angular.module('dgdtoolApp')
    .factory('PartnerSearch', function ($resource) {
        return $resource('api/_search/partners/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
