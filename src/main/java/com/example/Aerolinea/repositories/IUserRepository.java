package com.example.Aerolinea.repositories;

import com.example.Aerolinea.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long>{
}