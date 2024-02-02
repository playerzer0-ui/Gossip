
document.addEventListener('keydown', function(event) {

    if (document.activeElement.tagName === 'INPUT') {
        let audio = null;

        switch (event.key) {
            case "1":
                audio = new Audio('sounds/do.wav');
                audio.play();
                break;
            case "2":
                audio = new Audio('sounds/re.wav');
                audio.play();
                break;
            case "3":
                audio = new Audio('sounds/mi.wav');
                audio.play();
                break;
            case "4":
                audio = new Audio('sounds/fa.wav');
                audio.play();
                break;
            case "5":
                audio = new Audio('sounds/sol.wav');
                audio.play();
                break;
            case "6":
                audio = new Audio('sounds/la.wav');
                audio.play();
                break;
            case "7":
                audio = new Audio('sounds/si.wav');
                audio.play();
                break;
            case "8":
                audio = new Audio('sounds/do1.wav');
                audio.play();
                break;
            case "9":
                audio = new Audio('sounds/re1.wav');
                audio.play();
                break;
            case "0":
                audio = new Audio('sounds/mi1.wav');
                audio.play();
                break;
          }
    }
});