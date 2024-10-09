package comatchingfc.comatchingfc.auth.jwt.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDto {
    private String accountId;
    private String password;
    private String role;
    private String uuid;
}
