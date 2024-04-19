//menuToggle
var toggle = document.querySelector(".toggle");
var nav = document.querySelector("nav");
var main = document.querySelector("main");

toggle.onclick = function(){
    nav.classList.toggle('active');
    main.classList.toggle('active');
}

var list = document.querySelectorAll("nav li");
function activeLink(){
    list.forEach((item) => item.classList.remove('hovered'));
    this.classList.add('hovered');
}
list.forEach((item) => item.addEventListener('mouseover', activeLink));