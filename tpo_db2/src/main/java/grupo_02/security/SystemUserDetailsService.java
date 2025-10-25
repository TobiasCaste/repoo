package grupo_02.security;

import grupo_02.entities.User;
import grupo_02.mapper.UserMapper;
import grupo_02.mongoQuerys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class SystemUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public SystemUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =  userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with email: "+ email));
        return user;
    }
}
