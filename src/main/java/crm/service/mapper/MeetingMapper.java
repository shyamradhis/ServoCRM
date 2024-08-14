package crm.service.mapper;

import crm.domain.Meeting;
import crm.service.dto.MeetingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Meeting} and its DTO {@link MeetingDTO}.
 */
@Mapper(componentModel = "spring")
public interface MeetingMapper extends EntityMapper<MeetingDTO, Meeting> {}
