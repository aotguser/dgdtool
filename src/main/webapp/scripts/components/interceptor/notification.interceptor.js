 'use strict';

angular.module('dgdtoolApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-dgdtoolApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-dgdtoolApp-params')});
                }
                return response;
            }
        };
    });
