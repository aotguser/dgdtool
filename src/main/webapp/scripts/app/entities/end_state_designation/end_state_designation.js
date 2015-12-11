'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('end_state_designation', {
                parent: 'entity',
                url: '/end_state_designations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.end_state_designation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/end_state_designation/end_state_designations.html',
                        controller: 'End_state_designationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('end_state_designation');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('end_state_designation.detail', {
                parent: 'entity',
                url: '/end_state_designation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.end_state_designation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/end_state_designation/end_state_designation-detail.html',
                        controller: 'End_state_designationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('end_state_designation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'End_state_designation', function($stateParams, End_state_designation) {
                        return End_state_designation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('end_state_designation.new', {
                parent: 'end_state_designation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/end_state_designation/end_state_designation-dialog.html',
                        controller: 'End_state_designationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    end_state_designation_name: null,
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
                        $state.go('end_state_designation', null, { reload: true });
                    }, function() {
                        $state.go('end_state_designation');
                    })
                }]
            })
            .state('end_state_designation.edit', {
                parent: 'end_state_designation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/end_state_designation/end_state_designation-dialog.html',
                        controller: 'End_state_designationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['End_state_designation', function(End_state_designation) {
                                return End_state_designation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('end_state_designation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
