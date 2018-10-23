var questionInput = document.getElementById("question-input");

var answerInput = document.getElementById("answer-input");

var question = document.getElementById("question-btn");

var answer = document.getElementById("answer-btn");


    question.addEventListener('click', function () {
        axios.get('question', {
            params: {question:questionInput.value}
        })
            .then(function (result) {
                var answer = result.data.answer;
                document.getElementById("answer-output").value = answer;


            })
            .catch(function (reason) {
                console.error(reason);
            })
        document.getElementById('header').reset();

    });

    answer.addEventListener('click', function () {
            var inputQuestion = questionInput.value;
            var inputAnswer = answerInput.value;
            var questionAnswer = {
                question: inputQuestion,
                answer:  inputAnswer
            };
            axios.post('question', questionAnswer)
                .then(function () {

                })
                .catch(function (reason) {
                    console.error(reason);
                });
        document.getElementById('header').reset();
    });
