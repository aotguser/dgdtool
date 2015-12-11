'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('app_type', {
                parent: 'entity',
                url: '/app_types',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.app_type.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/app_type/app_types.html',
                        controller: 'App_typeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('app_type');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('app_type.detail', {
                parent: 'entity',
                url: '/app_type/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.app_type.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/app_type/app_type-detail.html',
                        controller: 'App_typeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('app_type');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'App_type', function($stateParams, App_type) {
                        return App_type.get({id : $stateParams.id});
                    }]
                }
            })
            .state('app_type.new', {
                parent: 'app_type',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/app_type/app_type-dialog.html',
                        controller: 'App_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    app_type_name: null,
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
                        $state.go('app_type', null, { reload: true });
                    }, function() {
                        $state.go('app_type');
                    })
                }]
            })
            .state('app_type.edit', {
                parent: 'app_type',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/app_type/app_type-dialog.html',
                        controller: 'App_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['App_type', function(App_type) {
                                return App_type.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('app_type', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
