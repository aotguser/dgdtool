'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('feedback_type', {
                parent: 'entity',
                url: '/feedback_types',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.feedback_type.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/feedback_type/feedback_types.html',
                        controller: 'Feedback_typeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('feedback_type');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('feedback_type.detail', {
                parent: 'entity',
                url: '/feedback_type/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.feedback_type.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/feedback_type/feedback_type-detail.html',
                        controller: 'Feedback_typeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('feedback_type');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Feedback_type', function($stateParams, Feedback_type) {
                        return Feedback_type.get({id : $stateParams.id});
                    }]
                }
            })
            .state('feedback_type.new', {
                parent: 'feedback_type',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feedback_type/feedback_type-dialog.html',
                        controller: 'Feedback_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    feedback_type_name: null,
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
                        $state.go('feedback_type', null, { reload: true });
                    }, function() {
                        $state.go('feedback_type');
                    })
                }]
            })
            .state('feedback_type.edit', {
                parent: 'feedback_type',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feedback_type/feedback_type-dialog.html',
                        controller: 'Feedback_typeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Feedback_type', function(Feedback_type) {
                                return Feedback_type.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('feedback_type', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
