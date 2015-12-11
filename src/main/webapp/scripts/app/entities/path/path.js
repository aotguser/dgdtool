'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('path', {
                parent: 'entity',
                url: '/paths',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.path.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/path/paths.html',
                        controller: 'PathController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('path');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('path.detail', {
                parent: 'entity',
                url: '/path/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.path.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/path/path-detail.html',
                        controller: 'PathDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('path');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Path', function($stateParams, Path) {
                        return Path.get({id : $stateParams.id});
                    }]
                }
            })
            .state('path.new', {
                parent: 'path',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/path/path-dialog.html',
                        controller: 'PathDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    path_name: null,
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
                        $state.go('path', null, { reload: true });
                    }, function() {
                        $state.go('path');
                    })
                }]
            })
            .state('path.edit', {
                parent: 'path',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/path/path-dialog.html',
                        controller: 'PathDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Path', function(Path) {
                                return Path.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('path', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
