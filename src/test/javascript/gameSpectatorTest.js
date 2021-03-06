describe('gameSpectator',function(){

   var rootId = "canvas";

    beforeEach(function(){
        var container = document.createElement('div');
        container.setAttribute('id', rootId);
        document.body.appendChild(container);
    });


    afterEach(function(){
        emptyCanvas();
    });

    describe('Ending of each round', function() {
        it('should clear the screen',function() {
            var racerData = {
                                 ID:        "aaa",
                                 position :  0,
                                 scars:      0,
                                 email:      "aaa@gmail.com"
                            };
            $('#canvas').html(createPlayerDiv(racerData));
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

            $('#canvas').html(createPlayerDiv(racerData));
            expect(document.getElementById("racerName").innerHTML).toBe("ID: aaa");
            expect(document.getElementById("racerEmail").innerHTML).toBe("Email: aaa@gmail.com");
            expect(document.getElementById("racerDist").innerHTML).toBe("Dist: 0");
            expect(document.getElementById("scar").innerHTML).toBe("Scar: 0");
        });
    });


    describe('Multi player', function (){
        it('should show correct details for 2 players when server responds', function(){
            var racerData =  [{
                                ID:        "player1",
                                position :  0,
                                scars:      0,
                                email:      "p1@gmail.com"
                            },{
                                ID:        "player2",
                                position :  1,
                                scars:      1,
                                email:      "p2@gmail.com"
                            }];

            updateCanvas(racerData);

            $('.racer').each(function(key, value){
                var racerName = $(this).find('#racerName').text();
                var racerDist = $(this).find('#racerDist').text();
                var racerScars = $(this).find('#scar').text();
                var racerEmail = $(this).find('#racerEmail').text();

                expect(racerName).toBe('ID: ' + racerData[key].ID);
                expect(racerDist).toBe('Dist: ' + racerData[key].position);
                expect(racerScars).toBe('Scar: ' + racerData[key].scars);
                expect(racerEmail).toBe('Email: ' + racerData[key].email);
            });

        });
    });
});


