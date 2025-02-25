let inputs = document.querySelectorAll(".input-field input[type='text']");
let editBtn = document.querySelector(".edit-btn");
let form = document.querySelector("form"); // Assuming your form element is present

console.log(inputs);

editBtn.onclick = function(event) {
    event.preventDefault();
    if (editBtn.innerHTML == "Lưu") {
        form.submit(); // Submit the form
    } else {
        for (let i = 0; i < inputs.length; i++) {
            console.log(inputs[i]);
            inputs[i].disabled = false;
        }
        editBtn.innerHTML = "Lưu";
    }
};

let orderLst = document.querySelector(".orders");
let changePassForm = document.querySelector(".change-password");
let changePassBtn = document.querySelector('.menu .setting');
let showOrderBtn = document.querySelector('.menu .order');

changePassBtn.onclick = function() {
    orderLst.classList.add('hide');
    changePassForm.classList.remove('hide');
    changePassBtn.classList.add('choosed');
    showOrderBtn.classList.remove('choosed');
};

showOrderBtn.onclick = function() {
    orderLst.classList.remove('hide');
    changePassForm.classList.add('hide');
    showOrderBtn.classList.add('choosed');
    changePassBtn.classList.remove('choosed');
};
