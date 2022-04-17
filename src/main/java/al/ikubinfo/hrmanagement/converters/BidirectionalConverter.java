package al.ikubinfo.hrmanagement.converters;

public interface BidirectionalConverter<DTO, ENTITY> {

    public DTO toDto(ENTITY entity);

    public ENTITY toEntity(DTO dto);

}
