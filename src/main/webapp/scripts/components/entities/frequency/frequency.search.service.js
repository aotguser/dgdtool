'use strict';

angular.module('dgdtoolApp')
    .factory('FrequencySearch', function ($resource) {
        return $resource('api/_search/frequencys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
