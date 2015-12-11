'use strict';

angular.module('dgdtoolApp')
    .factory('End_state_designationSearch', function ($resource) {
        return $resource('api/_search/end_state_designations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
