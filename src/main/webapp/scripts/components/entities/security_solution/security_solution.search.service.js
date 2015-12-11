'use strict';

angular.module('dgdtoolApp')
    .factory('Security_solutionSearch', function ($resource) {
        return $resource('api/_search/security_solutions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
