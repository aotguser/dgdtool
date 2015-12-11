'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('partner_api', {
                parent: 'entity',
                url: '/partner_apis',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.partner_api.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/partner_api/partner_apis.html',
                        controller: 'Partner_apiController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('partner_api');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('partner_api.detail', {
                parent: 'entity',
                url: '/partner_api/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.partner_api.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/partner_api/partner_api-detail.html',
                        controller: 'Partner_apiDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('partner_api');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Partner_api', function($stateParams, Partner_api) {
                        return Partner_api.get({id : $stateParams.id});
                    }]
                }
            })
            .state('partner_api.new', {
                parent: 'partner_api',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/partner_api/partner_api-dialog.html',
                        controller: 'Partner_apiDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    partner_id: null,
                                    partner_req_url: null,
                                    partner_req_obj: null,
                                    partner_res_url: null,
                                    asp_method: null,
                                    asp_app_id: null,
                                    description: null,
                                    status_id: null,
                                    created_by: null,
                                    created_date: null,
                                    modified_by: null,
                                    modified_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('partner_api', null, { reload: true });
                    }, function() {
                        $state.go('partner_api');
                    })
                }]
            })
            .state('partner_api.edit', {
                parent: 'partner_api',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/partner_api/partner_api-dialog.html',
                        controller: 'Partner_apiDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Partner_api', function(Partner_api) {
                                return Partner_api.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('partner_api', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
