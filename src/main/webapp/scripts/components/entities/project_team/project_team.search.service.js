'use strict';

angular.module('dgdtoolApp')
    .factory('Project_teamSearch', function ($resource) {
        return $resource('api/_search/project_teams/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
