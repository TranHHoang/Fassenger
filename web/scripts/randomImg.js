$(document).ready(() => {
    $('body').css('background-image', 'url(./images/' + getRandomImage() +')');
});

function getRandomImage() {
    var images = [];

    for (let i = 1; i <= 17; i++) {
        images.push(i + ".jpg");
    }

    return images[Math.floor(Math.random() * images.length)];
}