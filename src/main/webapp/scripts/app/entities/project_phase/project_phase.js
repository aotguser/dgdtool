'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('project_phase', {
                parent: 'entity',
                url: '/project_phases',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.project_phase.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/project_phase/project_phases.html',
                        controller: 'Project_phaseController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('project_phase');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('project_phase.detail', {
                parent: 'entity',
                url: '/project_phase/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.project_phase.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/project_phase/project_phase-detail.html',
                        controller: 'Project_phaseDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('project_phase');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Project_phase', function($stateParams, Project_phase) {
                        return Project_phase.get({id : $stateParams.id});
                    }]
                }
            })
            .state('project_phase.new', {
                parent: 'project_phase',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/project_phase/project_phase-dialog.html',
                        controller: 'Project_phaseDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    project_phase_name: null,
                                    description: null,
                                    status_id: null,
                                    created_by: null,
                                    modified_by: null,
                                    modified_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('project_phase', null, { reload: true });
                    }, function() {
                        $state.go('project_phase');
                    })
                }]
            })
            .state('project_phase.edit', {
                parent: 'project_phase',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/project_phase/project_phase-dialog.html',
                        controller: 'Project_phaseDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Project_phase', function(Project_phase) {
                                return Project_phase.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('project_phase', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
