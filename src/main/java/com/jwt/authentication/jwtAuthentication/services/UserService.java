package com.jwt.authentication.jwtAuthentication.services;

import com.jwt.authentication.jwtAuthentication.entities.User;
import com.jwt.authentication.jwtAuthentication.repositories.UserRepository;
import com.jwt.authentication.jwtAuthentication.util.GenericUtil;
import com.jwt.authentication.jwtAuthentication.util.MailSenderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private MailSenderUtil mailSenderUtil;


    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public ResponseEntity<String> createUser(User user){
        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateOfLogin = format.format(currentDate);
        user.setCreatedDate(dateOfLogin);

        // This block check the User email is already exists in the system or not. if yes, redirect in the login window
        // Else, register this as a new User
        Optional<User> findExistingUser = userRepository.findByEmail(user.getEmail());
        if(findExistingUser.isEmpty()){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User dbUser = userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("user "+dbUser.getFirstName()+" successfully register in the system");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The User is Already Present in the DB, named "+findExistingUser.get().getFirstName());
        }
    }

    public User findById(int theId) {
        Optional<User> result = userRepository.findById(String.valueOf(theId));
        User theUser = null;
        if(result.isPresent()){
            theUser = result.get();
        }else {
            throw new RuntimeException("Did not find the User id - "+theId);
        }
        return theUser;
    }

    public void deleteById(int theId) {
        userRepository.deleteById(String.valueOf(theId));
    }

    public ResponseEntity<String> forgotPassword(String emailId) {
        User existingDbUser = userRepository.findByEmail(emailId).get();
        if(existingDbUser == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("This Email is not registered in the system. Please Register First.");
        }else{
            String userUniqueToken = GenericUtil.generateResetToken();

            Date tokenCreationDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String toeknCreationTimeString = format.format(tokenCreationDate);

            Date tokenExpairyDate = new Date(tokenCreationDate.getTime() + (30 * 60 * 1000));
            String tokenExpairyTimeString = format.format(tokenExpairyDate);

            existingDbUser.setPassResetToekn(userUniqueToken);
            existingDbUser.setTokenCreationTime(toeknCreationTimeString);
            existingDbUser.setTokenExpairyTime(tokenExpairyTimeString);
            userRepository.save(existingDbUser);
            String msgBody = "Hi "+existingDbUser.getFirstName()+", Please Reset your Password using the Following Link.\nReset Password Link - http://localhost:3000/resetPassword?token="+userUniqueToken;
            MailSenderUtil.sendEmail(existingDbUser.getEmail(),"Reset Password",msgBody);
            return ResponseEntity.status(HttpStatus.OK).body("Email Send successfully");
        }
    }

    public ResponseEntity<String> resetPassword(User portalUser, String securityToken){
        User existingDbUser = userRepository.findByEmail(portalUser.getEmail()).get();
        if(existingDbUser == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This User Email does not exists in the System");
        }
        if(existingDbUser.getPassResetToekn() != null && existingDbUser.getPassResetToekn().equals(securityToken)){
            try {
                // Define the date format of the input string
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateOfLogin = dateFormat.parse(existingDbUser.getTokenExpairyTime());
                Date currentDate = new Date();
                int comparison = currentDate.compareTo(dateOfLogin);
                if (comparison > 0) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("This URL is expired. Please try again.");
                }else{
                    existingDbUser.setPassword(passwordEncoder.encode(portalUser.getPassword()));
                    userRepository.save(existingDbUser);
                    return ResponseEntity.status(HttpStatus.OK).body("Hi "+existingDbUser.getFirstName()+", password Updated.");
                }
            } catch (ParseException e) {
                System.out.println("Executing the catch Block");
                throw new RuntimeException(e);
            }
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not using a Valid URL. Try after sometime");
        }
    }
}
