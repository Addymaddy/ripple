package com.ripple.repository;

import com.ripple.model.ApplicationUser;
import com.ripple.model.VM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by intel on 5/10/2019.
 */
@Repository
public interface VMRepository extends JpaRepository<VM, Long> {
    List<VM> findByUserId(String userId);
}
