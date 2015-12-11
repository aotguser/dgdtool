'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('initiative', {
                parent: 'entity',
                url: '/initiatives',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.initiative.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/initiative/initiatives.html',
                        controller: 'InitiativeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('initiative');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('initiative.detail', {
                parent: 'entity',
                url: '/initiative/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.initiative.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/initiative/initiative-detail.html',
                        controller: 'InitiativeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('initiative');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Initiative', function($stateParams, Initiative) {
                        return Initiative.get({id : $stateParams.id});
                    }]
                }
            })
            .state('initiative.new', {
                parent: 'initiative',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/initiative/initiative-dialog.html',
                        controller: 'InitiativeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    initiative_name: null,
                                    initiative_type_id: null,
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
                        $state.go('initiative', null, { reload: true });
                    }, function() {
                        $state.go('initiative');
                    })
                }]
            })
            .state('initiative.edit', {
                parent: 'initiative',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/initiative/initiative-dialog.html',
                        controller: 'InitiativeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Initiative', function(Initiative) {
                                return Initiative.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('initiative', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
