package boardProject.global.auth.config.userDetail;

import boardProject.domain.member.entity.Member;
import boardProject.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;

public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Member existingMember = memberRepository.findByEmail(username)
                                                .orElseThrow(
                                                        () -> new UsernameNotFoundException(username)
                                                );


        return new User(existingMember.getEmail(), existingMember.getPassword(), authorities());

    }

    private Collection<? extends GrantedAuthority> authorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
