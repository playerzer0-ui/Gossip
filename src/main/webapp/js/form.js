//warning meassages
var warning = document.querySelectorAll(".warning");
document.getElementById("theButton").disabled = true;
var warn1 = warning[0];
var warn2 = warning[1];

//password input
var input_list = document.querySelectorAll("input");


//check password if match
function checkPassword(){
    var initial = input_list[2].value;
    var now = input_list[3].value;
    console.log(initial);
    console.log(now);
    if(initial === now && initial !== "" && now !== ""){
        warn1.style.color = "#75ffb3";
        warn1.innerHTML = "passwords MATCH";


    }
    else{
        warn1.style.color = "red";
        warn1.innerHTML = "passwords do not match";
        document.getElementById("theButton").disabled = true;
    }

}

//check password strength
function check_strength(){
    var initial = input_list[2].value;
    var length = false;
    var symbol = false;
    var num = false;
    var capital = false;
    var lower = false;

    if(initial.length >= 6){
        length = true;
    }
    else{
        warn2.style.color = "red";
        document.getElementById("theButton").disabled = true;
        warn2.innerHTML = "password should be at least 8 characters long, at least one number and symbol ";
    }

    if(/[A-Z]/.test(initial) === true){
        capital = true;
    }
    if(/[a-z]/.test(initial) === true){
        lower = true;
    }
    if(/[|\\/~^:,;?!&%$@*+]/.test(initial) === true){
        symbol = true;
    }
    if(/[0-9]/.test(initial) === true){
        num = true;
    }

    if(length === true && capital === true && lower === true && symbol === true && num === true){
        warn2.style.color = "#75ffb3";
        warn2.innerHTML = "password is secure";
        document.getElementById("theButton").disabled = false;
    }
    else{
        warn2.style.color = "red";
        document.getElementById("theButton").disabled = true;
        warn2.innerHTML = "password should be at least 8 characters long, at least one number and symbol";
    }
}