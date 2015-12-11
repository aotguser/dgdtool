'use strict';

angular.module('dgdtoolApp')
    .factory('InitiativeSearch', function ($resource) {
        return $resource('api/_search/initiatives/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
