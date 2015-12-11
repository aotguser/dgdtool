'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('statuses', {
                parent: 'entity',
                url: '/statusess',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.statuses.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/statuses/statusess.html',
                        controller: 'StatusesController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('statuses');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('statuses.detail', {
                parent: 'entity',
                url: '/statuses/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.statuses.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/statuses/statuses-detail.html',
                        controller: 'StatusesDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('statuses');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Statuses', function($stateParams, Statuses) {
                        return Statuses.get({id : $stateParams.id});
                    }]
                }
            })
            .state('statuses.new', {
                parent: 'statuses',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/statuses/statuses-dialog.html',
                        controller: 'StatusesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    status_type_id: null,
                                    status_name: null,
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
                        $state.go('statuses', null, { reload: true });
                    }, function() {
                        $state.go('statuses');
                    })
                }]
            })
            .state('statuses.edit', {
                parent: 'statuses',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/statuses/statuses-dialog.html',
                        controller: 'StatusesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Statuses', function(Statuses) {
                                return Statuses.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('statuses', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
