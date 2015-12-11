'use strict';

angular.module('dgdtoolApp')
    .factory('Project_phase', function ($resource, DateUtils) {
        return $resource('api/project_phases/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.modified_date = DateUtils.convertLocaleDateFromServer(data.modified_date);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.modified_date = DateUtils.convertLocaleDateToServer(data.modified_date);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.modified_date = DateUtils.convertLocaleDateToServer(data.modified_date);
                    return angular.toJson(data);
                }
            }
        });
    });
