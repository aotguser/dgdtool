'use strict';

angular.module('dgdtoolApp')
    .factory('Os_typeSearch', function ($resource) {
        return $resource('api/_search/os_types/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
