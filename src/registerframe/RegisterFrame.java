package registerframe;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.*;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Properties;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFormattedTextField.AbstractFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.mindrot.jbcrypt.BCrypt;

public class RegisterFrame extends JFrame {

    JLabel message;
    JLabel nameLabel, dobLabel, genderLabel, dobFormat;
    JTextField nameField;
    JRadioButton genderMale, genderFemale;
    ButtonGroup genderGroup;
    JLabel mailIdLabel, mobileNoLabel;
    JTextField mailIdField, mobileNoField;
    JLabel passwordLabel, rePasswordLabel;
    JPasswordField passwordField, rePasswordField;
    JLabel programLabel;
    JComboBox<String> programList;
    JLabel branchLabel, semesterLabel;
    JComboBox<String> branchList;
    JComboBox<Integer> semesterList;
    JButton registerButton;
    Container container;

    JDatePanelImpl datePanel;
    JDatePickerImpl datePicker;

    public RegisterFrame() {
        message = new JLabel("Register a new student");
        message.setFont(new Font("Courier", Font.BOLD, 20));

        nameLabel = new JLabel("Name");
        nameField = new JTextField();

        dobLabel = new JLabel("DOB");

        UtilDateModel model = new UtilDateModel();
        model.setDate(1999, 01, 02);
        model.setSelected(true);
        Properties p = new Properties();

        p.put("text.today", "today");
        p.put("text.month", "month");
        p.put("text.year", "year");
        datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DataLabelFormatter());
        dobFormat = new JLabel("yyyy-mm-dd");

        genderLabel = new JLabel("Gender");
        genderMale = new JRadioButton("Male", true);
        genderFemale = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(genderMale);
        genderGroup.add(genderFemale);

        mailIdLabel = new JLabel("Mail Id");
        mailIdField = new JTextField();

        mobileNoLabel = new JLabel("Mobile Number");
        mobileNoField = new JTextField();

        passwordLabel = new JLabel("Password");
        passwordField = new JPasswordField();

        rePasswordLabel = new JLabel("Reset Password");
        rePasswordField = new JPasswordField();

        programLabel = new JLabel("Courses");
        programList = new JComboBox<String>();
        programList.addItem("ME/M Tect");
        programList.addItem("BE/B Tect");
        programList.addItem("Diploma");

        branchLabel = new JLabel("Branch");
        branchList = new JComboBox<String>();
        branchList.addItem("Computer Science and Engineering");
        branchList.addItem("Electronics and Telecommunications");
        branchList.addItem("Information Technology");
        branchList.addItem("Electrical Engineering");
        branchList.addItem("Electrical and Electronics Engineering");
        branchList.addItem("Civil Engineering");

        semesterLabel = new JLabel("Semester");
        semesterList = new JComboBox<>();
        semesterList.addItem(1);
        semesterList.addItem(2);
        semesterList.addItem(3);
        semesterList.addItem(4);
        semesterList.addItem(5);
        semesterList.addItem(6);
        semesterList.addItem(7);
        semesterList.addItem(8);

        registerButton = new JButton("Register");
        container = getContentPane();
        container.setLayout(null);

        setBounds();
        addComponents();

