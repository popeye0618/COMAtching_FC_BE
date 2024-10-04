package comatchingfc.comatchingfc.survey.choice.dto;

import java.util.List;

public interface ChoiceProjection {
    String getChoiceText();
    List<CheerPropensityProjection> getCheerPropensities();
}