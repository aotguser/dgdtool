'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('field_type', {
                parent: 'entity',
                url: '/field_types',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.field_type.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/field_type/field_types.html',
                        controller: 'Field_typeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('field_type');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('field_type.detail', {
                parent: 'entity',
                url: '/field_type/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.field_type.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/field_type/field_type-detail.html',
                        controller: 'Field_typeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('field_type');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Field_type', function($stateParams, Field_type) {
                        return Field_type.get({id : $stateParams.id});
                    }]
                }
            })
            .state('field_type.new', {
                parent: 'field_type',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/field_type/field_type-dialog.html',
                        controller: 'Field_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    field_type_name: null,
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
                        $state.go('field_type', null, { reload: true });
                    }, function() {
                        $state.go('field_type');
                    })
                }]
            })
            .state('field_type.edit', {
                parent: 'field_type',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/field_type/field_type-dialog.html',
                        controller: 'Field_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Field_type', function(Field_type) {
                                return Field_type.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('field_type', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
