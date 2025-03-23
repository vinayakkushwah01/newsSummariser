package com.newsSummeriser.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newsSummeriser.model.User;




@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    
    // public String getRoleByEmail(String email){
    //     User u1 = findByEmail(email);
    //     Role r1 = u1.getRole();
    //     return r1.getName();
    // }
}
