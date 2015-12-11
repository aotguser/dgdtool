'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('data_state', {
                parent: 'entity',
                url: '/data_states',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.data_state.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/data_state/data_states.html',
                        controller: 'Data_stateController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('data_state');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('data_state.detail', {
                parent: 'entity',
                url: '/data_state/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.data_state.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/data_state/data_state-detail.html',
                        controller: 'Data_stateDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('data_state');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Data_state', function($stateParams, Data_state) {
                        return Data_state.get({id : $stateParams.id});
                    }]
                }
            })
            .state('data_state.new', {
                parent: 'data_state',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/data_state/data_state-dialog.html',
                        controller: 'Data_stateDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    data_state_name: null,
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
                        $state.go('data_state', null, { reload: true });
                    }, function() {
                        $state.go('data_state');
                    })
                }]
            })
            .state('data_state.edit', {
                parent: 'data_state',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/data_state/data_state-dialog.html',
                        controller: 'Data_stateDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Data_state', function(Data_state) {
                                return Data_state.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('data_state', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
