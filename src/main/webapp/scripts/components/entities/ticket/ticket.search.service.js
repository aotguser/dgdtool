'use strict';

angular.module('dgdtoolApp')
    .factory('TicketSearch', function ($resource) {
        return $resource('api/_search/tickets/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
