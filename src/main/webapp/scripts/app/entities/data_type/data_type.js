'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('data_type', {
                parent: 'entity',
                url: '/data_types',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.data_type.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/data_type/data_types.html',
                        controller: 'Data_typeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('data_type');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('data_type.detail', {
                parent: 'entity',
                url: '/data_type/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.data_type.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/data_type/data_type-detail.html',
                        controller: 'Data_typeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('data_type');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Data_type', function($stateParams, Data_type) {
                        return Data_type.get({id : $stateParams.id});
                    }]
                }
            })
            .state('data_type.new', {
                parent: 'data_type',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/data_type/data_type-dialog.html',
                        controller: 'Data_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    data_type_name: null,
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
                        $state.go('data_type', null, { reload: true });
                    }, function() {
                        $state.go('data_type');
                    })
                }]
            })
            .state('data_type.edit', {
                parent: 'data_type',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/data_type/data_type-dialog.html',
                        controller: 'Data_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Data_type', function(Data_type) {
                                return Data_type.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('data_type', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
