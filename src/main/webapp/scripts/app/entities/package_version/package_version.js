'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('package_version', {
                parent: 'entity',
                url: '/package_versions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.package_version.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/package_version/package_versions.html',
                        controller: 'Package_versionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('package_version');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('package_version.detail', {
                parent: 'entity',
                url: '/package_version/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.package_version.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/package_version/package_version-detail.html',
                        controller: 'Package_versionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('package_version');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Package_version', function($stateParams, Package_version) {
                        return Package_version.get({id : $stateParams.id});
                    }]
                }
            })
            .state('package_version.new', {
                parent: 'package_version',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/package_version/package_version-dialog.html',
                        controller: 'Package_versionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    package_version_name: null,
                                    package_id: null,
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
                        $state.go('package_version', null, { reload: true });
                    }, function() {
                        $state.go('package_version');
                    })
                }]
            })
            .state('package_version.edit', {
                parent: 'package_version',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/package_version/package_version-dialog.html',
                        controller: 'Package_versionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Package_version', function(Package_version) {
                                return Package_version.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('package_version', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
