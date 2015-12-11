'use strict';

angular.module('dgdtoolApp')
    .factory('Project_phaseSearch', function ($resource) {
        return $resource('api/_search/project_phases/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
