var app = (function(){

    function sendWord(){
        var wordToInsert = document.getElementById("inputWord").value;
        console.log(wordToInsert);
        $.ajax({
            url: "http://localhost:35000/add?word="+wordToInsert,
            type:'POST'
        }).then(function(data) {
            alert(response.status);
        });
    }

    return{
        send: sendWord
    };

})();