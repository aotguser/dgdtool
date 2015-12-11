'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('os_type', {
                parent: 'entity',
                url: '/os_types',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.os_type.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/os_type/os_types.html',
                        controller: 'Os_typeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('os_type');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('os_type.detail', {
                parent: 'entity',
                url: '/os_type/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.os_type.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/os_type/os_type-detail.html',
                        controller: 'Os_typeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('os_type');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Os_type', function($stateParams, Os_type) {
                        return Os_type.get({id : $stateParams.id});
                    }]
                }
            })
            .state('os_type.new', {
                parent: 'os_type',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/os_type/os_type-dialog.html',
                        controller: 'Os_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    os_type_name: null,
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
                        $state.go('os_type', null, { reload: true });
                    }, function() {
                        $state.go('os_type');
                    })
                }]
            })
            .state('os_type.edit', {
                parent: 'os_type',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/os_type/os_type-dialog.html',
                        controller: 'Os_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Os_type', function(Os_type) {
                                return Os_type.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('os_type', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
