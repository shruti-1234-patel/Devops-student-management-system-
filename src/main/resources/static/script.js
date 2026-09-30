const API_URL = "/api/students";


// Load students when page opens

window.onload = function () {
    loadStudents();
};


// GET ALL STUDENTS

function loadStudents() {

    fetch(API_URL)
        .then(response => response.json())
        .then(students => {

            const tableBody =
                document.getElementById("studentTableBody");

            tableBody.innerHTML = "";

            students.forEach(student => {

                const row = `
                    <tr>

                        <td>${student.id}</td>

                        <td>${student.name}</td>

                        <td>${student.email}</td>

                        <td>${student.course}</td>

                        <td>${student.age}</td>

                        <td>

                            <button
                                class="edit-button"
                                onclick="editStudent(${student.id})">
                                Edit
                            </button>

                            <button
                                class="delete-button"
                                onclick="deleteStudent(${student.id})">
                                Delete
                            </button>

                        </td>

                    </tr>
                `;

                tableBody.innerHTML += row;
            });
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Unable to load students");
        });
}


// CREATE / UPDATE STUDENT

function saveStudent() {

    const id =
        document.getElementById("studentId").value;

    const student = {

        name:
            document.getElementById("name").value,

        email:
            document.getElementById("email").value,

        course:
            document.getElementById("course").value,

        age:
            parseInt(document.getElementById("age").value)

    };


    let url = API_URL;
    let method = "POST";


    // UPDATE

    if (id) {

        url = `${API_URL}/${id}`;
        method = "PUT";
    }


    fetch(url, {

        method: method,

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(student)

    })

    .then(response => response.json())

    .then(data => {

        alert(
            id
                ? "Student updated successfully!"
                : "Student added successfully!"
        );

        clearForm();

        loadStudents();

    })

    .catch(error => {

        console.error("Error:", error);

        alert("Operation failed");

    });
}


// EDIT STUDENT

function editStudent(id) {

    fetch(`${API_URL}/${id}`)

        .then(response => response.json())

        .then(student => {

            document.getElementById("studentId").value =
                student.id;

            document.getElementById("name").value =
                student.name;

            document.getElementById("email").value =
                student.email;

            document.getElementById("course").value =
                student.course;

            document.getElementById("age").value =
                student.age;


            document.getElementById("formTitle").innerText =
                "Update Student";

            document.getElementById("saveButton").innerText =
                "Update Student";

        })

        .catch(error => {

            console.error("Error:", error);

        });
}


// DELETE STUDENT

function deleteStudent(id) {

    if (!confirm("Are you sure you want to delete this student?")) {
        return;
    }


    fetch(`${API_URL}/${id}`, {

        method: "DELETE"

    })

    .then(response => response.text())

    .then(message => {

        alert(message);

        loadStudents();

    })

    .catch(error => {

        console.error("Error:", error);

        alert("Delete failed");

    });
}


// CLEAR FORM

function clearForm() {

    document.getElementById("studentId").value = "";

    document.getElementById("name").value = "";

    document.getElementById("email").value = "";

    document.getElementById("course").value = "";

    document.getElementById("age").value = "";


    document.getElementById("formTitle").innerText =
        "Add Student";

    document.getElementById("saveButton").innerText =
        "Add Student";
}