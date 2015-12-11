'use strict';

angular.module('dgdtoolApp')
    .factory('Project_team', function ($resource, DateUtils) {
        return $resource('api/project_teams/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.assigned_date = DateUtils.convertDateTimeFromServer(data.assigned_date);
                    data.created_date = DateUtils.convertLocaleDateFromServer(data.created_date);
                    data.modified_date = DateUtils.convertLocaleDateFromServer(data.modified_date);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.created_date = DateUtils.convertLocaleDateToServer(data.created_date);
                    data.modified_date = DateUtils.convertLocaleDateToServer(data.modified_date);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.created_date = DateUtils.convertLocaleDateToServer(data.created_date);
                    data.modified_date = DateUtils.convertLocaleDateToServer(data.modified_date);
                    return angular.toJson(data);
                }
            }
        });
    });
