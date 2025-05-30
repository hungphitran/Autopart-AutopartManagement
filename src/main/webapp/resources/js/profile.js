document.addEventListener('DOMContentLoaded', function() {
    // Alert handling
    const alerts = document.querySelectorAll('[show-alert]');
    console.log(alerts);
    alerts.forEach(alert => {
        const time = parseInt(alert.getAttribute('data-time'));
        const closeBtn = alert.querySelector('[close-alert]');
        
        // Auto-hide after specified time
        setTimeout(() => {
            alert.classList.add('alert-hidden');
            // Remove from DOM after animation completes
            setTimeout(() => {
                if (alert.parentNode) {
                    alert.parentNode.removeChild(alert);
                }
            }, 300); // Match this with the animation duration
        }, time);
        
        // Manual close button
        if (closeBtn) {
            closeBtn.addEventListener('click', () => {
                alert.classList.add('alert-hidden');
                // Remove from DOM after animation completes
                setTimeout(() => {
                    if (alert.parentNode) {
                        alert.parentNode.removeChild(alert);
                    }
                }, 300);
            });
        }
    });

    // Edit button functionality
    const editBtn = document.querySelector('#edit-btn');
    const editForm = document.querySelector('#editForm');
    let isEditing = false;

    if (editBtn && editForm) {
        editBtn.addEventListener('click', function(event) {
            event.preventDefault(); // Prevent form submission initially

            if (!isEditing) {
                // Enable editing
                const inputs = editForm.querySelectorAll('input:not([type="email"]):not([type="hidden"])');
                inputs.forEach(input => input.disabled = false);
                editBtn.textContent = 'Lưu';
                editBtn.classList.add('save-btn'); // Optional: Add class for styling
                isEditing = true;
            } else {
                // Submit the form
                editForm.submit();
            }
        });
    }

    // Tab switching between orders and change password
    let orderLst = document.querySelector(".orders");
    let changePassForm = document.querySelector(".change-password");
    let changePassBtn = document.querySelector('.menu .setting');
    let showOrderBtn = document.querySelector('.menu .order');
  
    // Only setup tab switching if elements exist
    if (changePassBtn && showOrderBtn) {
        changePassBtn.onclick = function() {
            orderLst.classList.add('hide');
            changePassForm.classList.remove('hide');
            changePassBtn.classList.add('choosed');
            showOrderBtn.classList.remove('choosed');
    
            // Reset all inputs in the change password form
            let inputs = changePassForm.querySelectorAll("input");
            inputs.forEach(input => input.value = "");
        };
    
        showOrderBtn.onclick = function() {
            orderLst.classList.remove('hide');
            changePassForm.classList.add('hide');
            showOrderBtn.classList.add('choosed');
            changePassBtn.classList.remove('choosed');
        };
    }
  
    // Show/hide password functionality
    const togglePasswordVisibility = (input, eyeIcon, eyeSlashIcon) => {
        if (input.type === "password") {
            input.type = "text";
            eyeIcon.style.display = "inline";
            eyeSlashIcon.style.display = "none";
        } else {
            input.type = "password";
            eyeIcon.style.display = "none";
            eyeSlashIcon.style.display = "inline";
        }
    };
  
    // Set up password visibility toggles
    const passwordInputs = document.querySelectorAll(".change-password .form-item input[type='password']");
    passwordInputs.forEach((input, index) => {
        const eyeIcons = document.querySelectorAll(".change-password .form-item .fa-eye");
        const eyeSlashIcons = document.querySelectorAll(".change-password .form-item .fa-eye-slash");
        
        // Only proceed if we have both icons available
        if (index < eyeIcons.length && index < eyeSlashIcons.length) {
            const eyeIcon = eyeIcons[index];
            const eyeSlashIcon = eyeSlashIcons[index];
            
            // Initial state: show slash-eye (password hidden)
            eyeIcon.style.display = "none";
            eyeSlashIcon.style.display = "inline";
            
            eyeIcon.onclick = () => togglePasswordVisibility(input, eyeIcon, eyeSlashIcon);
            eyeSlashIcon.onclick = () => togglePasswordVisibility(input, eyeIcon, eyeSlashIcon);
        }
    });
});