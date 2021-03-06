'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('service', {
                parent: 'entity',
                url: '/services',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.service.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/service/services.html',
                        controller: 'ServiceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('service');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('service.detail', {
                parent: 'entity',
                url: '/service/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.service.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/service/service-detail.html',
                        controller: 'ServiceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('service');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Service', function($stateParams, Service) {
                        return Service.get({id : $stateParams.id});
                    }]
                }
            })
            .state('service.new', {
                parent: 'service',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/service/service-dialog.html',
                        controller: 'ServiceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    service_name: null,
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
                        $state.go('service', null, { reload: true });
                    }, function() {
                        $state.go('service');
                    })
                }]
            })
            .state('service.edit', {
                parent: 'service',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/service/service-dialog.html',
                        controller: 'ServiceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Service', function(Service) {
                                return Service.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('service', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
