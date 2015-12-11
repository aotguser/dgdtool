'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('stage', {
                parent: 'entity',
                url: '/stages',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.stage.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stage/stages.html',
                        controller: 'StageController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stage');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('stage.detail', {
                parent: 'entity',
                url: '/stage/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.stage.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/stage/stage-detail.html',
                        controller: 'StageDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('stage');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Stage', function($stateParams, Stage) {
                        return Stage.get({id : $stateParams.id});
                    }]
                }
            })
            .state('stage.new', {
                parent: 'stage',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/stage/stage-dialog.html',
                        controller: 'StageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    stage_name: null,
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
                        $state.go('stage', null, { reload: true });
                    }, function() {
                        $state.go('stage');
                    })
                }]
            })
            .state('stage.edit', {
                parent: 'stage',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/stage/stage-dialog.html',
                        controller: 'StageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Stage', function(Stage) {
                                return Stage.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('stage', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
