'use strict';

angular.module('dgdtoolApp')
    .factory('Data_stateSearch', function ($resource) {
        return $resource('api/_search/data_states/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
