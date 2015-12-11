'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('environment', {
                parent: 'entity',
                url: '/environments',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.environment.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/environment/environments.html',
                        controller: 'EnvironmentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('environment');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('environment.detail', {
                parent: 'entity',
                url: '/environment/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.environment.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/environment/environment-detail.html',
                        controller: 'EnvironmentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('environment');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Environment', function($stateParams, Environment) {
                        return Environment.get({id : $stateParams.id});
                    }]
                }
            })
            .state('environment.new', {
                parent: 'environment',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/environment/environment-dialog.html',
                        controller: 'EnvironmentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    environment_name: null,
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
                        $state.go('environment', null, { reload: true });
                    }, function() {
                        $state.go('environment');
                    })
                }]
            })
            .state('environment.edit', {
                parent: 'environment',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/environment/environment-dialog.html',
                        controller: 'EnvironmentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Environment', function(Environment) {
                                return Environment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('environment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
