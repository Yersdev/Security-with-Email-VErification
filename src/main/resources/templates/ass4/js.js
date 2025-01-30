const questions = [
    {
        question: "Who has won the most Ballon d'Or awards?",
        answers: [
            {text: "Lionel Messi", correct: true},
            {text: "Cristiano Ronaldo", correct: false},
            {text: "Johan Cruyff", correct: false},
            {text: "Michel Platini", correct: false}
        ]
    },
    {
        question: "Which country has the most World Cup titles?",
        answers: [
            {text: "Germany", correct: false},
            {text: "Italy", correct: false},
            {text: "Brazil", correct: true},
            {text: "Argentina", correct: false}
        ]
    },
    {
        question: "Who is the all-time top scorer in the UEFA Champions League?",
        answers: [
            {text: "Raúl", correct: false},
            {text: "Cristiano Ronaldo", correct: true},
            {text: "Lionel Messi", correct: false},
            {text: "Roberto Baggio", correct: false}
        ]
    },
    {
        question: "Which club has won the most UEFA Champions League titles?",
        answers: [
            {text: "AC Milan", correct: false},
            {text: "Liverpool", correct: false},
            {text: "Real Madrid", correct: true},
            {text: "Barcelona", correct: false}
        ]
    },
    {
        question: "Who is known as 'The King of Football'?",
        answers: [
            {text: "Pele", correct: true},
            {text: "Diego Maradona", correct: false},
            {text: "Lionel Messi", correct: false},
            {text: "Cristiano Ronaldo", correct: false}
        ]
    },
    {
        question: "Who holds the record for the most goals in a single Premier League season?",
        answers: [
            {text: "Sergio Agüero", correct: false},
            {text: "Mohamed Salah", correct: true},
            {text: "Alan Shearer", correct: false},
            {text: "Wayne Rooney", correct: false}
        ]
    },
    {
        question: "Which player has the most international caps?",
        answers: [
            {text: "Cristiano Ronaldo", correct: false},
            {text: "Liam Brady", correct: false},
            {text: "Bader Al-Mutawa", correct: true},
            {text: "Gianluigi Buffon", correct: false}
        ]
    },
    {
        question: "Who was the first player to score in a World Cup final?",
        answers: [
            {text: "Pele", correct: false},
            {text: "Geoff Hurst", correct: true},
            {text: "Zinedine Zidane", correct: false},
            {text: "Diego Maradona", correct: false}
        ]
    },
    {
        question: "Which country hosted the 2014 FIFA World Cup?",
        answers: [
            {text: "South Africa", correct: false},
            {text: "Brazil", correct: true},
            {text: "Germany", correct: false},
            {text: "Russia", correct: false}
        ]
    },
    {
        question: "Who is the only player to have won the FIFA World Cup three times?",
        answers: [
            {text: "Pele", correct: true},
            {text: "Diego Maradona", correct: false},
            {text: "Lionel Messi", correct: false},
            {text: "Cristiano Ronaldo", correct: false}
        ]
    },
];


const questionElement = document.getElementById("question");
const answerButtonsElement = document.getElementById("answer-buttons");
const nextButton = document.getElementById("next-btn");
const loginContainer = document.getElementById("login-container");
const startGameButton = document.getElementById("start-game-btn");
const usernameInput = document.getElementById("username");

let currentQuestionIndex = 0;
let score = 0;

startGameButton.addEventListener("click", () => {
    const username = usernameInput.value.trim();
    if (username) {
        loginContainer.style.display = "none"; 
        document.querySelector(".quiz").style.display = "block";
        startQuiz(); 
    } else {
        alert("Please enter your name!");
    }
});

function startQuiz() {
    currentQuestionIndex = 0;
    score = 0;
    nextButton.style.display = "none";
    showQuestion();
}

function showQuestion() {
    resetState();
    let currentQuestion = questions[currentQuestionIndex];
    questionElement.innerHTML = currentQuestion.question;

    currentQuestion.answers.forEach(answer => {
        const button = document.createElement("button");
        button.innerHTML = answer.text;
        button.classList.add("btn");
        answerButtonsElement.appendChild(button);
        if (answer.correct) {
            button.dataset.correct = answer.correct;
        }
        button.addEventListener("click", selectAnswer);
    });
}

function resetState() {
    nextButton.style.display = "none";
    while (answerButtonsElement.firstChild) {
        answerButtonsElement.removeChild(answerButtonsElement.firstChild);
    }
}

function selectAnswer(e) {
    const selectedBtn = e.target;
    const isCorrect = selectedBtn.dataset.correct === "true";
    
    if (isCorrect) {
        selectedBtn.classList.add("correct");
        score++;
    } else {
        selectedBtn.classList.add("incorrect");
    }

    Array.from(answerButtonsElement.children).forEach(button => {
        if (button.dataset.correct === "true") {
            button.classList.add("correct");
        }
        button.disabled = true;
    });

    nextButton.style.display = "block";
}

nextButton.addEventListener("click", () => {
    if (currentQuestionIndex < questions.length - 1) {
        currentQuestionIndex++;
        showQuestion();
    } else {
        showScore();
    }
});

function showScore() {
    resetState();
    questionElement.innerHTML = `Your score: ${score}/${questions.length}`;
    nextButton.innerHTML = "Play Again";
    nextButton.style.display = "block"; 
    nextButton.addEventListener("click", startQuiz);
}

