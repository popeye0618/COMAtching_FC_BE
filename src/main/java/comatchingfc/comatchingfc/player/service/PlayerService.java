package comatchingfc.comatchingfc.player.service;

import comatchingfc.comatchingfc.player.dto.PlayerInfoReq;
import comatchingfc.comatchingfc.player.dto.PlayerRes;
import comatchingfc.comatchingfc.player.entity.Player;
import comatchingfc.comatchingfc.player.repository.PlayerRepository;
import comatchingfc.comatchingfc.user.enums.CheerPropensityEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public List<PlayerRes> getSamePropensityPlayers(CheerPropensityEnum cheerPropensityEnum) {
        return playerRepository.findPlayersByCheerPropensityEnum(cheerPropensityEnum).stream()
                .map(player -> PlayerRes.builder()
                        .playerName(player.getName())
                        .backNumber(player.getBackNumber())
                        .position(player.getPosition())
                        .build()).collect(Collectors.toList());
    }

    @Transactional
    public void addPlayer(PlayerInfoReq playerInfoReq) {
        Player player = Player.builder()
                .name(playerInfoReq.getName())
                .backNumber(playerInfoReq.getBackNumber())
                .position(playerInfoReq.getPosition())
                .cheerPropensityEnum(CheerPropensityEnum.from(playerInfoReq.getCheerPropensity()))
                .build();

        playerRepository.save(player);
    }
}
