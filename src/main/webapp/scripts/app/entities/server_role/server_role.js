'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('server_role', {
                parent: 'entity',
                url: '/server_roles',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.server_role.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/server_role/server_roles.html',
                        controller: 'Server_roleController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('server_role');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('server_role.detail', {
                parent: 'entity',
                url: '/server_role/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.server_role.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/server_role/server_role-detail.html',
                        controller: 'Server_roleDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('server_role');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Server_role', function($stateParams, Server_role) {
                        return Server_role.get({id : $stateParams.id});
                    }]
                }
            })
            .state('server_role.new', {
                parent: 'server_role',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/server_role/server_role-dialog.html',
                        controller: 'Server_roleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    server_role_name: null,
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
                        $state.go('server_role', null, { reload: true });
                    }, function() {
                        $state.go('server_role');
                    })
                }]
            })
            .state('server_role.edit', {
                parent: 'server_role',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/server_role/server_role-dialog.html',
                        controller: 'Server_roleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Server_role', function(Server_role) {
                                return Server_role.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('server_role', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
