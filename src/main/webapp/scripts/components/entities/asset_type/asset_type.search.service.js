'use strict';

angular.module('dgdtoolApp')
    .factory('Asset_typeSearch', function ($resource) {
        return $resource('api/_search/asset_types/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
