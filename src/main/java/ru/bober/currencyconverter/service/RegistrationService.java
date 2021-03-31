package ru.bober.currencyconverter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bober.currencyconverter.entity.Users;
import ru.bober.currencyconverter.repository.RegistrationRepository;

@Service
public class RegistrationService {
    @Autowired
    RegistrationRepository registrationRepository;

    public void createUser(String password, String userName) {
        Users users = new Users();
        users.setPassword(password);
        users.setUsername(userName);
        users.setEnabled(true);
        users.setRole("ROLE_USER");
        registrationRepository.save(users);
    }
    public boolean findOneUserByLogin(String login){
        if(registrationRepository.findByUsername(login)!=null)
            return true;
        else return false;
    }
}
