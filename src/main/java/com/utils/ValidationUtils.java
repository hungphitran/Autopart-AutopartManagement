package com.utils;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

public class ValidationUtils {
    
    // Validation patterns
	// Kiểm tra định dạng email hợp lệ (chứa @, tên miền, ký tự hợp lệ).
	private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

	// Kiểm tra số điện thoại Việt Nam gồm 10 chữ số và bắt đầu bằng số 0.
	private static final String PHONE_PATTERN = "^0\\d{9}$";

	// Cho phép tên có chữ cái Unicode, dấu cách, dấu chấm, gạch nối, và dấu nháy đơn.
	private static final String NAME_PATTERN = "^[\\p{L} .'-]+$";

	// Kiểm tra chuỗi chỉ chứa chữ số.
	private static final String DIGITS_PATTERN = "^[0-9]+$";
    
    // Minimum length requirements
    private static final int MIN_PASSWORD_LENGTH = 4;
    private static final int MIN_EMAIL_LENGTH = 10;
    private static final int MAX_NAME_LENGTH = 100;
    
    /**
     * Validates an email address
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty() || email.length() < MIN_EMAIL_LENGTH) {
            return false;
        }
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    /**
     * Validates a Vietnamese phone number
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    
    /**
     * Validates a name
     */
    public static boolean isValidName(String name) {
        if (name == null || name.isEmpty() || name.length() > MAX_NAME_LENGTH) {
            return false;
        }
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
    
    /**
     * Validates a password
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= MIN_PASSWORD_LENGTH;
    }
    
    public static boolean isValidAddress(String address) {
    	// Cho phép chữ Unicode, số, khoảng trắng, dấu phẩy, chấm, gạch nối; ít nhất 3 ký tự.
        String pattern = "^[\\p{L}0-9\\s,\\.\\-\\/]{3,}$";

        // Use the regular expression to match the input address
        return address.matches(pattern);
    }

	public static boolean isValidDate(Date birthDate) {
		// TODO Auto-generated method stub
		if (birthDate == null) {
			return false;
		}
		Date today = new Date(System.currentTimeMillis());
		if (birthDate.after(today)) {
			return false; // Birth date cannot be in the future
		}
		return true;
	}
	
	
	public static boolean isValidDigits(String input) {
	    if (input == null || input.isEmpty()) {
	        return false;
	    }
	    Pattern pattern = Pattern.compile(DIGITS_PATTERN);
	    Matcher matcher = pattern.matcher(input);
	    return matcher.matches();
	}
	
	
	/**
     * Validates an image file (checks for non-empty and valid format)
     */
    public static boolean isValidImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        String fileName = file.getOriginalFilename().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png");
    }
}
