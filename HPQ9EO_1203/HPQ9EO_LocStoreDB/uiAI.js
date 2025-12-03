const ui = (() => {
    const studentTableBody = document.getElementById('student-tabla-body');
    const messageContainer = document.getElementById('message-container');

    const renderStudentList = (students) => {
        studentTableBody.innerHTML = '';
        students.forEach(student => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${student.name}</td>
                <td>${student.year}</td>
                <td>${student.major}</td>
                <td>${student.email}</td>
                <td>${student.studentId}</td>
                <td>
                    <button class="modosit" data-id="${student.studentId}">Módosítás</button>
                    <button class="torol" data-id="${student.studentId}">Törlés</button>
                </td>
            `;
            studentTableBody.appendChild(row);
        });
    };

    const showMessage = (message) => {
        messageContainer.textContent = message;
        messageContainer.classList.add('show');
        setTimeout(() => {
            messageContainer.classList.remove('show');
        }, 3000);
    };

    const clearForm = () => {
        document.getElementById('hallgato-form').reset();
    };

    return {
        renderStudentList,
        showMessage,
        clearForm
    };
})();

export default ui;