'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('asset_type', {
                parent: 'entity',
                url: '/asset_types',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.asset_type.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/asset_type/asset_types.html',
                        controller: 'Asset_typeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('asset_type');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('asset_type.detail', {
                parent: 'entity',
                url: '/asset_type/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.asset_type.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/asset_type/asset_type-detail.html',
                        controller: 'Asset_typeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('asset_type');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Asset_type', function($stateParams, Asset_type) {
                        return Asset_type.get({id : $stateParams.id});
                    }]
                }
            })
            .state('asset_type.new', {
                parent: 'asset_type',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/asset_type/asset_type-dialog.html',
                        controller: 'Asset_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    asset_type_name: null,
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
                        $state.go('asset_type', null, { reload: true });
                    }, function() {
                        $state.go('asset_type');
                    })
                }]
            })
            .state('asset_type.edit', {
                parent: 'asset_type',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/asset_type/asset_type-dialog.html',
                        controller: 'Asset_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Asset_type', function(Asset_type) {
                                return Asset_type.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('asset_type', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
