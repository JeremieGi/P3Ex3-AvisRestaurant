package com.openclassrooms.tajmahal.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

/** @noinspection SameReturnValue, SameReturnValue */
@Singleton
public class UserRepository {

    // TODO : J'ai fait un autre repository, plus évolutif
    // Ca aurait mérité une classe User mais il faudrait refactoriser l'existant (classe Review)

    @Inject
    public UserRepository() {

    }

    /**
     * this code will be implemented by Priyanka
     * @return : Username
     */
    public String getUserName(){
        return "Manon Garcia";
    }

    /**
     * this code will be implemented by Priyanka
     * @return : user avatar
     */
    public String getUserPicture(){
        return "https://xsgames.co/randomusers/assets/avatars/female/2.jpg";
    }
}
