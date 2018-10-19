var questionInput = document.getElementById("question-input");

var unswerInput = document.getElementById("unswer-input");

var unswerOutput = document.getElementById("unswer-output");

var question = document.getElementById("question-btn");

var answer = document.getElementById("answer-btn");

function getUnswer() {


    question.addEventListener('click', function () {
        axios.get('/question', {
            params: {question: questionInput.value}
        })
            .then(function (result) {
                var answer = result.data.answer;
                document.getElementById("answer").value = answer;

            })
            .catch(function (reason) {
                console.error(reason);
            })

    })

    answer.addEventListener('click', function () {
        axios.post()
    })
