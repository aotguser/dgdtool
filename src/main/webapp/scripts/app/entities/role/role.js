'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('role', {
                parent: 'entity',
                url: '/roles',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.role.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/role/roles.html',
                        controller: 'RoleController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('role');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('role.detail', {
                parent: 'entity',
                url: '/role/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.role.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/role/role-detail.html',
                        controller: 'RoleDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('role');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Role', function($stateParams, Role) {
                        return Role.get({id : $stateParams.id});
                    }]
                }
            })
            .state('role.new', {
                parent: 'role',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/role/role-dialog.html',
                        controller: 'RoleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    role_name: null,
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
                        $state.go('role', null, { reload: true });
                    }, function() {
                        $state.go('role');
                    })
                }]
            })
            .state('role.edit', {
                parent: 'role',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/role/role-dialog.html',
                        controller: 'RoleDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Role', function(Role) {
                                return Role.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('role', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
