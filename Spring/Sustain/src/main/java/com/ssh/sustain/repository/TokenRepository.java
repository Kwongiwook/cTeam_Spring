package com.ssh.sustain.repository;

import com.ssh.sustain.model.user.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, String> {
}
