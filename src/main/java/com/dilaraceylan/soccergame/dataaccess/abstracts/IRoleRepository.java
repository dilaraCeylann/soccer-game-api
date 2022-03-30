package com.dilaraceylan.soccergame.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dilaraceylan.soccergame.entities.concrete.Role;
/**
 * @author dilara.ceylan
 */
@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
  Role findByName(String name);
}
