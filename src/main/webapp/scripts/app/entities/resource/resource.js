'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('resource', {
                parent: 'entity',
                url: '/resources',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.resource.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/resource/resources.html',
                        controller: 'ResourceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('resource');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('resource.detail', {
                parent: 'entity',
                url: '/resource/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.resource.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/resource/resource-detail.html',
                        controller: 'ResourceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('resource');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Resource', function($stateParams, Resource) {
                        return Resource.get({id : $stateParams.id});
                    }]
                }
            })
            .state('resource.new', {
                parent: 'resource',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/resource/resource-dialog.html',
                        controller: 'ResourceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    resource_name: null,
                                    resource_number: null,
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
                        $state.go('resource', null, { reload: true });
                    }, function() {
                        $state.go('resource');
                    })
                }]
            })
            .state('resource.edit', {
                parent: 'resource',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/resource/resource-dialog.html',
                        controller: 'ResourceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Resource', function(Resource) {
                                return Resource.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('resource', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
