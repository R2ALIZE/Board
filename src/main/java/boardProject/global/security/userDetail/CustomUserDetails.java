package boardProject.global.security.userDetail;

import boardProject.domain.member.entity.Member;
import boardProject.global.util.authority.CustomAuthorityUtil;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public final class CustomUserDetails extends Member implements UserDetails {

    private final String username;

    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;


    CustomUserDetails(Member member) {
        this.username = member.getEmail();
        this.password = member.getPassword();
        this.authorities = CustomAuthorityUtil.rolesToAuthorities(member);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
