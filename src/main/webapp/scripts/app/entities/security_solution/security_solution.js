'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('security_solution', {
                parent: 'entity',
                url: '/security_solutions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.security_solution.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/security_solution/security_solutions.html',
                        controller: 'Security_solutionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('security_solution');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('security_solution.detail', {
                parent: 'entity',
                url: '/security_solution/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.security_solution.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/security_solution/security_solution-detail.html',
                        controller: 'Security_solutionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('security_solution');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Security_solution', function($stateParams, Security_solution) {
                        return Security_solution.get({id : $stateParams.id});
                    }]
                }
            })
            .state('security_solution.new', {
                parent: 'security_solution',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/security_solution/security_solution-dialog.html',
                        controller: 'Security_solutionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    security_solution_name: null,
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
                        $state.go('security_solution', null, { reload: true });
                    }, function() {
                        $state.go('security_solution');
                    })
                }]
            })
            .state('security_solution.edit', {
                parent: 'security_solution',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/security_solution/security_solution-dialog.html',
                        controller: 'Security_solutionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Security_solution', function(Security_solution) {
                                return Security_solution.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('security_solution', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
