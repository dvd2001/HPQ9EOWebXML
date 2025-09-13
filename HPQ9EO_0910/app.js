function loadNavigationBar() {
    fetch('nav.html')
        .then(response => response.text())
        .then(navigationHtml => {
            const bodyElement = document.querySelector('body');
            bodyElement.insertAdjacentHTML('afterbegin', navigationHtml);
        }).catch(error => console.error('Error loading navigation bar:', error));
}

function loadFooter() {
    fetch('foot.html')
        .then(response => response.text())
        .then(footerHtml => {
            const htmlElement = document.querySelector('html');
            htmlElement.insertAdjacentHTML('beforeend', footerHtml);
        }).catch(error => console.error('Error loading footer:', error));
}

loadNavigationBar();
loadFooter();