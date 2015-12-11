'use strict';

describe('Asset_type Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAsset_type;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAsset_type = jasmine.createSpy('MockAsset_type');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Asset_type': MockAsset_type
        };
        createController = function() {
            $injector.get('$controller')("Asset_typeDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'dgdtoolApp:asset_typeUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
