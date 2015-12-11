'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('initiative_type', {
                parent: 'entity',
                url: '/initiative_types',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.initiative_type.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/initiative_type/initiative_types.html',
                        controller: 'Initiative_typeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('initiative_type');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('initiative_type.detail', {
                parent: 'entity',
                url: '/initiative_type/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.initiative_type.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/initiative_type/initiative_type-detail.html',
                        controller: 'Initiative_typeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('initiative_type');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Initiative_type', function($stateParams, Initiative_type) {
                        return Initiative_type.get({id : $stateParams.id});
                    }]
                }
            })
            .state('initiative_type.new', {
                parent: 'initiative_type',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/initiative_type/initiative_type-dialog.html',
                        controller: 'Initiative_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    initiative_type_name: null,
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
                        $state.go('initiative_type', null, { reload: true });
                    }, function() {
                        $state.go('initiative_type');
                    })
                }]
            })
            .state('initiative_type.edit', {
                parent: 'initiative_type',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/initiative_type/initiative_type-dialog.html',
                        controller: 'Initiative_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Initiative_type', function(Initiative_type) {
                                return Initiative_type.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('initiative_type', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
