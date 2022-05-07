package al.ikubinfo.hrmanagement.converters;

public interface BidirectionalConverter<DTO, ENTITY> {

    DTO toDto(ENTITY entity);

    ENTITY toEntity(DTO dto);

}
