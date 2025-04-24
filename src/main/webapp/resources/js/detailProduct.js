// Function to hide messages after a delay
document.addEventListener('DOMContentLoaded', function() {
    // Get success and error message elements
    const successMessage = document.querySelector('.alert-success');
    const errorMessage = document.querySelector('.alert-danger');
    
    // If success message exists, hide it after 3 seconds
    if (successMessage) {
        setTimeout(function() {
            successMessage.style.transition = 'opacity 0.5s';
            successMessage.style.opacity = '0';
            setTimeout(function() {
                successMessage.style.display = 'none';
            }, 500);
        }, 3000);
    }
    
    // If error message exists, hide it after 3 seconds
    if (errorMessage) {
        setTimeout(function() {
            errorMessage.style.transition = 'opacity 0.5s';
            errorMessage.style.opacity = '0';
            setTimeout(function() {
                errorMessage.style.display = 'none';
            }, 500);
        }, 3000);
    }
});



let minusBtn=document.getElementById('button-minus')

let plusBtn = document.getElementById('button-plus');

if(plusBtn!=null && minusBtn!=null ){
	document.getElementById('button-plus').addEventListener('click', function () {
	    var quantity = document.getElementById('quantity');
	    quantity.value = parseInt(quantity.value) + 1;
	});

	document.getElementById('button-minus').addEventListener('click', function () {
	    var quantity = document.getElementById('quantity');
	    if (quantity.value > 1) {
	        quantity.value = parseInt(quantity.value) - 1;
	    }
	});
}


let mainImg = document.getElementById('main-image')

function updateMainImg(element) {
	
	let current = document.querySelector('.image-item.active');
    if (current) {
        current.classList.remove('active');
        element.classList.add('active');
    }
	mainImg.src = element.src;
	console.log(mainImg.src)
}