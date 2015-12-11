'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('license_type', {
                parent: 'entity',
                url: '/license_types',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.license_type.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/license_type/license_types.html',
                        controller: 'License_typeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('license_type');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('license_type.detail', {
                parent: 'entity',
                url: '/license_type/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.license_type.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/license_type/license_type-detail.html',
                        controller: 'License_typeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('license_type');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'License_type', function($stateParams, License_type) {
                        return License_type.get({id : $stateParams.id});
                    }]
                }
            })
            .state('license_type.new', {
                parent: 'license_type',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/license_type/license_type-dialog.html',
                        controller: 'License_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    license_type_name: null,
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
                        $state.go('license_type', null, { reload: true });
                    }, function() {
                        $state.go('license_type');
                    })
                }]
            })
            .state('license_type.edit', {
                parent: 'license_type',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/license_type/license_type-dialog.html',
                        controller: 'License_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['License_type', function(License_type) {
                                return License_type.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('license_type', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
