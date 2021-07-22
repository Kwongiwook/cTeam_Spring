package com.ssh.sustain.repository;

import com.ssh.sustain.model.token.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, String> {
}
