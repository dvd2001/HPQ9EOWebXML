document.addEventListener('DOMContentLoaded', () => {
    // app.js

    document.addEventListener('DOMContentLoaded', () => {
        const form = document.getElementById('hallgato-form');
        const searchInput = document.getElementById('kereses-input');

        // Load existing students from local storage
        loadStudents();

        // Event listener for form submission
        form.addEventListener('submit', (e) => {
            e.preventDefault();
            const studentData = getFormData();
            if (studentData) {
                saveStudent(studentData);
                form.reset();
                loadStudents();
            }
        });

        // Event listener for search functionality
        searchInput.addEventListener('input', () => {
            const searchTerm = searchInput.value.toLowerCase();
            filterStudents(searchTerm);
        });
    });

    function getFormData() {
        const name = document.getElementById('nev').value;
        const year = document.getElementById('evfolyam').value;
        const major = document.getElementById('szak').value;
        const email = document.getElementById('email').value;
        const studentId = document.getElementById('neptun').value;

        if (name && year && major && email && studentId) {
            return { name, year, major, email, studentId };
        }
        return null;
    }

    function loadStudents() {
        const students = JSON.parse(localStorage.getItem('students')) || [];
        renderStudentList(students);
    }

    function saveStudent(studentData) {
        const students = JSON.parse(localStorage.getItem('students')) || [];
        students.push(studentData);
        localStorage.setItem('students', JSON.stringify(students));
    }

    function filterStudents(searchTerm) {
        const students = JSON.parse(localStorage.getItem('students')) || [];
        const filteredStudents = students.filter(student =>
            student.name.toLowerCase().includes(searchTerm) ||
            student.major.toLowerCase().includes(searchTerm)
        );
        renderStudentList(filteredStudents);
    }

    function renderStudentList(students) {
        const studentTable = document.getElementById('hallgato-tabla-body');
        studentTable.innerHTML = '';
        students.forEach(student => {
            const row = document.createElement('tr');
            row.innerHTML = `
            <td>${student.name}</td>
            <td>${student.year}</td>
            <td>${student.major}</td>
            <td>${student.email}</td>
            <td>${student.studentId}</td>
            <td><button class="modosit">Módosítás</button></td>
            <td><button class="torol">Törlés</button></td>
        `;
            studentTable.appendChild(row);
        });
    }
})