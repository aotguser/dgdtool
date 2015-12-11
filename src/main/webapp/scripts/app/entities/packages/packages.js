'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('packages', {
                parent: 'entity',
                url: '/packagess',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.packages.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/packages/packagess.html',
                        controller: 'PackagesController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('packages');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('packages.detail', {
                parent: 'entity',
                url: '/packages/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.packages.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/packages/packages-detail.html',
                        controller: 'PackagesDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('packages');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Packages', function($stateParams, Packages) {
                        return Packages.get({id : $stateParams.id});
                    }]
                }
            })
            .state('packages.new', {
                parent: 'packages',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/packages/packages-dialog.html',
                        controller: 'PackagesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    package_name: null,
                                    package_type_id: null,
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
                        $state.go('packages', null, { reload: true });
                    }, function() {
                        $state.go('packages');
                    })
                }]
            })
            .state('packages.edit', {
                parent: 'packages',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/packages/packages-dialog.html',
                        controller: 'PackagesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Packages', function(Packages) {
                                return Packages.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('packages', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
