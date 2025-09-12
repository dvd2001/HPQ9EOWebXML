function loadNavigationBar() {
    fetch('nav.html')
        .then(response => response.text())
        .then(navigationHtml => {
            const bodyElement = document.querySelector('body');
            bodyElement.insertAdjacentHTML('afterbegin', navigationHtml);
        }).catch(error => console.error('Error loading navigation bar:', error));
}

loadNavigationBar();