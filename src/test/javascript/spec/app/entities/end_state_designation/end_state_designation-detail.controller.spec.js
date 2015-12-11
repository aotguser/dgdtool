'use strict';

describe('End_state_designation Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockEnd_state_designation;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockEnd_state_designation = jasmine.createSpy('MockEnd_state_designation');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'End_state_designation': MockEnd_state_designation
        };
        createController = function() {
            $injector.get('$controller')("End_state_designationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'dgdtoolApp:end_state_designationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
