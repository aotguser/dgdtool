'use strict';

angular.module('dgdtoolApp')
    .factory('FeedbackSearch', function ($resource) {
        return $resource('api/_search/feedbacks/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
