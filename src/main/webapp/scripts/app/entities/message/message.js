'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('message', {
                parent: 'entity',
                url: '/messages',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.message.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/message/messages.html',
                        controller: 'MessageController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('message');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('message.detail', {
                parent: 'entity',
                url: '/message/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.message.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/message/message-detail.html',
                        controller: 'MessageDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('message');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Message', function($stateParams, Message) {
                        return Message.get({id : $stateParams.id});
                    }]
                }
            })
            .state('message.new', {
                parent: 'message',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/message/message-dialog.html',
                        controller: 'MessageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    sender_id: null,
                                    recipient_id: null,
                                    sender_deleted: null,
                                    recipient_deleted: null,
                                    recipient_viewed: null,
                                    message: null,
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
                        $state.go('message', null, { reload: true });
                    }, function() {
                        $state.go('message');
                    })
                }]
            })
            .state('message.edit', {
                parent: 'message',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/message/message-dialog.html',
                        controller: 'MessageDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Message', function(Message) {
                                return Message.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('message', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });