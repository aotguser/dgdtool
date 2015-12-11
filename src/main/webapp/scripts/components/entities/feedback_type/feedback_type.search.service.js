'use strict';

angular.module('dgdtoolApp')
    .factory('Feedback_typeSearch', function ($resource) {
        return $resource('api/_search/feedback_types/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
