'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('project_team', {
                parent: 'entity',
                url: '/project_teams',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.project_team.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/project_team/project_teams.html',
                        controller: 'Project_teamController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('project_team');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('project_team.detail', {
                parent: 'entity',
                url: '/project_team/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.project_team.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/project_team/project_team-detail.html',
                        controller: 'Project_teamDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('project_team');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Project_team', function($stateParams, Project_team) {
                        return Project_team.get({id : $stateParams.id});
                    }]
                }
            })
            .state('project_team.new', {
                parent: 'project_team',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/project_team/project_team-dialog.html',
                        controller: 'Project_teamDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    project_id: null,
                                    resource_id: null,
                                    role_id: null,
                                    support_level: null,
                                    est_hours: null,
                                    assigned_date: null,
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
                        $state.go('project_team', null, { reload: true });
                    }, function() {
                        $state.go('project_team');
                    })
                }]
            })
            .state('project_team.edit', {
                parent: 'project_team',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/project_team/project_team-dialog.html',
                        controller: 'Project_teamDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Project_team', function(Project_team) {
                                return Project_team.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('project_team', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
