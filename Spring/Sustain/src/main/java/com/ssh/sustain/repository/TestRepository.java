package com.ssh.sustain.repository;

import com.ssh.sustain.model.TestModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<TestModel, Long> {

    List<TestModel> findByNameContainsIgnoreCase(String name);

}
