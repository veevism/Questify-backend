package com.backend.questify.Repository;

import com.backend.questify.Entity.Laboratory;
import com.backend.questify.Entity.Logging;
import com.backend.questify.Entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoggingRepository extends JpaRepository<Logging, UUID> {

}
