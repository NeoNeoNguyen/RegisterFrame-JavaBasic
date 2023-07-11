package registerframe;

import java.util.ArrayList;
import java.util.List;

public class Validation {

    public List<String> validateRegistration(Student student) {
        ArrayList<String> err = new ArrayList<String>();
        
        //raiserror name
        if(student.getName().isEmpty()) {
            err.add("Name can not be empty!");
        }
        else if(student.getName().length()<4){
            err.add("Name is too short!");
        }
        else if(student.getName().length()>50){
            err.add("Name is too long!");
        }
        else if(!isString(student.getName())){
            err.add("Only characters allowed in name.");
        }
        
        //raiserror email
        if(student.getMailId().isEmpty()){
            err.add("MailId can not be empty!");
        }
        else if(!isValidEmailAddress(student.getMailId())){
            err.add("Mail is not valid!");
        }
        
        //raiserror phone number
        if(student.getMobileNo().isEmpty()){
            err.add("Phone number can not be empty!");
        }
        else if(student.getMobileNo().length() != 10){
            err.add("Phone number be 10 dibit long.");
        }
        else if(!isDigit(student.getMobileNo())){
            err.add("Phone number must have only digits.");
        }
        
        //raiserror password
        if(student.getPassword().isEmpty()){
            err.add("Password not be empty!");
        }
        else if(student.getPassword().length() < 8){
            err.add("Password is too short!");
        }
        else if(student.getPassword().length() > 20){
            err.add("Password is too long!");
        }
        else if(!isStrongPassword(student.getPassword())){
            err.add("Enter Strong Password.");
        }
        
        //raiserror repassword
        if(student.getRePassword().isEmpty()){
            err.add("Repassword can not be empty!");
        }
        else if(student.getRePassword().length() < 8){
            err.add("Repassword is too short!");
        }
        else if(student.getRePassword().length() > 20){
            err.add("Repassword is too long!");
        }
        
        //raiserror if pass like repass
        if(!student.getPassword().equals(student.getRePassword())){
            err.add("Both password are not matching");
        }
        return err;
    }

    private boolean isString(String name) {
        String ePattenrn = "^[\\p{L} '-]+$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattenrn);
        java.util.regex.Matcher m = p.matcher(name);
        return m.matches();
    }

    private boolean isValidEmailAddress(String mailId) {
        String ePattenrn = "^[a-zA-Z0-9.!#$%'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\."
                + "[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)"
                + "+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattenrn);
        java.util.regex.Matcher m = p.matcher(mailId);
        return m.matches();
    }

    private boolean isDigit(String mobileNo) {
        boolean digits = mobileNo.chars().allMatch(Character::isDigit);
        return digits;
    }

    private boolean isStrongPassword(String password) {
        String ePattenrn = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattenrn);
        java.util.regex.Matcher m = p.matcher(password);
        return m.matches();
    }
}
