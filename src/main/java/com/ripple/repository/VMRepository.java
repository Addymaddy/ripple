package com.ripple.repository;

import com.ripple.model.VM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VMRepository extends JpaRepository<VM, Long> {
    List<VM> findByUserId(String userId);
}
