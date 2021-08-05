package com.ssh.sustain.dto.user;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VerifyDto {

    @NotNull
    private String email;

    @NotNull
    private String code;

}
