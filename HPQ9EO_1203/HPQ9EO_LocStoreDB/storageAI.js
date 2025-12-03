function saveStudent(student) {
    let students = getStudents();
    students.push(student);
    localStorage.setItem('students', JSON.stringify(students));
}

function getStudents() {
    return JSON.parse(localStorage.getItem('students')) || [];
}

function updateStudent(studentId, updatedStudent) {
    let students = getStudents();
    const index = students.findIndex(student => student.id === studentId);
    if (index !== -1) {
        students[index] = updatedStudent;
        localStorage.setItem('students', JSON.stringify(students));
    }
}

function deleteStudent(studentId) {
    let students = getStudents();
    students = students.filter(student => student.id !== studentId);
    localStorage.setItem('students', JSON.stringify(students));
}

function clearStudents() {
    localStorage.removeItem('students');
}

function sortStudentsBy(field) {
    let students = getStudents();
    return students.sort((a, b) => a[field].localeCompare(b[field]));
}

export { saveStudent, getStudents, updateStudent, deleteStudent, clearStudents, sortStudentsBy };