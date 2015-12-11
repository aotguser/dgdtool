'use strict';

angular.module('dgdtoolApp')
    .factory('Datasource_typeSearch', function ($resource) {
        return $resource('api/_search/datasource_types/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
