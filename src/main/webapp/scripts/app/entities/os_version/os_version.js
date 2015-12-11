'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('os_version', {
                parent: 'entity',
                url: '/os_versions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.os_version.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/os_version/os_versions.html',
                        controller: 'Os_versionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('os_version');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('os_version.detail', {
                parent: 'entity',
                url: '/os_version/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.os_version.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/os_version/os_version-detail.html',
                        controller: 'Os_versionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('os_version');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Os_version', function($stateParams, Os_version) {
                        return Os_version.get({id : $stateParams.id});
                    }]
                }
            })
            .state('os_version.new', {
                parent: 'os_version',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/os_version/os_version-dialog.html',
                        controller: 'Os_versionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    os_version_name: null,
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
                        $state.go('os_version', null, { reload: true });
                    }, function() {
                        $state.go('os_version');
                    })
                }]
            })
            .state('os_version.edit', {
                parent: 'os_version',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/os_version/os_version-dialog.html',
                        controller: 'Os_versionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Os_version', function(Os_version) {
                                return Os_version.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('os_version', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
