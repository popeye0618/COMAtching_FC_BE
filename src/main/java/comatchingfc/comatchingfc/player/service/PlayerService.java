package comatchingfc.comatchingfc.player.service;

import comatchingfc.comatchingfc.player.dto.PlayerRes;
import comatchingfc.comatchingfc.player.repository.PlayerRepository;
import comatchingfc.comatchingfc.user.enums.CheerPropensityEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
