'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('server_type', {
                parent: 'entity',
                url: '/server_types',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.server_type.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/server_type/server_types.html',
                        controller: 'Server_typeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('server_type');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('server_type.detail', {
                parent: 'entity',
                url: '/server_type/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.server_type.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/server_type/server_type-detail.html',
                        controller: 'Server_typeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('server_type');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Server_type', function($stateParams, Server_type) {
                        return Server_type.get({id : $stateParams.id});
                    }]
                }
            })
            .state('server_type.new', {
                parent: 'server_type',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/server_type/server_type-dialog.html',
                        controller: 'Server_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    server_type_name: null,
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
                        $state.go('server_type', null, { reload: true });
                    }, function() {
                        $state.go('server_type');
                    })
                }]
            })
            .state('server_type.edit', {
                parent: 'server_type',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/server_type/server_type-dialog.html',
                        controller: 'Server_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Server_type', function(Server_type) {
                                return Server_type.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('server_type', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
