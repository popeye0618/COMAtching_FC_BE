package comatchingfc.comatchingfc.auth.service;

import comatchingfc.comatchingfc.admin.entity.Admin;
import comatchingfc.comatchingfc.admin.repository.AdminRepository;
import comatchingfc.comatchingfc.auth.jwt.dto.admin.AdminDto;
import comatchingfc.comatchingfc.auth.jwt.dto.admin.CustomAdmin;
import comatchingfc.comatchingfc.utils.uuid.UUIDUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByAccountId(accountId)
                .orElseThrow(() -> new UsernameNotFoundException("관리자를 찾을 수 없습니다."));

        AdminDto adminDto = AdminDto.builder()
                .accountId(admin.getAccountId())
                .password(admin.getPassword())
                .role("ROLE_ADMIN")
                .uuid(UUIDUtil.bytesToHex(admin.getUuid()))
                .build();

        return new CustomAdmin(adminDto);
    }
}
