package crm.service.mapper;

import crm.domain.Accounts;
import crm.service.dto.AccountsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Accounts} and its DTO {@link AccountsDTO}.
 */
@Mapper(componentModel = "spring")
public interface AccountsMapper extends EntityMapper<AccountsDTO, Accounts> {}
