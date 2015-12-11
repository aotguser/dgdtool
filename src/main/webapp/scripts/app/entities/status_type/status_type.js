'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('status_type', {
                parent: 'entity',
                url: '/status_types',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.status_type.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/status_type/status_types.html',
                        controller: 'Status_typeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('status_type');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('status_type.detail', {
                parent: 'entity',
                url: '/status_type/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.status_type.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/status_type/status_type-detail.html',
                        controller: 'Status_typeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('status_type');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Status_type', function($stateParams, Status_type) {
                        return Status_type.get({id : $stateParams.id});
                    }]
                }
            })
            .state('status_type.new', {
                parent: 'status_type',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/status_type/status_type-dialog.html',
                        controller: 'Status_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    status_type_name: null,
                                    description: null,
                                    created_by: null,
                                    created_date: null,
                                    modified_by: null,
                                    modified_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('status_type', null, { reload: true });
                    }, function() {
                        $state.go('status_type');
                    })
                }]
            })
            .state('status_type.edit', {
                parent: 'status_type',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/status_type/status_type-dialog.html',
                        controller: 'Status_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Status_type', function(Status_type) {
                                return Status_type.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_type', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
