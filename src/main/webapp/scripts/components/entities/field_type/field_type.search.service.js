'use strict';

angular.module('dgdtoolApp')
    .factory('Field_typeSearch', function ($resource) {
        return $resource('api/_search/field_types/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
