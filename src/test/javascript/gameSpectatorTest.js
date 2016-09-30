describe('gameSpectator',function(){

   var rootId = "canvas";

    beforeEach(function(){
        var container = document.createElement('div');
        container.setAttribute('id', rootId);
        document.body.appendChild(container);
    });

    describe('Ending of each round', function() {
        it('should clear the screen',function() {
            var racerData = {
                                 ID:        "aaa",
                                 position :  0,
                                 scars:      0,
                                 email:      "aaa@gmail.com"
                            };
            $('#canvas').html(updatePlayerPosition(racerData));
            emptyCanvas();
            expect($('#canvas').html()).toBe("");
        });
    });

    describe('Single player', function() {
        it('should show 1 racer on screen when server responds with 1 player',function() {
            var racerData = {
                                 ID:        "aaa",
                                 position :  0,
                                 scars:      0,
                                 email:      "aaa@gmail.com"
                            };
            $('#canvas').html(updatePlayerPosition(racerData));
            expect(document.getElementById("racerName").innerHTML).toBe("ID: aaa");
            expect(document.getElementById("racerEmail").innerHTML).toBe("Email: aaa@gmail.com");
            expect(document.getElementById("racerDist").innerHTML).toBe("Dist: 0");
            expect(document.getElementById("scar").innerHTML).toBe("Scar: 0");
        });
    });
});
