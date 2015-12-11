'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('frequency', {
                parent: 'entity',
                url: '/frequencys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.frequency.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/frequency/frequencys.html',
                        controller: 'FrequencyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('frequency');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('frequency.detail', {
                parent: 'entity',
                url: '/frequency/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.frequency.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/frequency/frequency-detail.html',
                        controller: 'FrequencyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('frequency');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Frequency', function($stateParams, Frequency) {
                        return Frequency.get({id : $stateParams.id});
                    }]
                }
            })
            .state('frequency.new', {
                parent: 'frequency',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/frequency/frequency-dialog.html',
                        controller: 'FrequencyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    frequency_name: null,
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
                        $state.go('frequency', null, { reload: true });
                    }, function() {
                        $state.go('frequency');
                    })
                }]
            })
            .state('frequency.edit', {
                parent: 'frequency',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/frequency/frequency-dialog.html',
                        controller: 'FrequencyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Frequency', function(Frequency) {
                                return Frequency.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('frequency', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
