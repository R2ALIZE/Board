package boardProject.global.security.userDetail;

import boardProject.domain.member.entity.Member;
import boardProject.domain.member.repository.MemberRepository;
import boardProject.global.exception.BusinessLogicException;
import boardProject.global.exception.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Member existingMember = memberRepository.findByEmail(username)
                                                .orElseThrow(
                                                        () -> new BusinessLogicException(StatusCode.MEMBER_NOT_EXIST)
                                                );


        return new CustomUserDetails(existingMember);

    }

}
