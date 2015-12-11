'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('partner', {
                parent: 'entity',
                url: '/partners',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.partner.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/partner/partners.html',
                        controller: 'PartnerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('partner');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('partner.detail', {
                parent: 'entity',
                url: '/partner/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.partner.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/partner/partner-detail.html',
                        controller: 'PartnerDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('partner');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Partner', function($stateParams, Partner) {
                        return Partner.get({id : $stateParams.id});
                    }]
                }
            })
            .state('partner.new', {
                parent: 'partner',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/partner/partner-dialog.html',
                        controller: 'PartnerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    partner_name: null,
                                    partner_key: null,
                                    partner_base_url: null,
                                    partner_broker_url: null,
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
                        $state.go('partner', null, { reload: true });
                    }, function() {
                        $state.go('partner');
                    })
                }]
            })
            .state('partner.edit', {
                parent: 'partner',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/partner/partner-dialog.html',
                        controller: 'PartnerDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Partner', function(Partner) {
                                return Partner.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('partner', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
