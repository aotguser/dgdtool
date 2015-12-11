'use strict';

angular.module('dgdtoolApp')
    .factory('PlatformSearch', function ($resource) {
        return $resource('api/_search/platforms/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
