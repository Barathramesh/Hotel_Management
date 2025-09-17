package com.project.Hotel.Management.Service;

import com.project.Hotel.Management.Util.InputUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class otpService {

    private final Map<String, String> otpStorage = new HashMap<>();

    @Autowired
    private EmailService emailService;

    public String generateOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000); // 6-digit OTP
        otpStorage.put(email, otp);

        emailService.sendEmail(email, "Your OTP Code", "Your OTP is: " + otp);
        return otp;
    }

    public boolean verifyOtp(String email, String enteredOtp) {
        String validOtp = otpStorage.get(email);
        return validOtp != null && validOtp.equals(enteredOtp);
    }


//    public void userLoginWithOtp() {
//        String email = InputUtil.getString("Enter your email:");
//        String otp = otpService.generateOtp(email);
//
//        String enteredOtp = InputUtil.getString("Enter OTP sent to your email: ");
//
//        if (otpService.verifyOtp(email, enteredOtp)) {
//            System.out.println("✅ OTP Verified! Login successful.");
//        } else {
//            System.out.println("❌ Invalid OTP.");
//        }
//    }
//
//    String sub = "Room booking";
//    String text = "Your room booked successfully and your booking id is: "+bookingnum;
//            emailService.sendEmail(email,sub,text);
//    userLoginWithOtp();

}


