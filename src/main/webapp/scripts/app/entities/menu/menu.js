'use strict';

angular.module('dgdtoolApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('menu', {
                parent: 'entity',
                url: '/menus',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.menu.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/menu/menus.html',
                        controller: 'MenuController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('menu');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('menu.detail', {
                parent: 'entity',
                url: '/menu/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'dgdtoolApp.menu.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/menu/menu-detail.html',
                        controller: 'MenuDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('menu');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Menu', function($stateParams, Menu) {
                        return Menu.get({id : $stateParams.id});
                    }]
                }
            })
            .state('menu.new', {
                parent: 'menu',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/menu/menu-dialog.html',
                        controller: 'MenuDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    parent_menu_id: null,
                                    menu_item_name: null,
                                    controller_url: null,
                                    display_order: null,
                                    menu_hint: null,
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
                        $state.go('menu', null, { reload: true });
                    }, function() {
                        $state.go('menu');
                    })
                }]
            })
            .state('menu.edit', {
                parent: 'menu',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/menu/menu-dialog.html',
                        controller: 'MenuDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Menu', function(Menu) {
                                return Menu.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('menu', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