        registerButton.addActionListener(new addActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message.setText("Congratulation!");

                System.out.println("Register Buttton Clicked");
                String gender = null;
                if (genderFemale.isSelected()) {
                    gender = "Female";
                }
                if (genderMale.isSelected()) {
                    gender = "Male";
                }
                String branchName = branchList.getSelectedItem().toString();
                String programName = programList.getSelectedItem().toString();
                int semesterNo = Integer.parseInt(semesterList.getSelectedItem().toString());

                String dobString = datePicker.getJFormattedTextField().getText();
                if (dobString.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Date of Birth is Emty");
                    return;
                }
                Date dob = null;
                try {
                    dob = Date.valueOf((dobString));
                } catch (IllegalArgumentException ex) {
                    System.out.println("Exception" + ex);
                    JOptionPane.showMessageDialog(null, "Date of Birth is in correct");
                    return;
                }
                System.out.println("name" + nameField.getText() + "dob" + dobString
                        + "gender" + gender + "mailid" + mailIdField.getText()
                        + "mobileNo" + mobileNoField.getText() + "password" + passwordField.getText()
                        + "rePassword" + rePasswordField.getText() + "program" + programName + "branch" + branchName
                        + "semester" + semesterNo);

                Student student = new Student(nameField.getText(), dob, gender, mailIdField.getText(),
                        mobileNoField.getText(), passwordField.getText(),
                        rePasswordField.getText(), programName, branchName, semesterNo);

                student.setEncPassword(BCrypt.hashpw(student.getPassword(), BCrypt.gensalt()));
                
                Validation v = new Validation();
                java.util.List<String> errors = v.validateRegistration(student);
                
                if(errors.size() > 0){
                    JOptionPane.showMessageDialog(null, errors.toArray());
                    return;
                }
                else{
                    RegisterDAO dao = new RegisterDAO();
                    int st = dao.registerStudent(student);
                    System.out.println(st);
                    if(st == 1){
                        JOptionPane.showMessageDialog(null, "Registered Successfully!");
                    }
                    if(st == -1){
                        JOptionPane.showMessageDialog(null,"Already Registered!");
                    }
                    if(st == -2){
                        JOptionPane.showMessageDialog(null,"OOps Unable to Register");
                    }
                }
            }
        });
    }

    //MAIN
    public static void main(String[] args) {
        //new RegisterFrame("Student Register Form").setVisible(true);
        System.out.println("ok");
        RegisterFrame frame = new RegisterFrame();
        frame.setTitle("Student Register Form");
        frame.setVisible(true);
        frame.setBounds(500, 100, 500, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        UtilDateModel model = new UtilDateModel();
        model.setDate(1990, 8, 24);
        model.setSelected(true);
        //JDatePanelIml datePanel = new JDatePaneIml(model);
        //JDatePickerIml datePicker = new JDatePickerIml(datePanel, new DateLabelFormatter());
        //frame.add(datePicker);

    }

    public void setBounds() {
        message.setBounds(50, 10, 600, 30);

        nameLabel.setBounds(50, 60, 100, 30);
        nameField.setBounds(150, 60, 200, 30);

        dobLabel.setBounds(50, 110, 100, 30);

        datePicker.setBounds(150, 110, 200, 30);
        dobFormat.setBounds(370, 110, 200, 30);

        genderLabel.setBounds(50, 160, 100, 30);
        genderMale.setBounds(150, 160, 100, 30);
        genderFemale.setBounds(260, 160, 100, 30);

        mailIdLabel.setBounds(50, 210, 100, 30);
        mailIdField.setBounds(150, 210, 200, 30);

        mobileNoLabel.setBounds(50, 260, 100, 30);
        mobileNoField.setBounds(150, 260, 200, 30);

        passwordLabel.setBounds(50, 310, 100, 30);
        passwordField.setBounds(150, 310, 200, 30);

        rePasswordLabel.setBounds(50, 360, 100, 30);
        rePasswordField.setBounds(150, 360, 200, 30);

        programLabel.setBounds(50, 410, 100, 30);
        programList.setBounds(150, 410, 200, 30);

        branchLabel.setBounds(50, 460, 100, 30);
        branchList.setBounds(150, 460, 200, 30);

        semesterLabel.setBounds(50, 510, 100, 30);
        semesterList.setBounds(150, 510, 200, 30);

        registerButton.setBounds(150, 550, 200, 30);
    }

    public void addComponents() {
        container.add(message);

        container.add(nameLabel);
        container.add(nameField);

        container.add(dobLabel);

        container.add(datePicker);
        container.add(dobFormat);

        container.add(genderLabel);
        container.add(genderMale);
        container.add(genderFemale);

        container.add(mailIdLabel);
        container.add(mailIdField);

        container.add(mobileNoLabel);
        container.add(mobileNoField);

        container.add(passwordLabel);
        container.add(passwordField);

        container.add(rePasswordLabel);
        container.add(rePasswordField);

        container.add(rePasswordLabel);
        container.add(rePasswordField);

        container.add(programLabel);
        container.add(programList);

        container.add(branchLabel);
        container.add(branchList);

        container.add(semesterLabel);
        container.add(semesterList);

        container.add(registerButton);
    }

    public class DataLabelFormatter extends AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }

    private static class addActionListener implements ActionListener {

        public addActionListener() {
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }

    
//    private void Onclick(java.awt.event.MouseEvent evt) {
//        System.out.println("Register Button Clicked");
//        String gender = null;
//        if (genderFemale.isSelected()) {
//            gender = "Female";
//        }
//        if (genderFemale.isSelected()) {
//            gender = "Male";
//        }
//        String programName = programList.getSelectedItem().toString();
//        String branchName = branchList.getSelectedItem().toString();
//        int semesterNo = Integer.parseInt(semesterList.getSelectedItem().toString());
//        String dobString = datePicker.getJFormattedTextField().getText();
//        if (dobString.isEmpty()) {
//            JOptionPane.showMessageDialog(null, "Date of Birth is Empty");
//            return;
//        }
//        Date dob = null;
//        try {
//            dob = Date.valueOf(dobString);
//        } catch (IllegalArgumentException ex) {
//            System.out.println("Exception" + ex);
//            JOptionPane.showMessageDialog(null, "Date of Birth is correct");
//            return;
//        }
//        System.out.println("name" + nameField.getText() + "dob" + dobString
//                + "gender" + gender + "mailid" + mailIdField.getText()
//                + "mobileNo" + mobileNoField.getText() + "password" + passwordField.getText()
//                + "rePassword" + rePasswordField.getText() + "program" + programName + "branch" + branchName
//                + "semester" + semesterNo);
//        Student student = new Student(nameField.getText(), dob, gender, mailIdField.getText(),
//                mobileNoField.getText(), passwordField.getText(),
//                rePasswordField.getText(), programName, branchName, semesterNo);
//    }

    
    public class DateLabelFormatter extends AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }

    }
}
