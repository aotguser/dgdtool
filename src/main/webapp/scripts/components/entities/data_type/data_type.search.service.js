'use strict';

angular.module('dgdtoolApp')
    .factory('Data_typeSearch', function ($resource) {
        return $resource('api/_search/data_types/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
