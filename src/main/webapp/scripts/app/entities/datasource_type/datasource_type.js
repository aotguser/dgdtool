'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('datasource_type', {
                parent: 'entity',
                url: '/datasource_types',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.datasource_type.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/datasource_type/datasource_types.html',
                        controller: 'Datasource_typeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('datasource_type');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('datasource_type.detail', {
                parent: 'entity',
                url: '/datasource_type/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.datasource_type.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/datasource_type/datasource_type-detail.html',
                        controller: 'Datasource_typeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('datasource_type');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Datasource_type', function($stateParams, Datasource_type) {
                        return Datasource_type.get({id : $stateParams.id});
                    }]
                }
            })
            .state('datasource_type.new', {
                parent: 'datasource_type',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/datasource_type/datasource_type-dialog.html',
                        controller: 'Datasource_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    datasource_type_name: null,
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
                        $state.go('datasource_type', null, { reload: true });
                    }, function() {
                        $state.go('datasource_type');
                    })
                }]
            })
            .state('datasource_type.edit', {
                parent: 'datasource_type',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/datasource_type/datasource_type-dialog.html',
                        controller: 'Datasource_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Datasource_type', function(Datasource_type) {
                                return Datasource_type.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('datasource_type', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
