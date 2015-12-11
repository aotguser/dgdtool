'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('decision', {
                parent: 'entity',
                url: '/decisions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.decision.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/decision/decisions.html',
                        controller: 'DecisionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('decision');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('decision.detail', {
                parent: 'entity',
                url: '/decision/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.decision.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/decision/decision-detail.html',
                        controller: 'DecisionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('decision');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Decision', function($stateParams, Decision) {
                        return Decision.get({id : $stateParams.id});
                    }]
                }
            })
            .state('decision.new', {
                parent: 'decision',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/decision/decision-dialog.html',
                        controller: 'DecisionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    decision_name: null,
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
                        $state.go('decision', null, { reload: true });
                    }, function() {
                        $state.go('decision');
                    })
                }]
            })
            .state('decision.edit', {
                parent: 'decision',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/decision/decision-dialog.html',
                        controller: 'DecisionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Decision', function(Decision) {
                                return Decision.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('decision', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
