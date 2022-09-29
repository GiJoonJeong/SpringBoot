package com.jo.five.security.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jo.five.security.model.User;

import lombok.Data;

// 시큐리티가 /login 주소 요총이 오면 낚아채셔 로그인 진행
// 로그인을 진행이 완료가 되면 시큐리티 session(공간은 똑같은데 시큘리티 공간에)
// 만들어준다. (Security ContextHolder)
// 위에 들어갈수 있는 오브젝트가 정해져 있는데 Authentication 타입 객체가 들어간다.
// Authentication 안에 User정보가 들어가야함.
// User오브젝트타입 => UserDetails 타입 객체

// Security Session => Authentication => UserDetails(PrincipalDetails)

@Data
public class PrincipalDetails implements UserDetails {

    private User user; // 콤포지션 //다른객체의 인스턴스를 자신의 인스턴스 변수로 포함해서 메서드를 호출하는 기법

    public PrincipalDetails(User user) {
        this.user = user;

    }

    // 해당 User의 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {

        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정만료
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정잠금
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 비밀번호 경과
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 계정 활성화
        // 사이트에서 1년동안 회원이 로그인을 안하면 휴먼 계정으로 하기로 한다면

        return true;
    }
}
