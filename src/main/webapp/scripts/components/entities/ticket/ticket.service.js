'use strict';

angular.module('dgdtoolApp')
    .factory('Ticket', function ($resource, DateUtils) {
        return $resource('api/tickets/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.ticket_created_date = DateUtils.convertDateTimeFromServer(data.ticket_created_date);
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
