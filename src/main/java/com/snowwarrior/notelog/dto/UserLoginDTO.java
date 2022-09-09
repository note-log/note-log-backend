package com.snowwarrior.notelog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author SnowWarrior
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginDTO {
    @NotBlank(message = "用户名不得为空")
    @Size(min = 6, max = 20)
    private String username;

    @NotBlank(message = "密码不得为空")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9~!@#$%^&*]{8,32}$", message = "必须包含大小写字母和数字的组合，可以使用特殊字符(~!@#$%^&*)，长度在8-32之间")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
