'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('app', {
                parent: 'entity',
                url: '/apps',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.app.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/app/apps.html',
                        controller: 'AppController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('app');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('app.detail', {
                parent: 'entity',
                url: '/app/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.app.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/app/app-detail.html',
                        controller: 'AppDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('app');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'App', function($stateParams, App) {
                        return App.get({id : $stateParams.id});
                    }]
                }
            })
            .state('app.new', {
                parent: 'app',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/app/app-dialog.html',
                        controller: 'AppDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    app_name: null,
                                    app_type_id: null,
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
                        $state.go('app', null, { reload: true });
                    }, function() {
                        $state.go('app');
                    })
                }]
            })
            .state('app.edit', {
                parent: 'app',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/app/app-dialog.html',
                        controller: 'AppDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['App', function(App) {
                                return App.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('app', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
