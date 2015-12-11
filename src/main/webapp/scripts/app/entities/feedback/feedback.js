'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('feedback', {
                parent: 'entity',
                url: '/feedbacks',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.feedback.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/feedback/feedbacks.html',
                        controller: 'FeedbackController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('feedback');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('feedback.detail', {
                parent: 'entity',
                url: '/feedback/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.feedback.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/feedback/feedback-detail.html',
                        controller: 'FeedbackDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('feedback');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Feedback', function($stateParams, Feedback) {
                        return Feedback.get({id : $stateParams.id});
                    }]
                }
            })
            .state('feedback.new', {
                parent: 'feedback',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feedback/feedback-dialog.html',
                        controller: 'FeedbackDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    feedback_type_id: null,
                                    feedback_name: null,
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
                        $state.go('feedback', null, { reload: true });
                    }, function() {
                        $state.go('feedback');
                    })
                }]
            })
            .state('feedback.edit', {
                parent: 'feedback',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/feedback/feedback-dialog.html',
                        controller: 'FeedbackDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Feedback', function(Feedback) {
                                return Feedback.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('feedback', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
