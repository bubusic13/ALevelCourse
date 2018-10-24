var questionInput = document.getElementById('question-input');

var answerInput = document.getElementById('answer-input');

var question = document.getElementById('question-btn');


question.addEventListener('click', function () {

    if (questionInput != "" && answerInput.style.display == 'none') {
        axios.get('question', {
            params: {question: questionInput.value}
        })
            .then(function (result) {
                var answer = result.data.answer;
                answerInput.style.display = 'block';
                answerInput.value = answer;
            })
            .catch(function (reason) {
                reason.status == 500;
                answerInput.style.display = 'block';
            })
    }
    else if (answerInput != "") {
        var inputQuestion = questionInput.value;
        var inputAnswer = answerInput.value;
        var questionAnswer = {
            question: inputQuestion,
            answer: inputAnswer
        };
        axios.post('question', questionAnswer)
            .then(function () {

            })
            .catch(function (reason) {
                console.error(reason);
            });
    }
});



