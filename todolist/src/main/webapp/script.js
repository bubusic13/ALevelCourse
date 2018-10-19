//Input field for user to create new todos
var todoInput = document.getElementById("todo-input");

//<ul> with todos
var todosHtml = document.getElementById("todos");

//Array of objects representing todos with (id, text, done) properties
//It serves as a model of our data on the front-end
var todos = [];

//Adds event listeners to buttons and text fields
function setupListeners() {
    //Pressing Enter on todoInput should add new item to the list
    todoInput.addEventListener('keyup', function (event) {
        if (event.key === 'Enter') {
            createTodo();
        }
    });
    //clicking on add button should add new item to the list
    document.getElementById('add').addEventListener('click', function () {
        createTodo();
    });
    //clicking on clear button should remove completed todos from the list
    document.getElementById('clear').addEventListener('click', function () {
        deleteDone();
    });
}

//When page loads, we send GET request to the server
axios.get('todo')
    .then(function (result) { //if request was successfully served, we get back the data

        //for each of todos we get back from server, we create a list element with corresponding text
        result.data.forEach(function (todo) {
            addElement(todo);
        });

        //After this, we can work with our list, thus we add event listeners to our controls.
        setupListeners();

    })
    .catch(function (reason) {
        //Any errors we just log
        console.error(reason);
    });

//This function creates new list element from the value of the input field
function createTodo() {
    var inputValue = todoInput.value; //retrieve input

    //Validate input - we don't need empty text
    if (inputValue === '') {
        alert("You must write something!");
        return;
    }

    var newTodo = {
        text: inputValue,
        done: false //initial value is false
    };
    //In POST request - send new object to the server
    axios.post('todo', newTodo)
        .then(function (result) {
            //server endpoint responds with [id] from the database
            newTodo.id = result.data.id;
            addElement(newTodo);
            todoInput.value = '';
        })
        .catch(function (reason) {
            console.error(reason);
        });
}

function deleteDone() {

    //Get todos with [done] == true
    var done = todos.filter(function (todo) {
        return todo.done;
    });

    //We use PUT request to "delete" todos, since DELETE method doesn't specify request body in HTTP standard
    //What is actually done is we set done to true in the database
    //so it doesn't return us done items when we reload page
    axios.put('todo', done)
        .then(function () {
            //We retrieve all <li> which have class 'checked' and construct an array from the NodeList we get.
            var checkedLIs = Array.from(todosHtml.getElementsByClassName('checked'));
            checkedLIs.forEach(function (li) {
                //We remove all the items we no longer need from the DOM
                li.remove();
            });
            //We reassign todos array to its subset of items which are not yet done
            todos = todos.filter(function (todo) {
                return !todo.done;
            });
        })
        .catch(function (reason) {
            console.error(reason)
        })
}

//This function adds elements to todosHtml list and updates todos array
function addElement(todo) {
    //create new list item
    var li = document.createElement('li');

    //create text node and add it to li
    li.appendChild(document.createTextNode(todo.text));

    //Append li to ul
    todosHtml.appendChild(li);

    //Add event listener that toggles css class 'checked' on li when we click on it
    li.addEventListener('click', function () {
        li.classList.toggle('checked');
        todo.done = !todo.done;
    });

    //Add data to the array
    todos.push(todo);
}