'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('package_type', {
                parent: 'entity',
                url: '/package_types',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.package_type.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/package_type/package_types.html',
                        controller: 'Package_typeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('package_type');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('package_type.detail', {
                parent: 'entity',
                url: '/package_type/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.package_type.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/package_type/package_type-detail.html',
                        controller: 'Package_typeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('package_type');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Package_type', function($stateParams, Package_type) {
                        return Package_type.get({id : $stateParams.id});
                    }]
                }
            })
            .state('package_type.new', {
                parent: 'package_type',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/package_type/package_type-dialog.html',
                        controller: 'Package_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    package_type_name: null,
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
                        $state.go('package_type', null, { reload: true });
                    }, function() {
                        $state.go('package_type');
                    })
                }]
            })
            .state('package_type.edit', {
                parent: 'package_type',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/package_type/package_type-dialog.html',
                        controller: 'Package_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Package_type', function(Package_type) {
                                return Package_type.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('package_type', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
