'use strict';

describe('Datasource_type Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDatasource_type;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDatasource_type = jasmine.createSpy('MockDatasource_type');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Datasource_type': MockDatasource_type
        };
        createController = function() {
            $injector.get('$controller')("Datasource_typeDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'dgdtoolApp:datasource_typeUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
