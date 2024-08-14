package crm.service.mapper;

import crm.domain.Deals;
import crm.service.dto.DealsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Deals} and its DTO {@link DealsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DealsMapper extends EntityMapper<DealsDTO, Deals> {}
