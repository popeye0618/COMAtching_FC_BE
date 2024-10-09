package comatchingfc.comatchingfc.auth.jwt.dto.admin;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomAdmin implements UserDetails {

    private final AdminDto adminDto;

    public CustomAdmin(AdminDto adminDto) {
        this.adminDto = adminDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(adminDto.getRole()));
    }

    @Override
    public String getPassword() {
        return adminDto.getPassword();
    }

    @Override
    public String getUsername() {
        return adminDto.getAccountId();
    }

    public String getUuid() {
        return adminDto.getUuid();
    }

    public String getRole() {
        return adminDto.getRole();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
