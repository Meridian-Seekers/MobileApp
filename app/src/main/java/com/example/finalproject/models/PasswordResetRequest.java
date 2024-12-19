package com.example.finalproject.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PasswordResetRequest {



    /*public PasswordResetRequest(String email, String newPassword, String resetToken) {
        this.email = email;
        this.newPassword = newPassword;
        this.resetToken = resetToken;
    }*/

    public PasswordResetRequest(String email,String confirmpassword, String newPassword) {
        this.email = email;
        this.confirm_password = confirmpassword;
        this.new_password = newPassword;

    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public String getNewPassword() {
        return new_password;
    }

    /*public String getResetToken() {
        return resetToken;
    }*/

    @Expose
    @SerializedName("confirm_password")
    private String confirm_password;

    @Expose
    @SerializedName("new_password")
    private String new_password;

   @Expose
    @SerializedName("email")
    private String email;
}


/*package com.example.finalproject.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PasswordResetRequest {

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("new_password")
    private String newPassword;

    @Expose
    @SerializedName("reset_token")
    private String resetToken;

    public PasswordResetRequest(String email, String newPassword, String resetToken) {
        this.email = email;
        this.newPassword = newPassword;
        this.resetToken = resetToken;
    }

    public String getEmail() {
        return email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getResetToken() {
        return resetToken;
    }
}*/
