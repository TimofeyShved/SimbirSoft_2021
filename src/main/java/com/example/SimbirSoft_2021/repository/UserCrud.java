package com.example.SimbirSoft_2021.repository;

import com.example.SimbirSoft_2021.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//public interface UserCrud extends CrudRepository<UserEntity, Long> { // наследуемый интерфейс для изменения данных в бд

@Repository
public interface UserCrud extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserId(Long userId);
    UserEntity findByFirstNameAndLastName(String firstName, String lastName);
}
