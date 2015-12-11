'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('support_level', {
                parent: 'entity',
                url: '/support_levels',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.support_level.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/support_level/support_levels.html',
                        controller: 'Support_levelController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('support_level');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('support_level.detail', {
                parent: 'entity',
                url: '/support_level/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.support_level.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/support_level/support_level-detail.html',
                        controller: 'Support_levelDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('support_level');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Support_level', function($stateParams, Support_level) {
                        return Support_level.get({id : $stateParams.id});
                    }]
                }
            })
            .state('support_level.new', {
                parent: 'support_level',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/support_level/support_level-dialog.html',
                        controller: 'Support_levelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    support_level_name: null,
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
                        $state.go('support_level', null, { reload: true });
                    }, function() {
                        $state.go('support_level');
                    })
                }]
            })
            .state('support_level.edit', {
                parent: 'support_level',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/support_level/support_level-dialog.html',
                        controller: 'Support_levelDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Support_level', function(Support_level) {
                                return Support_level.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('support_level', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
