package al.ikubinfo.hrmanagement.converters;

public interface BidirectionalConverter<D, E> {

    D toDto(E entity);

    E toEntity(D dto);

}
